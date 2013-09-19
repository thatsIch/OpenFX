package de.thatsich.core.javafx;

import java.io.File;
import java.nio.file.Path;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javax.annotation.PostConstruct;

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
		
		this.chooser.setInitialDirectory(lastLocation);
		this.log.info("Set up initial directory: " + lastLocation.getAbsolutePath());
	}
	
	@PostConstruct
	public void test() {
		System.out.println("Jetzt test");
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
	// TODO change to one image filter or add an opencv one as first and let the others just be there
	private ExtensionFilter getExtensions() {	
		
		String[] extensions = {
			"*.pbm",
			"*.pgm",
			"*.ppm",
			
			"*.sr",
			"*.ras",
			
			"*.jpeg",
			"*.jpg",
			"*.jpe",
			
			"*.tiff",
			"*.tif",
			
			"*.png",
			
			"*.bmp",
			
			"*.jp2",
			
			"*.exr",
		};
		
		return new ExtensionFilter("OpenCV Supported", extensions);
	}
}
