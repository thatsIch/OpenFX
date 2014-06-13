package de.thatsich.openfx.preprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessors;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;

import java.util.List;


public class InitPreProcessorListSucceededHandler extends ACommandHandler<List<IPreProcessor>>
{
	@Inject
	private IPreProcessors pps;

	@Override
	public void handle(List<IPreProcessor> value)
	{
		this.pps.list().addAll(value);
		this.log.info("Added " + value + " to DataBase.");
	}
}
