package de.thatsich.openfx.network.intern.control.prediction;

import com.google.inject.Inject;
import de.thatsich.core.Log;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.CreateErrorCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorFileStorageService;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.CreateExtractedFeatureCommand;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.network.api.control.entity.INetworkSpace;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.CollectiveNetworkBinaryClassifiers;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ICNBC;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.CreateTrainedPreProcessorCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.service.TrainedPreProcessorFileStorageService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author thatsIch
 * @since 10.06.2014.
 */
public class NetworkSpace implements INetworkSpace
{
	private static final int ERROR_ITERATION = 10;
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");


	@Inject private Log log;

	@Override
	public ITrainedNetwork train(List<IImage> trainingImages, List<IErrorGenerator> errorGenerators, ErrorFileStorageService errorStorage, List<IFeatureExtractor> featureExtractors, List<IPreProcessor> preProcessors, TrainedPreProcessorFileStorageService preproStorage, List<IBinaryClassifier> binaryClassifiers) throws Exception
	{
		final long startTime = System.currentTimeMillis();
		final List<IError> errors = this.getErrors(trainingImages, errorGenerators, errorStorage);
		final List<IFeature> features = this.getFeatures(errors, featureExtractors);
		final List<ITrainedPreProcessor> trainedPreProcessors = this.getTrainedPreProcessors(features, preProcessors, preproStorage);
		final List<IFeature> preprocessedFeatures = this.getPreProcessedFeatures(features, trainedPreProcessors);

		final ICNBC cnbc = new CollectiveNetworkBinaryClassifiers(this.log);
		for (IFeature preprocessedFeature : preprocessedFeatures)
		{
			cnbc.addFeature(preprocessedFeature);
		}
		this.log.info("Trained CNBC with " + preprocessedFeatures + ".");
		final long stopTime = System.currentTimeMillis();

		final String creationTime = format.format(new Date());
		final String id = UUID.randomUUID().toString();
		final NetworkConfig config = new NetworkConfig(creationTime, id);
		final long trainTime = stopTime - startTime;

		return new TrainedNetwork(config, featureExtractors, trainedPreProcessors, cnbc, trainTime);
	}

	/**
	 * Generate errors based on images and generators
	 *
	 * @param images          base images
	 * @param errorGenerators generators working on the base images
	 *
	 * @return generated errors
	 *
	 * @throws Exception when errors cant be saved
	 */
	private List<IError> getErrors(List<IImage> images, List<IErrorGenerator> errorGenerators, ErrorFileStorageService storage) throws Exception
	{
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		final int imageBound = images.size();
		final int errorGenBound = errorGenerators.size();

		final List<IError> errors = new LinkedList<>();
		for (int index = 0; index < ERROR_ITERATION; index++)
		{
			final int randomImageIndex = random.nextInt(imageBound);
			final int randomErrorGenIndex = random.nextInt(errorGenBound);

			final IImage randomImage = images.get(randomImageIndex);
			final IErrorGenerator randomErrorGen = errorGenerators.get(randomErrorGenIndex);

			final boolean randomSmooth = random.nextBoolean();
			final boolean randomThreshold = random.nextBoolean();
			final boolean randomDenoising = random.nextBoolean();

			final CreateErrorCommand create = new CreateErrorCommand(randomImage, randomErrorGen, randomSmooth, randomThreshold, randomDenoising, storage);
			final IError error = create.call();

			errors.add(error);
		}

		return errors;
	}

	/**
	 * Extracts features out of a given list of featureextractors and errors.
	 * All possible permutations are being generated
	 *
	 * @param errors            given errors
	 * @param featureExtractors given feature extractors
	 *
	 * @return list of features
	 *
	 * @throws Exception when a feature cant be stored
	 */
	private List<IFeature> getFeatures(List<IError> errors, List<IFeatureExtractor> featureExtractors) throws Exception
	{
		final List<IFeature> features = new LinkedList<>();
		for (IFeatureExtractor featureExtractor : featureExtractors)
		{
			IFeature result = null;
			for (IError error : errors)
			{
				final CreateExtractedFeatureCommand create = new CreateExtractedFeatureCommand(error, featureExtractor, 31);
				final IFeature feature = create.call();

				if (result == null)
				{
					result = feature;
				}
				else
				{
					result.merge(feature);
				}
			}
			features.add(result);
		}

		return features;
	}

	private List<ITrainedPreProcessor> getTrainedPreProcessors(List<IFeature> features, List<IPreProcessor> preProcessors, TrainedPreProcessorFileStorageService storage) throws Exception
	{
		final List<ITrainedPreProcessor> trainedPreProcessors = new LinkedList<>();

		for (IPreProcessor preProcessor : preProcessors)
		{
			for (IFeature feature : features)
			{
				final CreateTrainedPreProcessorCommand command = new CreateTrainedPreProcessorCommand(preProcessor, feature, storage);
				final ITrainedPreProcessor trained = command.call();
				trainedPreProcessors.add(trained);
			}
		}

		return trainedPreProcessors;
	}

	private List<IFeature> getPreProcessedFeatures(List<IFeature> features, List<ITrainedPreProcessor> trainedPreProcessors)
	{
		final List<IFeature> preprocessedFeatures = new LinkedList<>();

		for (ITrainedPreProcessor trainedPreProcessor : trainedPreProcessors)
		{
			final List<IFeature> preprocess = trainedPreProcessor.preprocess(features);
			preprocessedFeatures.addAll(preprocess);
		}

		return preprocessedFeatures;
	}

}
