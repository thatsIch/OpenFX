package de.thatsich.bachelor.errorgeneration.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.errorgeneration.api.model.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.intern.control.error.ErrorEntry;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for deleting the error
 *
 * @author Minh
 */
public class CreateErrorEntrySucceededHandler extends ACommandHandler<ErrorEntry>
{

	@Inject
	private IErrorEntries errorEntryList;

	@Override
	public void handle(ErrorEntry addition)
	{
		this.errorEntryList.getErrorEntryListProperty().add(addition);
		this.log.info("Added ErrorEntry to Database.");

		this.errorEntryList.getSelectedErrorEntryProperty().set(addition);
		this.log.info("Set current to selected ErrorEntry.");
	}
}
