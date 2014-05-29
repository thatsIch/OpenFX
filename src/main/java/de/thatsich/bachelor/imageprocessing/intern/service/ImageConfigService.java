package de.thatsich.bachelor.imageprocessing.intern.service;

import de.thatsich.core.AConfigurationService;


/**
 * ConfigurationService just to handle
 * the information used by ImageProcessing
 *
 * @author Tran Minh Do
 */
public class ImageConfigService extends AConfigurationService
{
	// IDs
	private static final String LAST_LOCATION = "last_location";
	private static final String LAST_IMAGE_INDEX = "last_image";

	// Defaults
	private static final String DEFAULT_LAST_LOCATION = System.getProperty("user.home");
	private static final int DEFAULT_LAST_IMAGE_INDEX = 0;

	// Getter
	public String getLastLocationString()
	{ return super.get(LAST_LOCATION, DEFAULT_LAST_LOCATION); }

	// Setter
	public void setLastLocationString(String lastLocation)
	{ super.set(LAST_LOCATION, lastLocation); }

	public int getLastImageIndexInt() { return super.get(LAST_IMAGE_INDEX, DEFAULT_LAST_IMAGE_INDEX); }

	public void setLastImageIndexInt(int lastImageIndex) { super.set(LAST_IMAGE_INDEX, lastImageIndex); }
}
