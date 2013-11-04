package de.thatsich.bachelor.imageprocessing.restricted.controller;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.imageprocessing.api.core.IImageState;
import de.thatsich.bachelor.imageprocessing.restricted.command.provider.IImageCommandProvider;
import de.thatsich.core.Log;

/**
 * ImageFileChooser
 * 
 * Injected Singleton Class.
 * Set up automatically the last selected location
 * and enables only OpenCV Supported images.
 * 
 * @author Tran Minh Do
 *
 */
@Singleton
public class ImageFileChooser {

	/**
	 * JavaFX FileChooser
	 */
	private final FileChooser chooser = new FileChooser();
	
	/**
	 * Title of ImageFileChooser.
	 */
	final private static String TITLE = "Add Image File";
	
	/**
	 * Injected Config to retrieve last location.
	 */
	@Inject private IImageCommandProvider commander;
	
	/**
	 * Injected Logger
	 */
	@Inject private Log log;
	
	/**
	 * Injected Model to retrieve the last Location
	 */
	@Inject private IImageState imageState;
	
	@Inject private void init() {
		this.chooser.getExtensionFilters().addAll(this.getExtensions());
		this.log.info("Set up OpenCV-supported extensions.");
		
		this.chooser.setTitle(TITLE);
		this.log.info("Set up Title: " + TITLE);
	}
	
	/**
	 * Show the Open Dialog
	 * 
	 * @return null if no file selected else the selected File converted to Path
	 */
	public List<Path> show() {
		this.chooser.setInitialDirectory(this.imageState.getLastLocation().toFile());
		
		List<File> result = this.chooser.showOpenMultipleDialog(null);
		this.log.info("Showing Open Dialog.");

		if (result == null || result.isEmpty()) {
			this.log.warning("No File selected.");
			return null;
		}
		else {
			final List<Path> pathList = FXCollections.observableArrayList();
			final String parent = result.get(0).getParent();
			
			for (final File file : result) {
				pathList.add(file.toPath());
			}
			
			this.commander.createSetLastLocationCommand(parent).start();
			this.log.info("Stored last DirectoryPath: " + parent);
			
			return pathList;	
		}
	}
	
	/**
	 * Results in an Array of File-Extensions used by OpenCV and JavaFX (jpg, png)
	 * 
	 * @return all OpenCV-supported Image-extensions
	 */
	private ExtensionFilter getExtensions() {	
		String[] extensions = {
			"*.jpeg",
			"*.jpg",
			"*.jpe",

			"*.png",
		};
		this.log.info("Created Extension Array: " + extensions.length + ".");
		
		return new ExtensionFilter("OpenCV Supported", extensions);
	}
}
