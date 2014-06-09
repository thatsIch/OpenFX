package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.preprocessing.api.control.entity.IPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.service.PreProcessingFileStorageService;

import java.util.List;


public class InitPreProcessingsCommand extends ACommand<List<IPreProcessing>>
{
	private final PreProcessingFileStorageService storage;

	@Inject
	protected InitPreProcessingsCommand(PreProcessingFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<IPreProcessing> call() throws Exception
	{
		return this.storage.init();
	}
}
