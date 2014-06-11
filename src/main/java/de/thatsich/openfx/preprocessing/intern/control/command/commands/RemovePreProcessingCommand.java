package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.service.TrainedPreProcessorFileStorageService;


public class RemovePreProcessingCommand extends ACommand<ITrainedPreProcessor>
{
	private final ITrainedPreProcessor preProcessing;
	private final TrainedPreProcessorFileStorageService storage;

	@Inject
	public RemovePreProcessingCommand(
		@Assisted ITrainedPreProcessor preProcessing,
		TrainedPreProcessorFileStorageService storage
	)
	{
		this.preProcessing = preProcessing;
		this.storage = storage;
	}

	@Override
	protected ITrainedPreProcessor call() throws Exception
	{
		this.storage.delete(this.preProcessing);

		return this.preProcessing;
	}
}
