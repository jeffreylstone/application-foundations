/**
 * AcmDao.java
 * Application Configuration Management Data Access Object
 * Jeff Stone (jeffreylstone)
 * 20180703
 * 
 */
package org.jsquare.appconfigmgmt.dao;

import java.util.Collection;
import java.util.Map;

/**
 * @author jeffreylstone
 *
 */
public interface AcmDao {
	
	public Collection<String> getConfigurationNames();
	
	public Map<String, String> getProperties(String propertySetName);
	
	public String getProperty(String propertySetName, String propertyName);
	

	
	
	
	
	
	
	

}
