package de.thatsich.bachelor.errorgeneration.intern.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.core.javafx.ACommandHandler;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the error entry list
 *
 * @author Minh
 */
public class InitErrorEntryListSucceededHandler extends ACommandHandler<List<ErrorEntry>>
{

	@Inject
	private IErrorEntries errorEntryList;

	@Override
	public void handle(List<ErrorEntry> entryList)
	{
		this.errorEntryList.getErrorEntryListProperty().addAll(entryList);
		this.log.info("Initialized all ErrorEntries into Model.");
	}
}