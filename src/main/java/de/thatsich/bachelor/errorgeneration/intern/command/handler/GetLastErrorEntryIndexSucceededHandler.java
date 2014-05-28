package de.thatsich.bachelor.errorgeneration.intern.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for getting the last error entry index
 *
 * @author Minh
 */
public class GetLastErrorEntryIndexSucceededHandler extends ACommandHandler<Integer>
{

	@Inject
	private IErrorEntries errorEntryList;

	@Override
	public void handle(Integer value)
	{
		final ErrorEntry selectedErrorEntry = this.errorEntryList.getErrorEntryListProperty().get(value);
		this.errorEntryList.getSelectedErrorEntryProperty().set(selectedErrorEntry);
		this.log.info("Set last selected error entry index in Model.");
	}
}
