package de.thatsich.openfx.preprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.api.model.ITrainedPreProcessors;


/**
 * Handler for what should happen if the Command was successfull
 * for getting the LastPreProcessingIndex
 *
 * @author Minh
 */
public class GetLastPreProcessingIndexSucceededHandler extends ACommandHandler<Integer>
{
	@Inject
	private ITrainedPreProcessors pps;

	@Override
	public void handle(Integer value)
	{
		if (value >= 0 && this.pps.list().size() > 0)
		{
			final ITrainedPreProcessor selected = this.pps.list().get(value);
			this.pps.selected().set(selected);
			this.log.info("Set last selected " + selected + " in Model.");
		}
	}
}
