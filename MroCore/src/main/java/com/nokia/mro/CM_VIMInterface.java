package com.nokia.mro;

import java.util.Set;

import com.nokia.mro.model.CellDetailed_Itf;
import com.nokia.mro.model.CellLite_itf;

/**
 * Models the Cell Topology Relationship and Retrieval needed for MRO (Prototype) 
 * @author acp
 *
 */
public interface CM_VIMInterface {
	
	
	/**
	 * Gets all the scopes for the EMS/NetActs
	 * The EM Identifier can be the NetAct cluster name
	 * @param EMidentifier
	 * @return
	 */
	Set<String> getAllScopeforEM(String EMidentifier);
	
	/**
	 * Get all the LTE Cells that are part of the Scope
	 * The Scope name can be a predefined scope consisting of DN's
	 * or scope created in the Map
	 * @param scopeName
	 * @return
	 */
	Set<CellLite_itf> getAllLTECellsforScope(String scopeName);
	
	/**
	 *  Get's All the Adjacencies for A Cell, that is a Set of all Cell which 
	 *  are HO targets of the sourceCell
	 * @param sourceCell
	 * @return
	 */
	Set<CellLite_itf> getAllEUtranRelationsforCell(CellLite_itf sourceCell);
	
	/**
	 * Get all the additional details for the Cell like Location, Parent Name
	 * Operational Status or any other info that is needed
	 * @param cell_itf
	 */
	CellDetailed_Itf getEUtranCellDetails(CellLite_itf cell_itf);
	

}
