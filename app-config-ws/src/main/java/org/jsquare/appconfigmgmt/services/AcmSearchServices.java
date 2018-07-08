/**
 * AcmSearchServices.java
 * REST services for searching/retrieving Application Configurations and their 
 *  properties
 * Jeff Stone (jeffreylstone)
 * 20180702
 * 
 */
package org.jsquare.appconfigmgmt.services;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jsquare.appconfigmgmt.dao.AcmDao;

/**
 * @author jeffreylstone
 *
 */
public class AcmSearchServices {

//	private final Logger LOGGER = 
//		LoggerFactory.getLogger(AcmSearchServices.class);

	@Inject
	AcmDao dao;
	
	@GET
	@Path("/get-configurations")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConfigurations() {
		return Response.ok(dao.getConfigurationNames()).build();
	}
	
	@GET
	@Path("/get-property/{propertySetName}/{propertyName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProperty(@PathParam("propertySetName") String propertySetName, @PathParam("propertyName") String propertyName) {
		return Response.ok(dao.getProperty(propertySetName, propertyName)).build();
	}
	
	@GET
	@Path("/get-properties/{propertySetName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProperties(@PathParam("propertySetName") String propertySetName) {
		return Response.ok(dao.getProperties(propertySetName)).build();
	}
}
