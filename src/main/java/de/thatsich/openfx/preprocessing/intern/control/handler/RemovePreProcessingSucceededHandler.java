package de.thatsich.openfx.preprocessing.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessings;

import java.util.List;


/**
 * Handler for what should happen if the Command was successfull
 * for deleting the preprocessing
 *
 * @author Minh
 */
public class RemovePreProcessingSucceededHandler extends ACommandHandler<IPreProcessing>
{
	@Inject
	private IPreProcessings preProcessings;

	@Override
	public void handle(IPreProcessing value)
	{
		final List<IPreProcessing> list = this.preProcessings.list();
		list.remove(value);
		this.log.info("Removed " + value);

		if (list.size() > 0)
		{
			final IPreProcessing first = list.get(0);
			this.preProcessings.selected().set(first);
			this.log.info("Reset Selection to first: " + first);
		}
		else
		{
			this.preProcessings.selected().set(null);
			this.log.info("Reset selection to null");
		}
	}
}
