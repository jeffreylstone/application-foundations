/**
 * AcmDaoPropertyFileImpl.java
 * Property File based implementation of AcmDao
 * Jeff Stone (jeffreylstone)
 * 20180705
 * 
 */
package org.jsquare.appconfigmgmt.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jsquare.appconfigmgmt.dao.AcmDao;

/**
 * @author jeffreylstone
 *
 */
public class AcmDaoPropertyFileImpl implements AcmDao {
	
	// private static final Logger LOGGER = 
	//	LoggerFactory.getLogger(AcmDaoPropertyFileImpl.class);

	// Members
	private Collection<String> configurationNames;
	private Map<String, Map<String, String>> propertySets;
	
	// API Methods
	/**
	 * @see org.jsquare.appconfigmgmt.dao.AcmDao#getConfigurationNames()
	 */
	@Override
	public Collection<String> getConfigurationNames() {
		
		if (this.configurationNames == null) {
			
			Collection<String> configurationNames = new ArrayList<>();
			InputStream in = null;
			BufferedReader inRdr = null;
			String line = null;
			
			try {
				in = this.getResourceAsStream("properties.list");
				if (null != in) {
					inRdr = new BufferedReader(new InputStreamReader(in));
					while ((line = inRdr.readLine()) != null) {
						configurationNames.add(line.trim());
					}
				}
				else {
					System.out.println("Did not find resource!");
				}
			}
			catch (IOException ioe) {
				
			}
			finally {
				if (null != inRdr) {
					try {
						inRdr.close();
					}
					catch (IOException ioe2) {
						// swallow on close()
					}
				}
			}
			
			this.configurationNames = configurationNames;
		}
		
		return this.configurationNames;
	}

	public InputStream getResourceAsStream(String name) {
//      *** The resources for this Application reside at the "ClassPath" level rather than the "Package" level.		
//		name = resolveName(name);
		ClassLoader cl = this.getClass().getClassLoader();
		if (cl == null) {
			return ClassLoader.getSystemResourceAsStream(name); // A system class.
		}
		return cl.getResourceAsStream(name);
	}

// ***This routine resolves a "Class" resource name (i.e., resource files in same package as using Class)	
//	private String resolveName(String name) {
//		if (name == null) {
//			return name;
//		}
//		if (!name.startsWith("/")) {
//			Class c = this.getClass();	// modified
//			while (c.isArray()) {
//				c = c.getComponentType();
//			}
//			String baseName = c.getName();
//			int index = baseName.lastIndexOf('.');
//			if (index != -1) {
//				name = baseName.substring(0, index).replace('.', '/') + "/" + name;
//			}
//		} else {
//			name = name.substring(1);
//		}
//		
//		
//		
//		System.out.println("Resolved Name: " + name);
//		return name;
//	}	
	
	/**
	 * @see org.jsquare.appconfigmgmt.dao.AcmDao#getProperties(java.lang.String)
	 */
	@Override
	public Map<String, String> getProperties(String propertySetName) {
		
		Map<String, String> properties = null;
		boolean cacheExists = false;
		boolean propertySetExists = false;

		// Does the propertSets "cache" exist, yet?
		if (null != this.propertySets) {
			cacheExists = true;
			
			// does propertySet exist in "cache"?
			properties = this.propertySets.get(propertySetName);
			
			if (null != properties) {
				propertySetExists = true;
			}
		}
		
		if (!propertySetExists) {
			InputStream in = null;

			try {
				in = this.getResourceAsStream(propertySetName + 
						".properties");
				if (null != in ) {
					Properties props = new Properties();
					props.load(in);
					properties = new HashMap<>();
					for (Map.Entry<Object, Object> currentEntry : 
						props.entrySet()) {
						properties.put((String) currentEntry.getKey(), 
								(String) currentEntry.getValue());
					}
					
					if (!cacheExists) {
						this.propertySets = new HashMap<>();
					}
					
					// add to "cache"
					this.propertySets.put(propertySetName, properties);
				}
				else {
					// error opening properties file
					// log the error
				}
			}
			catch (IOException ioe) {
				// error reading properties file
				// log the error
			}
			finally {
				if (null != in) {
					try {
						in.close();
					}
					catch (IOException ioe2) {
						// swallow exception on close()
					}
				}
			}
		}
		
		return properties;
	}
	
	/**
	 * @see org.jsquare.appconfigmgmt.dao.AcmDao#getProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public String getProperty(String propertySetName, String propertyName) {
		
		String propertyValue = null;
		Map<String, String> properties = null;

		properties = this.getProperties(propertySetName);
		
		if (null != properties) {
			propertyValue = properties.get(propertyName);
		}
		
		return propertyValue;
	}
}
