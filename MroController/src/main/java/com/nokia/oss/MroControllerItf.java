package com.nokia.oss;

import com.nokia.ison.FeedbackType;

/**
 * MROController Interface which specifies all the major opertaion
 * @author acp
 *
 */

public interface MroControllerItf {
	
	/**
	 * Sart MRO for a particular source scope and target scope
	 * @param sourceScopeName
	 * @param targetScopeName
	 * @return
	 */
	public FeedbackType startMroforScope(String sourceScopeName,String targetScopeName);
	public void aggregateNRSortResults(String nrResults);

}
