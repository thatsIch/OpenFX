package de.thatsich.openfx.preprocessing.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.preprocessing.api.control.entity.IPreProcessing;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessings;


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
		this.preProcessings.list().add(value);
		this.log.info("Added " + value + " to " + this.preProcessings + ".");

		this.preProcessings.selected().set(value);
		this.log.info("Selected " + value + ".");
	}
}
