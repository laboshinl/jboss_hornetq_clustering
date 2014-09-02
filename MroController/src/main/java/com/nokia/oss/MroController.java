package com.nokia.oss;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.google.gson.Gson;
import com.nokia.ison.CommandType;
import com.nokia.ison.FeedbackType;
import com.nokia.ison.JsonSourceDestDN;
import com.nokia.ison.MessageType;
import com.nokia.ison.MessageWrapper;
import com.nokia.ison.dispatch.SendtoExecutor;
import com.nokia.ison.dispatch.SendMessageItf;
import com.nokia.ison.external.StubScopeService;

@Local
@Stateless
@EJB(beanInterface = MroControllerItf.class, name = "MroController")
public class MroController implements MroControllerItf {

	/**
	 * This will be the entry point for MRO
	 * 
	 * @param scopename
	 * @return
	 */
	public FeedbackType startMroforScope(String sourceScopeName,
			String targetScopeName) {

		// Get the DN's for the Target Scope
		List<String> targetdnlist = StubScopeService
				.getTargetDNsforScopeName(targetScopeName);
		// List<String> filteredList =filterDNs(sourcednlist,targetdnlist);
		splitDNs(sourceScopeName, targetScopeName);
		return FeedbackType.SUCCEESS;
	}

	/*
	 * //filter DN's protected List<String> filterDNs(List<String>
	 * sourcednlist,final List<String> targetdnlist){ //dont want to make a copy
	 * here of the list sourcednlist.retainAll(targetdnlist); return
	 * sourcednlist;
	 * 
	 * }
	 */

	// Split the DN's depending on the number of Nodes
	protected void splitDNs(String sourcescopename, String targetscopename) {

		// Get the DN's for the Source Scope
		List<String> sourcednlist = StubScopeService
				.getSourceDNsforScopeName(sourcescopename);
		// Split the DN's and send it to MDBS's
		List<String> targetdnlist = StubScopeService
				.getTargetDNsforScopeName(targetscopename);
		SendMessageItf messageSender = new SendtoExecutor();
		splitDNsandSendinBatchesOf(1000, sourcednlist,targetdnlist, messageSender);

	}

	/**
	 * Method to split the DN's and Send it in Batches (in Round robin manner to
	 * MROExecutors)
	 * 
	 * @param batchsize
	 * @param sourcednList
	 * @param sendmsg
	 * @param targetScopeName
	 */
	protected void splitDNsandSendinBatchesOf(int batchsize,
			List<String> sourcednList,List<String> targetDnlist, SendMessageItf sendmsg) {


		int iteration = sourcednList.size() / batchsize;
		if (sourcednList.size() % batchsize > 0) {
			iteration++;
		}

		int fromIndex = 0;
		if (batchsize > sourcednList.size()) {
			batchsize = sourcednList.size();
		}
		int toIndex = batchsize;
		for (int i = 0; i < iteration; ++i) {

			List<String> tempList = sourcednList.subList(fromIndex, toIndex);
			MessageWrapper messageWrapper = new MessageWrapper();
			JsonSourceDestDN wrapper = new JsonSourceDestDN();
			wrapper.sourceDNList = tempList;
			wrapper.targentLnrelList = targetDnlist;

			messageWrapper.setPayload(new Gson().toJson(wrapper));
			messageWrapper.setMessageSenderId("Controller");
			messageWrapper.setMessageType(MessageType.COMMAND);
			messageWrapper.setCommandType(CommandType.START_NR);
			sendmsg.sendMessage(messageWrapper);

			fromIndex = toIndex;
			if (toIndex + batchsize > sourcednList.size()) {
				toIndex = sourcednList.size();
			} else {
				toIndex = toIndex + batchsize;
			}

		}

	}

}
