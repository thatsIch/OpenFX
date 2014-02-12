package de.thatsich.bachelor.imageprocessing.intern.controller.handler;

import java.nio.file.Path;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for adding the image to the input directory.
 * 
 * @author Minh
 */
public class AddImageEntrySucceededHandler extends ACommandHandler<Path> {
	
	@Inject private IImageEntries imageEntries;
	
	@Override public void handle(Path value) {
		final Mat copiedMat = Highgui.imread(value.toString(), 0);
		final ImageEntry copy = new ImageEntry(value, copiedMat);
		this.imageEntries.imageEntriesmageEntryListProperty().get().add(copy);
		this.log.info("Added copy to ChoiceBoxDisplayImage: " + value.toString());

		this.imageEntries.selectedImageEntryProperty().set(copy);
		this.log.info("Set currently selected Image to " + value);
	}
}
