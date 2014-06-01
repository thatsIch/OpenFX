package de.thatsich.bachelor.preprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.preprocessing.api.model.IPreProcessors;
import de.thatsich.bachelor.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.core.javafx.ACommandHandler;


/**
 * Handler for what should happen if the Command was successful
 * for getting LastPreProcessorIndex
 *
 * @author Minh
 */
public class GetLastPreProcessorIndexSucceededHandler extends ACommandHandler<Integer>
{
	@Inject
	private IPreProcessors pps;

	@Override
	public void handle(Integer value)
	{
		final IPreProcessor selected = this.pps.getPreProcessorListProperty().get(value);
		this.pps.setSelectedPreProcessor(selected);
		this.log.info("Set LastPreProcessorIndex in Model.");
	}
}
