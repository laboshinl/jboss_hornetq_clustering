package com.nokia.mro.kpi;

import com.nokia.mro.model.CellLite_itf;



/**
 * Interface to model the KPI
 * @author acp
 *
 */
public interface Kpi_itf {
	
	public static enum Aggregation { MONTHLY,WEEKLY,DAILY, HOURLY,FIVE_MTS};
	
	public void setValue(double kpival);
	public void setStartDate(long startDate);
	public void setEndDate(long endDate);
	public void setCellAssociation(CellLite_itf sourceCell,CellLite_itf destCell);
	
	public CellLite_itf getSourceCell();
	public CellLite_itf getTargetCell();
	
	public void  setAggregationCount(Aggregation interval);
	public double getValue();
	public long getStartDate();
	public long getEndDate();
	public Aggregation getAggreagationInterval();
	
	

}
