package de.thatsich.bachelor.imageprocessing.restricted.services;

import de.thatsich.core.AConfiguration;


/**
 * 
 * @author Tran Minh Do
 *
 */
public class ImageConfigService extends AConfiguration {

	/**
	 * 
	 */
	private static final String LAST_LOCATION = "last_location";
	private static final String LAST_IMAGE_INDEX = "last_image";
	
	/**
	 * 
	 */
	private static final String DEFAULT_LAST_LOCATION = System.getProperty("user.home");
	private static final int DEFAULT_LAST_IMAGE_INDEX = 0;
	
	// ================================================== 
	// Getter Implementation 
	// ==================================================
	/**
	 * 
	 */
	public String getLastLocationString() {
		return super.get(LAST_LOCATION, DEFAULT_LAST_LOCATION);
	}
	
	/**
	 * 
	 */
	public int getLastImageIndexInt() {
		return super.get(LAST_IMAGE_INDEX, DEFAULT_LAST_IMAGE_INDEX);
	}
	
	// ================================================== 
	// Setter Implementation 
	// ==================================================
	/**
	 * 
	 */
	public void setLastLocationString(String lastLocation) {
		super.set(LAST_LOCATION, lastLocation);
	}

	/**
	 * 
	 */
	public void setLastImageIndexInt(int lastImageIndex) {
		super.set(LAST_IMAGE_INDEX, lastImageIndex);
	}
}