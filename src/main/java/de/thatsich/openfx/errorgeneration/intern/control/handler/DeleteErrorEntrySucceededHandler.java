package de.thatsich.openfx.errorgeneration.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.model.IErrors;
import javafx.collections.ObservableList;

/**
 * Handler for what should happen if the Command was successfull
 * for deleting the error
 *
 * @author Minh
 */
public class DeleteErrorEntrySucceededHandler extends ACommandHandler<IError>
{
	@Inject private IErrors errors;

	@Override
	public void handle(IError value)
	{
		final ObservableList<IError> list = this.errors.list();
		list.remove(value);
		this.log.info("Removed ErrorEntry from Database.");

		if (list.size() > 0)
		{
			final IError first = list.get(0);
			this.errors.selected().set(first);
			this.log.info("Reset Selection to first ErrorEntry.");
		}
		else
		{
			this.errors.selected().set(null);
			this.log.info("Reset Selection to null.");
		}
	}
}