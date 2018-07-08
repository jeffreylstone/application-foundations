/**
 * ApplicationConfigurationManagement.java
 * Application Class for this System 
 * (Application Configuration Management (through REST services))
 * Jeff Stone (jeffreylstone)
 * 20180702
 * 
 */
package org.jsquare.appconfigmgmt.application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author jeffreylstone
 *
 */
@ApplicationPath("/rest")
public class ApplicationConfigurationManagement extends Application {

	/**
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override
	public Set<Class<?>> getClasses() {

		return new HashSet<Class<?>>(Arrays.asList(org.jsquare.appconfigmgmt.services.AcmSearchServices.class));
	}
}