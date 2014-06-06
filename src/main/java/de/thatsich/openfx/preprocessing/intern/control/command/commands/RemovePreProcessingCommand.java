package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.service.PreProcessingFileStorageService;


public class RemovePreProcessingCommand extends ACommand<IPreProcessing>
{
	private final IPreProcessing preProcessing;
	private final PreProcessingFileStorageService storage;

	@Inject
	public RemovePreProcessingCommand(@Assisted IPreProcessing preProcessing, PreProcessingFileStorageService storage)
	{
		this.preProcessing = preProcessing;
		this.storage = storage;
	}

	@Override
	protected IPreProcessing call() throws Exception
	{
		this.storage.delete(this.preProcessing);

		return this.preProcessing;
	}
}
