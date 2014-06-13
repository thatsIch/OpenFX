package de.thatsich.openfx.preprocessed.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessed.intern.control.command.service.PreProcessedFeatureFileStorageService;

import java.util.List;


public class InitPreProcessedsCommand extends ACommand<List<IFeature>>
{
	private final PreProcessedFeatureFileStorageService storage;

	@Inject
	protected InitPreProcessedsCommand(PreProcessedFeatureFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<IFeature> call() throws Exception
	{
		return this.storage.init();
	}
}
