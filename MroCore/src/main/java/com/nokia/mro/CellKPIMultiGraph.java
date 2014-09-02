package com.nokia.mro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedMultigraph;

import com.nokia.mro.kpi.Kpi_Comparator;
import com.nokia.mro.kpi.Kpi_Edge;
import com.nokia.mro.kpi.Kpi_itf;
import com.nokia.mro.kpi.Kpi_itf.Aggregation;
import com.nokia.mro.model.CellLite_itf;


/**
 * This calls models Cell relationship as a Directed Multi-graph, the multiple 
 * edged being the different KPI's
 * That is all Cells are vertecies of the graph
 * For a KPI for an adjacency from a source cell to a target cell, there is a directed edge from the source
 * to target cell. The value of the directed edge is is Kpi_itf value
 * For a KPI that is for a particular cell there is a loop for that cell. The value of the loop (an edge where source 
 * and dest vertices are hte same) is the vlaue of the KPI
 * Each KPI also has this information
 * @author acp
 *
 */
public class CellKPIMultiGraph extends DirectedMultigraph<CellLite_itf, Kpi_itf> implements Kpi_VIMInterface {

	public CellKPIMultiGraph(Class<? extends Kpi_itf> kpival) {
		super(kpival);
	}


	/**
	 * Helper function for easy understanding
	 * @param kpi - The KPI that needs to be added
	 * @param sourcecell -Source Cell
	 * @param targetcell - Destination Cell (Can also be the source cell)
	 */
	@Override
	public void  addKpi(CellLite_itf sourceCell,CellLite_itf targetCell,Kpi_itf kpi){

		try{
			kpi.setCellAssociation(sourceCell, targetCell);
			addEdge(sourceCell, targetCell,kpi);
		}catch(IllegalArgumentException e){ //In case vertex are not added
			boolean k=this.containsVertex(sourceCell) ? true :addVertex(sourceCell);
			k=this.containsVertex(targetCell) ? true :addVertex(targetCell);
			addEdge(sourceCell, targetCell,kpi);
		}
	}


	/**
	 * Helper function for easy understanding
	 * Return all Cell Related KPI like {@link Kpi_RadiolnkFailure}
	 * @param sourcecell
	 * @return
	 */
	@Override
	public Set<Kpi_itf>  getAllKPIsofCell(CellLite_itf sourcecell){
		return getAllEdges(sourcecell, sourcecell);
	}

	@Override
	/**
	 * Get Adjacency of all Cells above a threshold where the Cells are target of the  
	 *  given sourceCellScope
	 */
	public <T> Map<CellLite_itf, Set<CellLite_itf>> getAdjacenciesWhereKPIAboveThreshold(
			Class<T> filterClass, Aggregation aggregationFilter,Set<CellLite_itf> sourceCellScope, double threshold) {
		//Let us start
		//Get all Adjacencies in scope
		Set<Kpi_itf> edgeSet =getAllKPIsofScope(sourceCellScope);
		//Filter only the necessary KPI's
		Set<Kpi_itf> edges= filterKpiSetForKpiType(filterClass,aggregationFilter, edgeSet);
		//Filter only the KPIs that crossed the threshold
		edges =	filterKpiSetforThresholdAbove(edges,threshold);
		//Find the culprint Cells
		Map<CellLite_itf, Set<CellLite_itf>> cellSourceToDestMap = new HashMap<CellLite_itf, Set<CellLite_itf>>();
		for(Kpi_itf kpival: edges){
			CellLite_itf sourcell=kpival.getSourceCell();
			CellLite_itf targetCell=kpival.getTargetCell();
			if(cellSourceToDestMap.containsKey(sourcell)){

				Set<CellLite_itf> targetCellSet = cellSourceToDestMap.get(sourcell);
				targetCellSet.add(targetCell);
			}else{
				Set<CellLite_itf> targetCellSet = new HashSet<CellLite_itf>();
				targetCellSet.add(targetCell);
				cellSourceToDestMap.put(sourcell, targetCellSet);
			}
		}

		return cellSourceToDestMap;
	}

	/**
	 * Returns all the Adjacensies whose origin is the sourceCell
	 * @param sourcecell
	 * @return
	 */
	public  Set<CellLite_itf> getTargetCellsofSource(CellLite_itf sourcecell){

		Set<CellLite_itf> targetCellSet = new HashSet<CellLite_itf>();

		if(!containsVertex(sourcecell)){
			return targetCellSet;
		}

		if(outDegreeOf(sourcecell)<1){
			return targetCellSet;
		}
		Set<Kpi_itf> kpiset =this.outgoingEdgesOf(sourcecell);
		for(Kpi_itf kpiVal: kpiset){
			CellLite_itf targetCell=this.getEdgeTarget(kpiVal);
			targetCellSet.add(targetCell);
		}
		return targetCellSet;
	}

	/**
	 * Set all the Adjacencies for the source cell
	 * @param sourcecell
	 * @param adjacentCell
	 */
	public void setAllAdjacensiesOfCell(CellLite_itf sourceCell, Set<CellLite_itf> adjacentCell){

		//add the vertex
		boolean k=this.containsVertex(sourceCell) ? true :addVertex(sourceCell);

		//add the edge
		for( CellLite_itf adjcell: adjacentCell){

			//add the vertex
			k=this.containsVertex(adjcell) ? true :addVertex(adjcell);
			this.addEdge(sourceCell, adjcell, new Kpi_Edge(1.0, null, 0, 0));
		}
	}

	/**
	 * Filters the KPI Set for Threshold
	 * @param edges
	 * @param threshold
	 * @return
	 */
	protected Set<Kpi_itf> filterKpiSetforThresholdAbove(Set<Kpi_itf> edges, double threshold){

		Iterator<Kpi_itf> kpi_itr = edges.iterator();

		while(kpi_itr.hasNext()){

			Kpi_itf kpival = kpi_itr.next();

			if(kpival.getValue() <= threshold){
				kpi_itr.remove();// remove from the set
			}
		}
		return edges;
	}
	/**
	 * getAllKPIsofScope
	 * @param sourceCellScope
	 * @return
	 */
	protected Set<Kpi_itf> getAllKPIsofScope(Set<CellLite_itf> sourceCellScope)
	{

		Set<Kpi_itf> edgeSet = new HashSet<Kpi_itf>();
		for(CellLite_itf sourcecell :sourceCellScope){

			Set<Kpi_itf> edges =this.outgoingEdgesOf(sourcecell);
			edgeSet.addAll(edges);
		}
		return edgeSet;
	}

	/** Filters the KPI Set only for the relevant Kpi Types
	 * @param filterClass
	 * @param sourceCellScope
	 * @return
	 * 
	 */
	//TODO: All the Colleciton filters should be handled by some predicate type interface
	// like http://www.dzone.com/snippets/collection-filtering-java
	protected <T> Set<Kpi_itf> filterKpiSetForKpiType(Class<T> filterClass,
			Aggregation aggregationFilter,Set<Kpi_itf> edgeSet) {

		Iterator< Kpi_itf> itr = edgeSet.iterator();

		while(itr.hasNext()){
			Kpi_itf kpiEdge = itr.next();
			if((kpiEdge.getAggreagationInterval()!=aggregationFilter) ||
					!kpiEdge.getClass().equals(filterClass)){
				itr.remove();
			}

		}
		//The resultant Edge Set will have only the needed KPI's
		return edgeSet;
	}


	@Override
	/**
	 *  Over-riding with a dummy edge
	 */
	public Kpi_itf addEdge(CellLite_itf sourceCell, CellLite_itf targetCell) {

		Kpi_itf  kpitemp = new Kpi_Edge(1.0, null, 0, 0);

		//add the vertex
		boolean k=this.containsVertex(sourceCell) ? true :addVertex(sourceCell);
		k=this.containsVertex(targetCell) ? true :addVertex(targetCell);
		kpitemp.setCellAssociation(sourceCell, targetCell);
		this.addEdge(sourceCell, targetCell,kpitemp );
		return kpitemp;
	}

	/** Helper method to get all the Source Cells from the Graph
	 * @param cellgraph
	 * @param resultSet
	 */
	public Set<CellLite_itf> getSourceCells() {

		Set<CellLite_itf> resultSet = new HashSet<CellLite_itf>();

		Set<CellLite_itf> vertexSet= vertexSet();

		for(CellLite_itf cell: vertexSet){

			if(outDegreeOf(cell) >0){//then it is a source cell
				resultSet.add(cell);
			}
		}
		return resultSet;
	}

	/** Helper method to get all the Target Cells from the Graph
	 * @param cellgraph
	 * @param resultSet
	 */
	public Set<CellLite_itf> getTargetCells() {

		Set<CellLite_itf> resultSet = new HashSet<CellLite_itf>();

		Set<CellLite_itf> vertexSet= vertexSet();

		for(CellLite_itf cell: vertexSet){

			if(inDegreeOf(cell) >0){//then it is a source cell
				resultSet.add(cell);
			}
		}
		return resultSet;
	}


	@Override
	public Set<Kpi_itf> getAllKPIsOfAdjacencies(CellLite_itf sourcecell,
			CellLite_itf targetcell) {

		return getAllEdges(sourcecell, targetcell);

	}

	/**
	 * Sorting all the Adgencencies based on the value of a particular KPI (edge)
	 * @param cellgraph
	 * @param kpiName
	 * @return
	 */
	public List<Kpi_itf>  sortAdjacencybyWeight(String kpiName) {
		long startime = System.currentTimeMillis();
		System.out.println("Going to calculateCost for all the edges! ");

		List<Kpi_itf> kpiList = new ArrayList<Kpi_itf>();
		{
			Set<Kpi_itf> kpiSet= this.edgeSet();
			for(Kpi_itf kpi:kpiSet){
				if(kpi.getClass().getName()==kpiName)
					kpiList.add(kpi);
			}
		}
		Collections.sort(kpiList, new Kpi_Comparator());

		long timetaken = (System.currentTimeMillis() -startime)/1000;
		System.out.println("Calculated cost in seconds =" + timetaken);
		return kpiList;
	}

	/**
	 * To clear the graph
	 * It is cheaper to drop the Graph
	 */
	private void clearGraph(){

		List<Kpi_itf> copy = new LinkedList<Kpi_itf> ();
		for (Kpi_itf e : this.edgeSet()) {

			copy.add(e);

		}

		removeAllEdges(copy);
		List<CellLite_itf> copy2 = new LinkedList<CellLite_itf>();
		for (CellLite_itf v : vertexSet()) {
			copy2.add(v);
		}
		removeAllVertices(copy2);


	}






}
