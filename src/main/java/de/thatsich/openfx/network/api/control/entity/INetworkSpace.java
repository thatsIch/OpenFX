package de.thatsich.openfx.network.api.control.entity;

import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorFileStorageService;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.service.TrainedPreProcessorFileStorageService;

import java.util.List;

/**
 * @author thatsIch
 * @since 10.06.2014.
 */
public interface INetworkSpace
{
	ITrainedNetwork train(List<IImage> trainingImages, List<IErrorGenerator> errorGenerators, ErrorFileStorageService errorStorage, List<IFeatureExtractor> featureExtractors, List<IPreProcessor> preProcessors, TrainedPreProcessorFileStorageService preproStorage, List<IBinaryClassifier> binaryClassifiers) throws Exception;
}
