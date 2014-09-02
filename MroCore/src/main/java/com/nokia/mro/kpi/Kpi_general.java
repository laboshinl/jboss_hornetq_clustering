package com.nokia.mro.kpi;

import com.nokia.mro.model.CellLite_itf;



/**
 * Abstarct holder class
 * @author acp
 *
 */
public abstract class Kpi_general implements Kpi_itf {

	
	private double kpival;
	private long startDate;
	private long endDate;
	private Aggregation interval;
	private CellLite_itf sourceCell;
	private CellLite_itf destCell;
	
	
	public Kpi_general(double value, Aggregation interval,long startdate,long enddate) {
		this.kpival = value;
		this.startDate=startdate;
		this.endDate =enddate;
		this.interval=interval;
	}


	@Override
	public void setValue(double kpival) {
		this.kpival = kpival;
	}

	@Override
	public void setStartDate(long startDate) {
		this.startDate = startDate;

	}

	@Override
	public void setEndDate(long endDate) {
		this.endDate = endDate;

	}

	@Override
	public void setAggregationCount(Aggregation interval) {
		this.interval = interval;

	}

	@Override
	public double getValue() {
		return kpival;
	}

	@Override
	public long getStartDate() {
		return startDate;
	}

	@Override
	public long getEndDate() {
		return endDate;
	}

	@Override
	public void setCellAssociation(CellLite_itf sourceCell,CellLite_itf destCell){
		this.sourceCell=sourceCell;
		this.destCell=destCell;
	}
	
	@Override
	public CellLite_itf getTargetCell() {
			return this.destCell;
	}
	
	@Override
	public CellLite_itf getSourceCell() {
			return this.sourceCell;
	}
	@Override
	public Aggregation getAggreagationInterval() {
		return interval;
	}

	
	
}
