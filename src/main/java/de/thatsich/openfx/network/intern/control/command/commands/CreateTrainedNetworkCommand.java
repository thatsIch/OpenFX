package de.thatsich.openfx.network.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.Log;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorFileStorageService;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.network.api.control.entity.INetworkSpace;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.intern.control.command.service.NetworkFileStorageService;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.service.TrainedPreProcessorFileStorageService;

import java.util.List;

/**
 * // TODO train network
 *
 * @author thatsIch
 * @since 01.06.2014.
 */
public class CreateTrainedNetworkCommand extends ACommand<ITrainedNetwork>
{
	private final List<IImage> images;
	private final List<IErrorGenerator> errorGenerators;
	private final List<IFeatureExtractor> featureExtractors;
	private final List<IPreProcessor> preProcessors;
	private final List<IBinaryClassifier> binaryClassifiers;

	private final INetworkSpace networkSpace;
	private final ErrorFileStorageService errorStorage;
	private final TrainedPreProcessorFileStorageService preProcessorFileStorageService;
	private final NetworkFileStorageService networkStorage;

	private final Log log;

	@Inject
	public CreateTrainedNetworkCommand(@Assisted List<IImage> images, @Assisted List<IErrorGenerator> errorGenerators, @Assisted List<IFeatureExtractor> featureExtractors, @Assisted List<IPreProcessor> preProcessors, @Assisted List<IBinaryClassifier> binaryClassifiers, INetworkSpace networkSpace, ErrorFileStorageService errorStorage, TrainedPreProcessorFileStorageService preProcessorFileStorageService, NetworkFileStorageService networkStorage, Log log)
	{
		this.images = images;
		this.errorGenerators = errorGenerators;
		this.featureExtractors = featureExtractors;
		this.preProcessors = preProcessors;
		this.binaryClassifiers = binaryClassifiers;
		this.networkSpace = networkSpace;
		this.errorStorage = errorStorage;
		this.preProcessorFileStorageService = preProcessorFileStorageService;

		this.networkStorage = networkStorage;
		this.log = log;
	}

	@Override
	protected ITrainedNetwork call() throws Exception
	{
		assert this.images != null;

		final ITrainedNetwork trainedNetwork = this.networkSpace.train(this.images, this.errorGenerators, this.errorStorage, this.featureExtractors, this.preProcessors, this.preProcessorFileStorageService, this.binaryClassifiers);
		this.log.info("Trained the network.");

		this.networkStorage.create(trainedNetwork);
		this.log.info("Saved network to filestorage.");

		return trainedNetwork;
	}
}
