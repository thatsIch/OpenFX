package de.thatsich.openfx.preprocessing.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.api.model.ITrainedPreProcessors;


/**
 * Handler for what should happen if the Command was successfull
 * for training PreProcessor
 *
 * @author Minh
 */
public class TrainPreProcessorSucceededHandler extends ACommandHandler<ITrainedPreProcessor>
{
	@Inject
	private ITrainedPreProcessors preProcessings;

	@Override
	public void handle(ITrainedPreProcessor value)
	{
		this.preProcessings.list().add(value);
		this.log.info("Added " + value + " to " + this.preProcessings + ".");

		this.preProcessings.selected().set(value);
		this.log.info("Selected " + value + ".");
	}
}
