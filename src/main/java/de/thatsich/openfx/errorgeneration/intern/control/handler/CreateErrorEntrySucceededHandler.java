package de.thatsich.openfx.errorgeneration.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.model.IErrors;

/**
 * Handler for what should happen if the Command was successfull
 * for deleting the error
 *
 * @author Minh
 */
public class CreateErrorEntrySucceededHandler extends ACommandHandler<IError>
{
	@Inject private IErrors errorEntryList;

	@Override
	public void handle(IError addition)
	{
		this.errorEntryList.list().add(addition);
		this.log.info("Added ErrorEntry to Database.");

		this.errorEntryList.selected().set(addition);
		this.log.info("Set current to selected ErrorEntry.");
	}
}
