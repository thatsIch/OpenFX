package de.thatsich.openfx.preprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessors;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;


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
		final IPreProcessor selected = this.pps.get().get(value);
		this.pps.selected().set(selected);
		this.log.info("Set LastPreProcessorIndex in Model.");
	}
}
