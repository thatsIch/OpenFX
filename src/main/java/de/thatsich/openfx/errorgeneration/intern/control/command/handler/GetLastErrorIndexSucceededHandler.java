package de.thatsich.openfx.errorgeneration.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.model.IErrors;
import javafx.beans.property.ListProperty;

/**
 * Handler for what should happen if the Command was successfull
 * for getting the last error entry index
 *
 * @author Minh
 */
public class GetLastErrorIndexSucceededHandler extends ACommandHandler<Integer>
{
	@Inject private IErrors errorEntryList;

	@Override
	public void handle(Integer value)
	{
		final ListProperty<IError> errors = this.errorEntryList.list();
		if (value >= 0 && errors.size() > 0)
		{
			final IError selectedError = errors.get(value);
			this.errorEntryList.selected().set(selectedError);
			this.log.info("Set last selected error entry index in Model.");
		}
	}
}
