package com.nokia.mro.model;

/**
 * Interface to model a very light weight Cell (LNCEL) so that 
 * millions of these can be held in memory
 * 
 * @author acp
 *
 */
public interface CellLite_itf {
	
	void setEutranCellid(int eutrancellid);
	int getEutranCellid();
	void setMCC(int mcc);
	int getMCC();
	void setMNC(int mnc);
	int getMNC();
	void setDistinguishedName(String distName);
	String getDistinguishedName();
	/**
	 * Get the MRBTS , LNBTS id basiclly the btsId field
	 * @param localcellid = lcrid in RAML
	 * @return
	 */
	int geteNodeBId(int localcellid);

}
