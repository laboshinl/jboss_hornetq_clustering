package com.nokia.mro.model;

public class CellDetailed implements CellDetailed_Itf{

	private int lattitude;
	private int longitude;
	private int localcellid;
	CellLite_itf cell;
	private String distinguishedName;

	public CellDetailed(int lattitude,
			int longitude,int lcrid, CellLite_itf cell) {
		super();
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.localcellid = lcrid;
		this.cell = cell;
	}
	

	@Override
	public void setEutranCellid(int eutrancellid) {
		this.cell.setEutranCellid(eutrancellid);
	}

	@Override
	public int getEutranCellid() {
		return this.cell.getEutranCellid();
	}

	@Override
	public void setMCC(int mcc) {
		this.setMCC(mcc);
	}

	@Override
	public int getMCC() {
		return this.cell.getMCC();
	}

	@Override
	public void setMNC(int mnc) {
		this.cell.setMNC(mnc);
	}

	@Override
	public int getMNC() {
		return this.cell.getMNC();
	}

	@Override
	public int getCellLatitude(CellLite_itf cell) {
		return this.lattitude;
	}

	@Override
	public int getCellLongitude(CellLite_itf cell) {
		return this.longitude;
	}

	@Override
	public int geteNodeBId(int localcellid) {
		return this.geteNodeBId(localcellid);
	}
	
	@Override
	public int getLocalCellId(){
		return this.localcellid;
	}


	@Override
	public void setDistinguishedName(String distName) {
		this.distinguishedName = distName;
		
	}


	@Override
	public String getDistinguishedName() {
		return this.distinguishedName;
	}


}
