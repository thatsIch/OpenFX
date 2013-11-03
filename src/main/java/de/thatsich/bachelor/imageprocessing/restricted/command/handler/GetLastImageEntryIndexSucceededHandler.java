package de.thatsich.bachelor.imageprocessing.restricted.command.handler;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for selecting the last selected image entry
 * 
 * @author Minh
 */
public class GetLastImageEntryIndexSucceededHandler extends ACommandHandler<Integer> {

	@Inject private IImageEntries imageEntries;

	@Override
	public void handle(Integer value) {
		final ImageEntry selectedImageEntry = this.imageEntries.imageEntriesmageEntryListProperty().get(value); 
		this.imageEntries.setSelctedImageEntry(selectedImageEntry);
		log.info("Set last selected image entry index in Model.");
	}
}
