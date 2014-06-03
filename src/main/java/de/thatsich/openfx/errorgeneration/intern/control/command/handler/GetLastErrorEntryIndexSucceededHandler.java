package de.thatsich.openfx.errorgeneration.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.openfx.errorgeneration.api.model.IErrorEntries;
import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for getting the last error entry index
 *
 * @author Minh
 */
public class GetLastErrorEntryIndexSucceededHandler extends ACommandHandler<Integer>
{
	@Inject private IErrorEntries errorEntryList;

	@Override
	public void handle(Integer value)
	{
		final ErrorEntry selectedErrorEntry = this.errorEntryList.errorEntries().get(value);
		this.errorEntryList.selectedErrorEntry().set(selectedErrorEntry);
		this.log.info("Set last selected error entry index in Model.");
	}
}
