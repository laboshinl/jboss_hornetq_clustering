package com.nokia.mro;

import java.util.Map;
import java.util.Set;

import com.nokia.mro.kpi.Kpi_itf;
import com.nokia.mro.kpi.Kpi_itf.Aggregation;
import com.nokia.mro.model.CellLite_itf;

/**
 * Models the KPI Interface needed for MRO (Prototype Code)
 * @author acp
 *
 */
public interface Kpi_VIMInterface {

	/**
	 *  Gets all the KPI from Adjacencies that goes from the source cell to the target cell
	 *  Example KPI like Early/Late HO which involves source and destination cell
	 * @param sourcecell (HO originating cell)
	 * @param targetcell (HO target cell)
	 * @return
	 */
	Set<Kpi_itf>  getAllKPIsOfAdjacencies(CellLite_itf sourcecell,CellLite_itf targetcell);
	
	/**
	 *  Gets all the KPIs of the particular cell; Example KPI's like Radio Link Failure
	 * @param sourcecell
	 * @return
	 */
	Set<Kpi_itf>  getAllKPIsofCell(CellLite_itf sourcecell);
	
	/**
	 * This method returns the Adjacencies of Cells where a particular KPI has crossed
	 * a given threshold 
	 * @param filterClass - The particular KPI that one is interested in
	 * @param aggregationFilter - Whether KPI is filtered for Daily, Hourly etc
	 * @param sourceCellScope
	 * @param treshold
	 * @return
	 */
	<T> Map<CellLite_itf, Set<CellLite_itf>> getAdjacenciesWhereKPIAboveThreshold(
			Class<T> filterClass,Aggregation aggregationFilter,Set<CellLite_itf> sourceCellScope, double treshold);

	void addKpi(CellLite_itf sourceCell, CellLite_itf targetCell, Kpi_itf kpi);
	
	
}
