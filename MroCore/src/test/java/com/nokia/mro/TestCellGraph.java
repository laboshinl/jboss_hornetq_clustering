package com.nokia.mro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.nokia.mro.kpi.Kpi_HOPingPongCounter;
import com.nokia.mro.kpi.Kpi_HOTooEarlyCounter;
import com.nokia.mro.kpi.Kpi_HOTooLateCounter;
import com.nokia.mro.kpi.Kpi_HOWrongCellTargetCounter;
import com.nokia.mro.kpi.Kpi_NoOfHOAttempts;
import com.nokia.mro.kpi.Kpi_itf;
import com.nokia.mro.kpi.Kpi_itf.Aggregation;
import com.nokia.mro.model.CellAdjacency;
import com.nokia.mro.model.CellDetailed_Itf;
import com.nokia.mro.model.CellLite;
import com.nokia.mro.model.CellLite_itf;



public class TestCellGraph {

	CellLite_itf sourceCell,sourceCell2,targetCell1,targetCell2,targetCell3,targetCell4;
	Kpi_itf cellRlf_27414529,cellRlf_27412484,cellRlf_27412485,cellRlf_27412486,hosuceesRatio1,hosuceesRatio2,
	hosuceesRatio3,hosuceesRatio4,lateHo1,lateHo2,lateHo3,lateHo4,hoattempts1,hoattempts2,hoattempts3;
	@Before
	public void init(){
		sourceCell= new CellLite(10,155,27414529);
		//Create adjacency for the Cell
		targetCell1= new CellLite(10,155,27412484);
		targetCell2= new CellLite(10,155,27412485);
		targetCell3= new CellLite(10,155,27412486);
		targetCell4= new CellLite(10,155,27412492);
		sourceCell2= new CellLite(10,155,27412481);

		//Add cell level KPI's
		cellRlf_27414529 = new Kpi_HOPingPongCounter(18, Aggregation.DAILY,30072012,0);
		cellRlf_27412484 = new Kpi_HOPingPongCounter(12, Aggregation.DAILY,30072012,0);
		cellRlf_27412485 = new Kpi_HOPingPongCounter(17, Aggregation.DAILY,30072012,0);
		cellRlf_27412486 = new Kpi_HOPingPongCounter(18, Aggregation.DAILY,30072012,0);

		//Add KPI's for each Adjacencies

		hosuceesRatio1 = new Kpi_HOTooEarlyCounter(88, Aggregation.DAILY,30072012,0);
		hosuceesRatio2 = new Kpi_HOTooEarlyCounter(92, Aggregation.DAILY,30072012,0);
		hosuceesRatio3 = new Kpi_HOTooEarlyCounter(94, Aggregation.DAILY,30072012,0);
		hosuceesRatio4 = new Kpi_HOTooEarlyCounter(94, Aggregation.DAILY,30072012,0);

		lateHo1 = new Kpi_HOTooLateCounter(3.45, Aggregation.DAILY,30072012,0);
		lateHo2 = new Kpi_HOTooLateCounter(6.52, Aggregation.DAILY,30072012,0);
		lateHo3 = new Kpi_HOTooLateCounter(13.2, Aggregation.DAILY,30072012,0);
		lateHo4 = new Kpi_HOTooLateCounter(95, Aggregation.DAILY,30072012,0);

		hoattempts1 = new Kpi_NoOfHOAttempts(3.45, Aggregation.DAILY,30072012,0);
		hoattempts2 = new Kpi_NoOfHOAttempts(6.52, Aggregation.DAILY,30072012,0);
		hoattempts3 = new Kpi_NoOfHOAttempts(13.2, Aggregation.DAILY,30072012,0);

	}

	@Test
	public void testGraphRaw(){


		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		cellgraph.addVertex(targetCell1);
		cellgraph.addVertex(targetCell2);
		cellgraph.addVertex(targetCell3);
		cellgraph.addVertex(sourceCell);

		cellgraph.addEdge(sourceCell, targetCell1,hosuceesRatio1);
		cellgraph.addEdge(sourceCell, targetCell2,hosuceesRatio2);
		cellgraph.addEdge(sourceCell, targetCell2,hosuceesRatio3);

		cellgraph.addEdge(sourceCell, targetCell1,lateHo1);
		cellgraph.addEdge(sourceCell, targetCell2,lateHo2);
		cellgraph.addEdge(sourceCell, targetCell2,lateHo3);


		Set<Kpi_itf> kpisetExpected= new HashSet<Kpi_itf>();
		kpisetExpected.add(hosuceesRatio1);
		kpisetExpected.add(hosuceesRatio2);
		kpisetExpected.add(hosuceesRatio3);
		kpisetExpected.add(lateHo1);
		kpisetExpected.add(lateHo2);
		kpisetExpected.add(lateHo3);

		Set<Kpi_itf> kpiset= cellgraph.outgoingEdgesOf(sourceCell);
		Assert.assertTrue(kpisetExpected.containsAll(kpiset));

		cellgraph.addEdge(sourceCell, sourceCell,cellRlf_27414529);
		kpisetExpected.add(cellRlf_27414529);

		CellLite_itf sourceCell2= new CellLite(10,155,27412481);

		Kpi_itf lateHo4 = new Kpi_HOTooLateCounter(3.77, Aggregation.DAILY,30072012,0);
		cellgraph.addKpi(sourceCell2, sourceCell,lateHo4);

		//cellgraph.addVertex(sourceCell2);
		//Set<Kpi_itf>  kpiset2= cellgraph.incomingEdgesOf(sourceCell);
		Set<Kpi_itf>  kpiset3=cellgraph.getAllEdges(sourceCell, sourceCell);

		for(Kpi_itf itr: kpiset3){
			System.out.println(itr.getValue());

		}

		Set<Kpi_itf> kpisetExpected2= new HashSet<Kpi_itf>();
		kpisetExpected2.add(cellRlf_27414529);
		Assert.assertTrue(kpisetExpected2.containsAll(kpiset3));
	}

	@Test
	public void testGraphWrapper(){


		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		cellgraph.addKpi(sourceCell, targetCell1,hosuceesRatio1);
		cellgraph.addKpi(sourceCell, targetCell2,hosuceesRatio2);
		cellgraph.addKpi(sourceCell, targetCell2,hosuceesRatio3);
		cellgraph.addKpi(sourceCell, targetCell1,lateHo1);
		cellgraph.addKpi(sourceCell, targetCell2,lateHo2);
		cellgraph.addKpi(sourceCell, targetCell2,lateHo3);
		Set<Kpi_itf> kpisetExpected= new HashSet<Kpi_itf>();
		kpisetExpected.add(hosuceesRatio1);
		kpisetExpected.add(hosuceesRatio2);
		kpisetExpected.add(hosuceesRatio3);
		kpisetExpected.add(lateHo1);
		kpisetExpected.add(lateHo2);
		kpisetExpected.add(lateHo3);
		Set<Kpi_itf> kpiset= cellgraph.outgoingEdgesOf(sourceCell);
		Assert.assertTrue(kpisetExpected.containsAll(kpiset));
		cellgraph.addKpi(sourceCell, sourceCell,cellRlf_27414529);
		kpisetExpected.add(cellRlf_27414529);
		cellgraph.addKpi(sourceCell2, sourceCell,lateHo4);
		Set<Kpi_itf>  kpiset3=cellgraph.getAllEdges(sourceCell, sourceCell);
		Set<Kpi_itf> kpisetExpected2= new HashSet<Kpi_itf>();
		kpisetExpected2.add(cellRlf_27414529);
		Assert.assertTrue(kpisetExpected2.containsAll(kpiset3));


	}


	@Test
	public void testfilterKpiSetForKpiType(){

		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);

		Kpi_itf kpi = new Kpi_NoOfHOAttempts(0, null, 0, 0);
		Set<Kpi_itf> kpisetInput= new HashSet<Kpi_itf>();
		kpisetInput.add(hosuceesRatio1);
		kpisetInput.add(hosuceesRatio2);
		kpisetInput.add(hosuceesRatio3);
		kpisetInput.add(lateHo1);
		kpisetInput.add(lateHo2);
		kpisetInput.add(lateHo3);

		Set<Kpi_itf> kpisetExpected= new HashSet<Kpi_itf>();
		kpisetExpected.add(hosuceesRatio1);
		kpisetExpected.add(hosuceesRatio2);
		kpisetExpected.add(hosuceesRatio3);

		Set<Kpi_itf> resultset =cellgraph.filterKpiSetForKpiType(Kpi_HOTooEarlyCounter.class,Aggregation.DAILY,kpisetInput) ;
		Assert.assertTrue(resultset.equals(kpisetExpected));
	}

	@Test
	public void testgetAllKPIsofScope(){

		Set<CellLite_itf> sourceCellScope = new HashSet<CellLite_itf>();
		sourceCellScope.add(sourceCell);
		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		cellgraph.addKpi(sourceCell, targetCell1,hosuceesRatio1);
		cellgraph.addKpi(sourceCell, targetCell2,hosuceesRatio2);
		cellgraph.addKpi(sourceCell, targetCell2,hosuceesRatio3);
		cellgraph.addKpi(sourceCell, sourceCell,hoattempts1);
		cellgraph.addKpi(sourceCell2, sourceCell,lateHo4);
		Set<Kpi_itf> kpisetExpected= new HashSet<Kpi_itf>();
		kpisetExpected.add(hosuceesRatio1);
		kpisetExpected.add(hosuceesRatio2);
		kpisetExpected.add(hosuceesRatio3);
		kpisetExpected.add(hoattempts1);
		Set<Kpi_itf> resultSet= cellgraph.getAllKPIsofScope(sourceCellScope);
		Assert.assertTrue(resultSet.equals(kpisetExpected));
	}

	@Test
	public void testfilterKpiSetforThreshold(){

		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		Set<Kpi_itf> inputset= new HashSet<Kpi_itf>();
		inputset.add(hosuceesRatio1);
		inputset.add(hosuceesRatio2);
		inputset.add(hosuceesRatio3);

		Set<Kpi_itf> expectedSet= new HashSet<Kpi_itf>();
		expectedSet.add(hosuceesRatio3);
		Set<Kpi_itf> resultSet=  cellgraph.filterKpiSetforThresholdAbove(inputset, 93);
		Assert.assertTrue(resultSet.equals(expectedSet));

	}

	@Test
	public void testgetAdjacenciesWhereKPIAboveThreshold(){

		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		cellgraph.addKpi(sourceCell, targetCell1,hosuceesRatio1);
		cellgraph.addKpi(sourceCell2, targetCell2,hosuceesRatio2);
		cellgraph.addKpi(sourceCell, targetCell3,hosuceesRatio3);
		cellgraph.addKpi(sourceCell, sourceCell,hoattempts1);
		cellgraph.addKpi(sourceCell2, sourceCell,lateHo4);

		Set<CellLite_itf> sourceCellScope = new HashSet<CellLite_itf>();
		sourceCellScope.add(sourceCell);

		cellgraph.getAdjacenciesWhereKPIAboveThreshold(
				Kpi_HOTooEarlyCounter.class,Aggregation.DAILY,sourceCellScope,93);
		Map<CellLite_itf, Set<CellLite_itf>> resultSet =cellgraph.getAdjacenciesWhereKPIAboveThreshold(
				Kpi_HOTooEarlyCounter.class,Aggregation.DAILY,sourceCellScope,93);

		Gson gson = new Gson();

		Map<CellLite_itf, Set<CellLite_itf>> expectedSet = new HashMap<CellLite_itf, Set<CellLite_itf>>();
		Set<CellLite_itf> target = new HashSet<CellLite_itf>();
		target.add(targetCell3);
		//System.out.println(gson.toJson(target));
		System.out.println(gson.toJson(resultSet));
		expectedSet.put(sourceCell, target);
		Assert.assertTrue(resultSet.equals(expectedSet));

	}

	/**
	 * Testting to get the source cell from the Graph
	 * This is a wrong way
	 */
	@Test
	public void testGetSourceCell(){

		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		cellgraph.addVertex(sourceCell);
		Set<CellLite_itf> expectedSet = new HashSet();

		for(int i =0; i <30; i++){
			CellLite_itf	sourceCellTemp= new CellLite(10,155,27414529+i);
			expectedSet.add(sourceCellTemp);
			for(int k =0; k <60; k++){
				CellLite_itf	targetCellTemp= new CellLite(10,155,27412484+k);
				cellgraph.addEdge(sourceCellTemp, targetCellTemp);

			}
		}

		Set<CellLite_itf> resultSet = new HashSet();

		Set<Kpi_itf> kpiset =cellgraph.edgeSet();

		for(Kpi_itf kpi: kpiset){

			CellLite_itf cell =kpi.getSourceCell();
			resultSet.add(cell);
		}

		System.out.println("SourceSet= " + resultSet.size());
		Assert.assertTrue(resultSet.equals(expectedSet));
	}


	/**
	 * Adding more KPI's edges after the graph is formed should not affect the graph
	 */
	@Test
	public void testGetSourceCell2(){

		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		Set<CellLite_itf> expectedSet = new HashSet();

		for(int i =0; i <30; i++){
			CellLite_itf	sourceCellTemp= new CellLite(10,155,27414529+i);
			expectedSet.add(sourceCellTemp);
			for(int k =0; k <60; k++){
				CellLite_itf	targetCellTemp= new CellLite(10,155,27412484+k);
				cellgraph.addEdge(sourceCellTemp, targetCellTemp);

			}
		}

		Set<CellLite_itf> resultSet = cellgraph.getSourceCells();
		System.out.println("SourceSet= " + resultSet.size());
		Assert.assertTrue(resultSet.equals(expectedSet));
		loadKpis(cellgraph);// Add more KPIS

		Set<CellLite_itf> resultSet2 = cellgraph.getSourceCells();
		System.out.println("SourceSet= " + resultSet2.size());
		Assert.assertTrue(resultSet2.equals(expectedSet));

	}


	/**
	 * Testing to get the Target Cell
	 */
	@Test
	public void testGetTargetCell(){

		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		Set<CellLite_itf> expectedSet = new HashSet();

		for(int i =0; i <30; i++){
			CellLite_itf	sourceCellTemp= new CellLite(10,155,27414529+i);

			for(int k =0; k <60; k++){
				CellLite_itf	targetCellTemp= new CellLite(10,155,27412484+k);
				cellgraph.addEdge(sourceCellTemp, targetCellTemp);
				expectedSet.add(targetCellTemp);
			}
		}

		Set<CellLite_itf> resultSet = cellgraph.getTargetCells();
		System.out.println("TargetSet= " + resultSet.size());
		Assert.assertTrue(resultSet.equals(expectedSet));
	}

	/**
	 * Testing to get the Target Cell
	 */
	@Test
	public void testGetTargetCellofSourceCell(){

		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		cellgraph.addVertex(sourceCell);
		Set<CellLite_itf> expectedSet = new HashSet();

		for(int i =0; i <30; i++){
			CellLite_itf	sourceCellTemp= new CellLite(10,155,27414529+i);

			for(int k =0; k <60; k++){
				CellLite_itf	targetCellTemp= new CellLite(10,155,27412484+k);
				cellgraph.addEdge(sourceCellTemp, targetCellTemp);
				if(i==0){
					expectedSet.add(targetCellTemp);
				}

			}
		}

		Set<CellLite_itf> resultSet = cellgraph.getTargetCellsofSource(new CellLite(10,155,27414529+0));
		System.out.println("TargetSet= " + resultSet.size());
		Assert.assertTrue(resultSet.equals(expectedSet));
	}

	@Test
	public void testgetTargetCell(){

		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		cellgraph.addKpi(sourceCell, targetCell1,hosuceesRatio1);
		cellgraph.addKpi(targetCell1, sourceCell,hosuceesRatio3);
		Set<CellLite_itf> expectedSet= new HashSet();
		expectedSet.add(targetCell1);
		Set<CellLite_itf> resultSet=cellgraph.getTargetCellsofSource(sourceCell);
		Assert.assertTrue(resultSet.equals(expectedSet));

	}

	@Test
	public void testGetAdjacencies(){

		final Set<CellLite_itf> adjacencySet = new HashSet<CellLite_itf>();
		adjacencySet.add(targetCell1);
		adjacencySet.add(targetCell2);
		adjacencySet.add(targetCell3);
		Set<CellAdjacency> lnrelSet = new HashSet<CellAdjacency>();

		CellAdjacency temp1= new CellAdjacency(sourceCell, targetCell1);
		CellAdjacency temp2= new CellAdjacency(sourceCell, targetCell2);
		CellAdjacency temp3= new CellAdjacency(sourceCell, targetCell3);

		lnrelSet.add(temp1);
		lnrelSet.add(temp2);
		lnrelSet.add(temp3);

		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		/**
		 * To Illustrate how SoftVIM needs to be modelled
		 */
		CM_VIMInterface vim =  new CM_VIMInterface() {

			@Override
			public CellDetailed_Itf getEUtranCellDetails(CellLite_itf cell_itf) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Set<String> getAllScopeforEM(String EMidentifier) {
				return null;
			}

			@Override
			public Set<CellLite_itf> getAllLTECellsforScope(String scopeName) {

				Set<CellLite_itf> cellSet= new HashSet<CellLite_itf>();
				cellSet.add(sourceCell);
				return cellSet;
			}

			@Override
			public Set<CellLite_itf> getAllEUtranRelationsforCell(CellLite_itf sourceCell) {
				return adjacencySet;
			}
		};


		Set<CellLite_itf> cellSet =vim.getAllLTECellsforScope("TestScope");
		Set<CellLite_itf> expectedSet= vim.getAllEUtranRelationsforCell((CellLite_itf) cellSet.toArray()[0]);

		//Set<CellLite_itf> expectedSet=vim.getAllLTEAdjacenciesforCell(sourceCell);
		cellgraph.setAllAdjacensiesOfCell(sourceCell,expectedSet);
		Set<CellLite_itf> resultSet=cellgraph.getTargetCellsofSource(sourceCell);
		Assert.assertTrue(resultSet.equals(expectedSet));

	}


	/**
	 * For Performance Tests
	 * 
	 */

	//@Test
	public void testGetAdjacenciesPerformanceMultithreaded() throws InterruptedException{
		final CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);

		long startime = System.currentTimeMillis();
		System.out.println("Going to add 30,000  Edges in 4 Threads");
		cellgraph.addVertex(sourceCell);
		List<Thread> threadList= new ArrayList<Thread>();
		for (int outCounter = 0; outCounter < 4; outCounter++) {

			Runnable t1 = new Runnable() {
				private int anonVar;
				@Override
				public void run() {
					for(int i =0; i <7500; i++){
						CellLite_itf	sourceCellTemp= new CellLite(10,155,27414529+anonVar+i);
						//cellgraph.addVertex(sourceCellTemp);
						for(int k =0; k <60; k++){
							CellLite_itf	targetCellTemp= new CellLite(10,155,27412484+k);
							//cellgraph.addVertex(targetCellTemp);
							cellgraph.addEdge(sourceCellTemp, targetCellTemp);
						}
					}

				}
				private Runnable init(int var){
					anonVar = var;
					return this;
				}
			}.init(outCounter)  ;


			Thread  thread1 = new Thread(t1,"Thread0to" +1000);
			thread1.start();
			threadList.add(thread1);

		}

		for(Thread t : threadList){
			t.join();
		}

		long timetaken = (System.currentTimeMillis() -startime)/1000;
		System.out.println("Edges Added in seconds =" + timetaken);
		timetaken = (System.currentTimeMillis() -startime)/1000;
		System.out.println("Commit finished in seconds =" + timetaken);
	}

	@Test
	public void testGetAdjacenciesPerformance() throws InterruptedException{
		final CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);

		loadAdjacencies(cellgraph,10);
		loadKpis(cellgraph);

	}

	public void testMROAlgorithm(){

		final CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);

		loadAdjacencies(cellgraph,150000);
		loadKpis(cellgraph);

	}
	@Test
	public void getAllKPIsOfAdjacencies(){
		final CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);

		long startime = System.currentTimeMillis();
		System.out.println("Going to get the vertex");
		CellLite_itf	sourceCellTemp= new CellLite(10,155,27414529+7000000);
		cellgraph.getAllKPIsOfAdjacencies(sourceCellTemp,null);
		long timetaken = (System.currentTimeMillis() -startime)/1000;
		System.out.println("Vertex queried in seconds =" + timetaken);
	}



	/**
	 * @param cellgraph
	 */
	private void loadAdjacencies(final CellKPIMultiGraph cellgraph,int count) {
		long startime = System.currentTimeMillis();
		System.out.println("Going to add " + count+ " Edges! ");
		cellgraph.addVertex(sourceCell);
		for(int i =0; i <count; i++){
			CellLite_itf	sourceCellTemp= new CellLite(10,155,27414529+i);
			sourceCellTemp.setDistinguishedName("PLMN-PLMN/MRBTS-21/LNCEL-10");
			//cellgraph.addVertex(sourceCellTemp);
			for(int k =0; k <60; k++){
				CellLite_itf	targetCellTemp= new CellLite(10,155,27412484+k);
				targetCellTemp.setDistinguishedName("PLMN-PLMN/MRBTS-111/LNCEL-12");
				//cellgraph.addVertex(targetCellTemp);
				cellgraph.addEdge(sourceCellTemp, targetCellTemp);
			}

		}
		long timetaken = (System.currentTimeMillis() -startime)/1000;
		System.out.println("Edges Added in seconds =" + timetaken);

	}

	private void loadKpis(CellKPIMultiGraph cellgraph) {

		long startime = System.currentTimeMillis();
		System.out.println("Going to add 3 kpi coutners to all the edges! ");
		Set<CellLite_itf> sourceCellSet =cellgraph.getSourceCells();

		for (CellLite_itf sourceCell : sourceCellSet) {

			Set<CellLite_itf> targetCellSet =cellgraph.getTargetCellsofSource(sourceCell);
			for (CellLite_itf targetCell : targetCellSet) {

				Kpi_itf kpiTooEarly = new  Kpi_HOTooEarlyCounter(500L,Aggregation.DAILY ,1231232131L, 3213213123L);
				Kpi_itf kpiTooLate = new  Kpi_HOTooLateCounter(200L,Aggregation.DAILY ,1231232131L, 3213213123L);
				Kpi_itf kpiWrongCellTarget = new  Kpi_HOWrongCellTargetCounter(200L,Aggregation.DAILY ,1231232131L, 3213213123L);
				cellgraph.addKpi(sourceCell, targetCell, kpiTooEarly);
				cellgraph.addKpi(sourceCell, targetCell, kpiTooLate);
				cellgraph.addKpi(sourceCell, targetCell, kpiWrongCellTarget);
				//NHOA_AB =	 HOWrongCellTarget + HOWrongCellReest + HOTooEarly + HOTooLate + HOSuccENBCellPair
				//Calculate the Cumulative HO Failure and PingPong Count for neighbor relation AB using the following formula:
				//NumHOFailuresAB = mroWeightTE*HOTooEarly + mroWeightTL*HOTooLate + mroWeightWR*HOWrongCellReest + mroWeightWT*HOWrongCellTarget
				//NumPingPongsAB  = mroWeightPP*HOPingPong


				//TODO: Calculate cost here itself
			}
		}
		long timetaken = (System.currentTimeMillis() -startime)/1000;
		System.out.println("KPIs Added in seconds =" + timetaken);

	}

	
	//Testing the adjacency
	@Test
	public void testAdjacencySorting(){

		Set<CellLite_itf> sourceCellScope = new HashSet<CellLite_itf>();
		sourceCellScope.add(sourceCell);
		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);
		cellgraph.addKpi(sourceCell, targetCell1,hosuceesRatio1);
		cellgraph.addKpi(sourceCell, targetCell2,hosuceesRatio2);
		cellgraph.addKpi(sourceCell, targetCell3,hosuceesRatio3);
		cellgraph.addKpi(sourceCell, targetCell4,hosuceesRatio4);
		List<Kpi_itf>  expectedSet = new ArrayList();
		expectedSet.add(hosuceesRatio3);
		expectedSet.add(hosuceesRatio4);
		expectedSet.add(hosuceesRatio2);
		expectedSet.add(hosuceesRatio1);
		List<Kpi_itf>  resultSet =cellgraph.sortAdjacencybyWeight("com.nokia.mro.kpi.Kpi_HOTooEarlyCounter");

		Assert.assertTrue(resultSet.equals(expectedSet));

	}

	public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException{

		System.out.println("Going to start the Performance test JGrpah 12_7_1");
		TestCellGraph testCellGraph = new TestCellGraph();
		testCellGraph.init();
		CellKPIMultiGraph cellgraph = new CellKPIMultiGraph(null);

		testCellGraph.loadAdjacencies(cellgraph,100000);
		testCellGraph.loadKpis(cellgraph);
		cellgraph.sortAdjacencybyWeight("com.nokia.mro.kpi.Kpi_HOTooEarlyCounter");
		System.out.println("Performance test finished");

	}


}