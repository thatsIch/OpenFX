package de.thatsich.openfx.preprocessed.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessed.intern.control.command.service.PreProcessedFeatureFileStorageService;


public class RemovePreProcessedFeatureCommand extends ACommand<IFeature>
{
	private final IFeature preProcessedFeature;
	private final PreProcessedFeatureFileStorageService storage;

	@Inject
	public RemovePreProcessedFeatureCommand(@Assisted IFeature preProcessedFeature, PreProcessedFeatureFileStorageService storage)
	{
		this.preProcessedFeature = preProcessedFeature;
		this.storage = storage;
	}

	@Override
	protected IFeature call() throws Exception
	{
		this.storage.delete(this.preProcessedFeature);

		return this.preProcessedFeature;
	}
}
