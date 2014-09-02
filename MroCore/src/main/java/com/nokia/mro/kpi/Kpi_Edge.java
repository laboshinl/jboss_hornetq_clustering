package com.nokia.mro.kpi;


/**
 * This is to just model a relation between two Cells, Source cell and Target cell,
 * in essence just to model a LNREL relation
 * @author acp
 *
 */
public class Kpi_Edge   extends Kpi_general {

	
	public Kpi_Edge(double value, Aggregation interval, long startdate,
			long enddate) {
		super(value, interval, startdate, enddate);
		
	}
	
}
