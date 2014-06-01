package de.thatsich.bachelor.preprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.preprocessing.api.control.IPreProcessing;
import de.thatsich.bachelor.preprocessing.api.model.IPreProcessings;
import de.thatsich.core.javafx.ACommandHandler;


/**
 * Handler for what should happen if the Command was successfull
 * for getting the LastPreProcessingIndex
 *
 * @author Minh
 */
public class GetLastPreProcessingIndexSucceededHandler extends ACommandHandler<Integer>
{
	@Inject
	private IPreProcessings pps;

	@Override
	public void handle(Integer value)
	{
		if (value >= 0)
		{
			final IPreProcessing selected = this.pps.getPreProcessingListProperty().get(value);
			this.pps.setSelectedPreProcessing(selected);
			log.info("Set last selected " + selected + " in Model.");
		}
	}
}
