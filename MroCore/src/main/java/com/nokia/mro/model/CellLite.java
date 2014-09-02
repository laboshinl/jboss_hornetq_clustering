package com.nokia.mro.model;

import java.io.Serializable;


/**
 * Class to model a very light weight Cell (LNCEL) so that 
 * millions of these can be held in memory
 * 
 * @author acp
 *
 */
public class CellLite implements CellLite_itf ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2548590926806740215L;
	private int mcc; //mobile country code
	private int mnc;// mobile network code
	private int eutran_cell_id;//to find the cell and the enodeb (btsid) This is 20 bits
	//https://www.linkedin.com/groups/when-eNB-has-multiple-cells-1180727.S.221912781
	private String distinguishedName;

	public CellLite(){

	}
	public CellLite(int mcc, int mnc, int eutran_cell_id) {
		this.mcc = mcc;
		this.mnc = mnc;
		this.eutran_cell_id = eutran_cell_id;
	}


	@Override
	public void setEutranCellid(int eutrancellid) {
		this.eutran_cell_id=eutrancellid;
	}

	@Override
	public int getEutranCellid() {
		return this.eutran_cell_id;
	}

	@Override
	public void setMCC(int mcc) {
		this.mcc=mcc;
	}

	@Override
	public int getMCC() {
		return this.mcc;
	}

	@Override
	public void setMNC(int mnc) {
		this.mnc =mnc;
	}

	@Override
	public int getMNC() {
		return this.mnc;
	}

	/**
	 * The eNodeB ID is a string of 20 bits, the eutran cell id is a string of 
	 * 28 bits composed by the eNodeB ID (first 20 most significant bits) and the 
	 * local cell id (the last 8 bits). Finally, the EUTRAN cell ID is obtained 
	 * by shifting the eNodeB ID by 8 bits and then adding (mathematically) the local cell id.
	 * Say
	 *		"mcc"=732
	 * 		"mnc"=130
	 *		"eutraCelId"=512257
	 *		"lcrid"=1 
	 *		Then   512257= x*256+1
	 *		(256 = 8^2)  shifting left is equivalent to multiplication by powers of 2.
	 *		x= 2001
	 *		=PLMN-PLMN/MRBTS-2001/LNBTS-2001/
	 *		"btsId"=2001
	 *
	 * @param localcellid = lcrid  in RAML
	 * @return
	 */
	public int geteNodeBId(int localcellid){

		return  (this.eutran_cell_id-localcellid)/256;

	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CellLite))
			return false;
		if (obj == this)
			return true;
		CellLite tocomp = (CellLite)obj;

		return tocomp.mcc == this.mcc 
				&& tocomp.mnc == this.mnc
				&& tocomp.eutran_cell_id == this.eutran_cell_id;
	}

	@Override
	public int hashCode() {

		int result = 1;
		result += this.mcc;
		result += this.mnc;
		result += this.eutran_cell_id;

		return result;


	}
	@Override
	public void setDistinguishedName(String distName) {
		this.distinguishedName=distName;

	}
	@Override
	public String getDistinguishedName() {

		return distinguishedName;
	}

}
