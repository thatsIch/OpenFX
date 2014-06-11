package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.service.TrainedPreProcessorFileStorageService;

import java.util.List;


public class InitPreProcessingsCommand extends ACommand<List<ITrainedPreProcessor>>
{
	private final TrainedPreProcessorFileStorageService storage;

	@Inject
	protected InitPreProcessingsCommand(TrainedPreProcessorFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<ITrainedPreProcessor> call() throws Exception
	{
		return this.storage.init();
	}
}
