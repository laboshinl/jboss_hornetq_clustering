package com.nokia.oss;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.nokia.ison.JsonSourceDestDN;
import com.nokia.ison.MessageWrapperItf;
import com.nokia.ison.dispatch.SendMessageItf;

/**
 * To test the MroController Logic
 * @author acp
 *
 */
public class MroControllerTest {


	@Test
	/*
	 * Testing the Split of DN's
	 */
	public void testSplitDNs(){

		MroController controller = new MroController();

		List<String> dnlist = new ArrayList<String>();

		for(int i=0; i<30;++i){

			dnlist.add("PLMN_PLMN/LNBTS-" + i + "/LNCEL-"+ i);
		}

		List<String> targetDnList = new ArrayList<String>();

		for(int i=30; i<60;++i){

			targetDnList.add("PLMN_PLMN/LNBTS-" + i + "/LNCEL-"+ i);
		}

		final int  batchsize=4;
		SendMessageItf sendmesage = new SendMessageItf() {

			@Override
			public void sendMessage(MessageWrapperItf message) {
				System.out.println("Going to Send a Message");
				String payload =message.getPayload();
				Assert.assertTrue(!payload.isEmpty());
				JsonSourceDestDN output=new Gson().fromJson(payload, JsonSourceDestDN.class);
				Assert.assertTrue(output.sourceDNList.size()<=batchsize);

			}
		};
		controller.splitDNsandSendinBatchesOf(batchsize, dnlist,targetDnList,sendmesage);

	}


	@Test
	public void testSplitDNs2(){

		MroController controller = new MroController();

		List<String> dnlist = new ArrayList<String>();

		for(int i=0; i<30;++i){

			dnlist.add("PLMN_PLMN/LNBTS-" + i + "/LNCEL-"+ i);
		}

		List<String> targetDnList = new ArrayList<String>();

		for(int i=30; i<60;++i){

			targetDnList.add("PLMN_PLMN/LNBTS-" + i + "/LNCEL-"+ i);
		}
		final int  batchsize=35;
		SendMessageItf sendmesage = new SendMessageItf() {

			@Override
			public void sendMessage(MessageWrapperItf message) {
				System.out.println("Going to Send a Message");
				String payload =message.getPayload();
				Assert.assertTrue(!payload.isEmpty());
				//Type collectionType = new TypeToken<List<String>>(){}.getType();
				JsonSourceDestDN output=new Gson().fromJson(payload, JsonSourceDestDN.class);
				Assert.assertTrue(output.sourceDNList.size()<=batchsize);
				System.out.println(payload);

			}
		};
		controller.splitDNsandSendinBatchesOf(batchsize, dnlist,targetDnList,sendmesage);

	}
}
