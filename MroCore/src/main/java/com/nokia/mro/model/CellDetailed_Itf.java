package com.nokia.mro.model;

/**
 * Contains all the details of the Cell like location 
 * cell operational status etc
 * @author acp
 *
 */
public interface CellDetailed_Itf extends CellLite_itf {

	int getCellLatitude(CellLite_itf cell);
	int getCellLongitude(CellLite_itf cell);
	int getLocalCellId();
	
}
