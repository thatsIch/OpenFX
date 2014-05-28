package de.thatsich.bachelor.preprocessing.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.api.models.IPreProcessings;
import de.thatsich.core.javafx.ACommandHandler;


/**
 * Handler for what should happen if the Command was successfull
 * for training PreProcessor
 *
 * @author Minh
 */
public class TrainPreProcessorSucceededHandler extends ACommandHandler<IPreProcessing>
{
	@Inject
	private IPreProcessings preProcessings;

	@Override
	public void handle(IPreProcessing value)
	{
		this.preProcessings.getPreProcessingListProperty().add(value);
		this.log.info("Added " + value + " to " + preProcessings + ".");

		this.preProcessings.setSelectedPreProcessing(value);
		this.log.info("Selected " + value + ".");
	}
}
