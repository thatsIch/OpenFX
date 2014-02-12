package de.thatsich.bachelor.errorgeneration.intern.controller.handler;

import javafx.collections.ObservableList;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.api.core.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for deleting the error
 * 
 * @author Minh
 */
public class DeleteErrorEntrySucceededHandler extends ACommandHandler<ErrorEntry> {

	@Inject private IErrorEntries errorEntryList;

	@Override
	public void handle(ErrorEntry value) {
		final ObservableList<ErrorEntry> entryList = this.errorEntryList.getErrorEntryListProperty();
		entryList.remove(value);
		this.log.info("Removed ErrorEntry from Database.");
		
		if (entryList.size() > 0) {
			final ErrorEntry first = entryList.get(0);
			this.errorEntryList.setSelectedErrorEntry(first);
			this.log.info("Reset Selection to first ErrorEntry.");
		} else {
			this.errorEntryList.setSelectedErrorEntry(null);
			this.log.info("Reset Selection to null.");
		}
	}
}