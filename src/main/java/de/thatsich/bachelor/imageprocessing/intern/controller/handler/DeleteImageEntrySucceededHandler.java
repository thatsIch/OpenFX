package de.thatsich.bachelor.imageprocessing.intern.controller.handler;

import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for deleting the image out of the input directory.
 * 
 * @author Minh
 */
public class DeleteImageEntrySucceededHandler extends ACommandHandler<ImageEntry> {
	
	@Inject private IImageEntries imageEntries;
	
	@Override public void handle(ImageEntry deletion) {
		final List<ImageEntry> imageEntryList = this.imageEntries.imageEntriesmageEntryListProperty().get();
		imageEntryList.remove(deletion);
		this.log.info("Removed ImageEntry from Database.");

		if (imageEntryList.size() > 0) {
			final ImageEntry first = imageEntryList.get(0);
			this.imageEntries.setSelctedImageEntry(first);
			this.log.info("Reset Selection to the first.");
		}
		else {
			this.imageEntries.setSelctedImageEntry(null);
		}
	}
}
