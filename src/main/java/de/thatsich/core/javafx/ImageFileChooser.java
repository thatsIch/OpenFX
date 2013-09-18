package de.thatsich.core.javafx;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.service.IConfigService;
import de.thatsich.core.Log;

/**
 * 
 * @author Tran Minh Do
 *
 */
@Singleton
public class ImageFileChooser {

	/**
	 * JavaFX FileChooser
	 */
	private FileChooser chooser;
	
	/**
	 * Title of ImageFileChooser.
	 */
	final private static String TITLE = "Add Image File";
	
	/**
	 * Injected Config to retrieve last location.
	 */
	private final IConfigService config;
	
	/**
	 * Injected Logger
	 */
	private final Log log;
	
	/**
	 * 
	 */
	@Inject
	public ImageFileChooser(Log log, IConfigService config) {
		this.chooser = new FileChooser();
		this.log = log;
		this.config = config;
		
		this.chooser.getExtensionFilters().addAll(this.getExtensions());
		this.log.info("Set up OpenCV-supported extensions.");
		
		this.chooser.setTitle(TITLE);
		this.log.info("Set up Title: " + TITLE);
		
		File lastLocation = new File(this.config.getLastLocationString());
		if (!lastLocation.isDirectory()) {
			this.log.warning("Last Location is invalid, loading user dir.");
			lastLocation = new File(System.getProperty("user.home"));
		}
		
//		System.out.println(this.config.getLastLocationString());
		this.chooser.setInitialDirectory(lastLocation);
		this.log.info("Set up initial directory: " + lastLocation.getAbsolutePath());
	}

	
	public Path show() {
		File result = this.chooser.showOpenDialog(null);

		if (result == null) {
			this.log.warning("No File selected.");
			return null;
		}
		
		this.config.setLastLocationString(result.getParent());
		this.log.info("Stored last DirectoryPath: " + result.getParent());
		
		return result.toPath();
	}
	
	/**
	 * 
	 * @return
	 */
	private List<ExtensionFilter> getExtensions() {	
		List<ExtensionFilter> extensionFilters = new ArrayList<ExtensionFilter>(7);
		
		List<String> portableImages = new ArrayList<String>(3);
		portableImages.add("*.pbm");
		portableImages.add("*.pgm");
		portableImages.add("*.ppm");
		
		List<String> sunRaster = new ArrayList<String>(2);
		sunRaster.add("*.sr");
		sunRaster.add("*.ras");
		
		List<String> jpeg = new ArrayList<String>(3);
		jpeg.add("*.jpeg");
		jpeg.add("*.jpg");
		jpeg.add("*.jpe");
		
		List<String> tiff = new ArrayList<String>(2);
		tiff.add("*.tiff");
		tiff.add("*.tif");
		
		extensionFilters.add(new ExtensionFilter("Windows Bitmap (bmp)", "*.bmp"));
		extensionFilters.add(new ExtensionFilter("Portable Image (pbm, pgm, ppm)", portableImages));
		extensionFilters.add(new ExtensionFilter("JPEG (jpeg, jpg, jpe)", jpeg));
		extensionFilters.add(new ExtensionFilter("JPEG 2000 (jp2)", "*.jp2"));
		extensionFilters.add(new ExtensionFilter("TIFF (jpeg, jpg, jpe)", tiff));
		extensionFilters.add(new ExtensionFilter("Portable Network Graphics (png)", "*.png"));
		extensionFilters.add(new ExtensionFilter("OpenEXR (exr)", "*.exr"));
		
		return extensionFilters;
	}
}
