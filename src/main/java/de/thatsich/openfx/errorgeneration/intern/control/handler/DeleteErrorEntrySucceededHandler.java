package de.thatsich.openfx.errorgeneration.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.openfx.errorgeneration.api.model.IErrorEntries;
import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;
import de.thatsich.core.javafx.ACommandHandler;
import javafx.collections.ObservableList;

/**
 * Handler for what should happen if the Command was successfull
 * for deleting the error
 *
 * @author Minh
 */
public class DeleteErrorEntrySucceededHandler extends ACommandHandler<ErrorEntry>
{
	@Inject private IErrorEntries errorEntryList;

	@Override
	public void handle(ErrorEntry value)
	{
		final ObservableList<ErrorEntry> entryList = this.errorEntryList.errorEntries();
		entryList.remove(value);
		this.log.info("Removed ErrorEntry from Database.");

		if (entryList.size() > 0)
		{
			final ErrorEntry first = entryList.get(0);
			this.errorEntryList.selectedErrorEntry().set(first);
			this.log.info("Reset Selection to first ErrorEntry.");
		}
		else
		{
			this.errorEntryList.selectedErrorEntry().set(null);
			this.log.info("Reset Selection to null.");
		}
	}
}