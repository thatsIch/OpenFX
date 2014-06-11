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
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ClassSelection;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.CollectiveNetworkBinaryClassifiers;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.INBC;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.CreateTrainedPreProcessorCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.service.TrainedPreProcessorFileStorageService;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author thatsIch
 * @since 10.06.2014.
 */
public class NetworkSpace implements INetworkSpace
{
	private static final int ERROR_ITERATION = 10;

	private final List<IImage> trainingImages;
	private final List<IErrorGenerator> errorGenerators;
	private final ErrorFileStorageService errorStorage;
	private final List<IFeatureExtractor> featureExtractors;
	private final List<IPreProcessor> preProcessors;
	private final TrainedPreProcessorFileStorageService preproStorage;
	private final CollectiveNetworkBinaryClassifiers cnbc;
	private final ClassSelection cs;
	private final Log log;

	@Inject
	public NetworkSpace(
		List<IImage> trainingImages,
		List<IErrorGenerator> errorGenerators,
		ErrorFileStorageService errorStorage,
		List<IFeatureExtractor> featureExtractors,
		List<IPreProcessor> preProcessors,
		TrainedPreProcessorFileStorageService preproStorage,
		List<IBinaryClassifier> binaryClassifiers,
		Log log
	)
	{
		this.trainingImages = trainingImages;
		this.errorGenerators = errorGenerators;
		this.errorStorage = errorStorage;
		this.featureExtractors = featureExtractors;
		this.preProcessors = preProcessors;
		this.preproStorage = preproStorage;
		this.cnbc = new CollectiveNetworkBinaryClassifiers(binaryClassifiers, log);
		this.cs = new ClassSelection();
		this.log = log;
	}

	@Override
	public void train() throws Exception
	{
		final List<IError> errors = this.getErrors(this.trainingImages, this.errorGenerators);
		final List<IFeature> features = this.getFeatures(errors, this.featureExtractors);
		final List<ITrainedPreProcessor> trainedPreProcessors = this.getTrainedPreProcessors(features, this.preProcessors);
		final List<IFeature> preprocessedFeatures = this.getPreProcessedFeatures(features, trainedPreProcessors);

		this.cnbc.train(preprocessedFeatures);
		this.log.info("Trained CNBC with " + preprocessedFeatures + ".");

		final List<INBC> nbcs = this.cnbc.getNbcs();
		this.cs.train(nbcs);
		this.log.info("Trained Class Selection.");
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
	private List<IError> getErrors(List<IImage> images, List<IErrorGenerator> errorGenerators) throws Exception
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

			final CreateErrorCommand create = new CreateErrorCommand(randomImage, randomErrorGen, randomSmooth, randomThreshold, randomDenoising, this.errorStorage);
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

	private List<ITrainedPreProcessor> getTrainedPreProcessors(
		List<IFeature> features, List<IPreProcessor> preProcessors
	) throws Exception
	{
		final List<ITrainedPreProcessor> trainedPreProcessors = new LinkedList<>();

		for (IPreProcessor preProcessor : preProcessors)
		{
			for (IFeature feature : features)
			{
				final CreateTrainedPreProcessorCommand command = new CreateTrainedPreProcessorCommand(preProcessor, feature, this.preproStorage);
				final ITrainedPreProcessor trained = command.call();
				trainedPreProcessors.add(trained);
			}
		}

		return trainedPreProcessors;
	}

	private List<IFeature> getPreProcessedFeatures(
		List<IFeature> features, List<ITrainedPreProcessor> trainedPreProcessors
	)
	{
		final List<IFeature> preprocessedFeatures = new LinkedList<>();

		trainedPreProcessors.forEach(trained -> {
			features.forEach(feature -> {
				feature.vectors().forEach(vector -> {

				});
			});
		});
		for (ITrainedPreProcessor trainedPreProcessor : trainedPreProcessors)
		{
			for (IFeature feature : features)
			{
				trainedPreProcessor.
			}
		}

		return preprocessedFeatures;
	}

	@Override
	public String predict(IError error) throws Exception
	{
		final List<IFeature> features = new LinkedList<>();
		for (IFeatureExtractor featureExtractor : this.featureExtractors)
		{
			final CreateExtractedFeatureCommand created = new CreateExtractedFeatureCommand(error, featureExtractor, 31);
			final IFeature feature = created.call();
			features.add(feature);
		}

		final List<Pair<String, Double>> predict = this.cnbc.predict(features);
		return this.cs.predict(predict);
	}
}
