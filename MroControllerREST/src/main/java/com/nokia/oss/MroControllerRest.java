package com.nokia.oss;

import java.util.Hashtable;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.nokia.ison.FeedbackType;


@Stateless
@Path("sessionbean")
public class MroControllerRest  {

	//@EJB
	MroControllerItf mroController;

	/**
	 * Default constructor. 
	 */
	public MroControllerRest() {

	}


	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test( ){

		return "Hello from Mro SessionBean Jar ";
	}

	@GET
	@Path("/startmro")
	@Produces(MediaType.TEXT_PLAIN)
	public String startMro(@QueryParam("source_scope") String source_scopename,
			@QueryParam("target_scope") String target_scopename){
		String output="ERROR";
		System.out.println("Source ScopeName received= " +source_scopename+ ", Target ScopeName= " + target_scopename);
		try {
			final Hashtable jndiProperties = new Hashtable();
			jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			final javax.naming.Context context = new InitialContext(jndiProperties);
			mroController =(MroControllerItf)context.lookup("java:app/MroController/MroController!com.nokia.oss.MroControllerItf");
			FeedbackType result=mroController.startMroforScope(source_scopename,target_scopename);
			output= " Strated MRO for Scope,Feedback " + result.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
		
	}

}
