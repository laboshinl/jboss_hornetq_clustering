package com.nokia.mro.model;

/**
 * A class to model LNREL's - basically Adjacencies
 * @author acp
 *
 */
public class CellAdjacency {

	public CellAdjacency(CellLite_itf sourceCell, CellLite_itf targetCell) {
		super();
		this.sourceCell = sourceCell;
		this.targetCell = targetCell;
	}
	private CellLite_itf sourceCell;
	private CellLite_itf targetCell;
	public CellLite_itf getSourceCell() {
		return sourceCell;
	}
	public CellLite_itf getTargetCell() {
		return targetCell;
	}
	

}
