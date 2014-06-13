package de.thatsich.openfx.preprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.api.model.ITrainedPreProcessors;

import java.util.List;


/**
 * Handler for what should happen if the Command was successfull
 * for initializing the preprocessing get
 *
 * @author Minh
 */
public class InitPreProcessingsSucceededHandler extends ACommandHandler<List<ITrainedPreProcessor>>
{
	@Inject
	private ITrainedPreProcessors pps;

	@Override
	public void handle(List<ITrainedPreProcessor> value)
	{
		this.pps.list().addAll(value);
		this.log.info("Added " + value + " with Size " + value.size() + " to DataBase.");
	}
}
