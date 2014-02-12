package de.thatsich.bachelor.imageprocessing.intern.command.handler;

import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for initializing all images entries
 * 
 * @author Minh
 */
public class InitImageEntryListSucceededHandler extends ACommandHandler<List<ImageEntry>> {
	
	@Inject private IImageEntries imageEntries;
	
	@Override
	public void handle(List<ImageEntry> value) {
		imageEntries.imageEntriesmageEntryListProperty().get().addAll(value);
		log.info("Added ImageEntryList to Model.");
	}
}
