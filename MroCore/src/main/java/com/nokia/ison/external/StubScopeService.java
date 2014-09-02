package com.nokia.ison.external;

import java.util.ArrayList;
import java.util.List;

/**
 * Acting  as a stub class for Scope Service
 * @author acp
 *
 */
public class StubScopeService {


	static public List<String> getSourceDNsforScopeName(String scopeName){

		List<String> mylist = new ArrayList<String>();

		for(int i=0; i<6000;++i){

			mylist.add("PLMN_PLMN\\LNBTS-" + i + "\\LNCEL-"+ i);
		}
		return mylist;
	}

	static public List<String> getTargetDNsforScopeName(String scopeName){

		List<String> mylist = new ArrayList<String>();

		for(int i=0; i<3000;++i){

			mylist.add("PLMN_PLMN\\LNBTS-" + i + "\\LNCEL-"+ i);
		}
		return mylist;
	}

}
