package de.thatsich.bachelor.service;

import de.thatsich.core.AConfiguration;


/**
 * 
 * @author Tran Minh Do
 *
 */
public class ConfigService extends AConfiguration implements IConfigService {

	/**
	 * 
	 */
	private static final String LAST_LOCATION = "last_location";
	
	/**
	 * 
	 */
	private static final String LAST_LOCATION_DEF = System.getProperty("user.home");
	
	/**
	 * 
	 */
	@Override
	public String getLastLocationString() {
		return super.get(LAST_LOCATION, LAST_LOCATION_DEF);
	}

	/**
	 * 
	 */
	@Override
	public void setLastLocationString(String lastLocation) {
		super.set(LAST_LOCATION, lastLocation);
	}

}
