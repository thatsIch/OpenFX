package de.thatsich.openfx.errorgeneration.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.model.IErrors;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the error entry get
 *
 * @author Minh
 */
public class InitErrorEntryListSucceededHandler extends ACommandHandler<List<IError>>
{
	@Inject private IErrors errors;

	@Override
	public void handle(List<IError> entryList)
	{
		this.errors.list().addAll(entryList);
		this.log.info("Initialized all ErrorEntries into Model.");
	}
}