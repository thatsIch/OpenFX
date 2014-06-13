package de.thatsich.openfx.preprocessed.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessed.intern.control.command.service.PreProcessedFeatureFileStorageService;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;

/**
 * @author thatsIch
 */
public class CreatePreProcessedFeatureCommand extends ACommand<IFeature>
{
	// Properties
	private final ITrainedPreProcessor preProcessor;
	private final IFeature feature;
	private final PreProcessedFeatureFileStorageService storage;

	@Inject
	public CreatePreProcessedFeatureCommand(@Assisted ITrainedPreProcessor preProcessor, @Assisted IFeature feature, PreProcessedFeatureFileStorageService storage)
	{
		this.preProcessor = preProcessor;
		this.feature = feature;
		this.storage = storage;
	}

	@Override
	public IFeature call() throws Exception
	{
		final IFeature preprocessedFeature = this.preProcessor.preprocess(this.feature);
		this.log.info("PreProcessed Feature to " + preprocessedFeature);

		this.storage.create(preprocessedFeature);
		this.log.info("Saved File to FileSystem.");

		return preprocessedFeature;
	}
}
