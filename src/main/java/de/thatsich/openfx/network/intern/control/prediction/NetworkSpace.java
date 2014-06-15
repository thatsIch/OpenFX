package de.thatsich.openfx.network.intern.control.prediction;

import com.google.inject.Inject;
import de.thatsich.core.Log;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.api.model.IErrors;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.CreateErrorCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorFileStorageService;
import de.thatsich.openfx.errorgeneration.intern.control.provider.IErrorCommandProvider;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.CreateExtractedFeatureCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.provider.IFeatureCommandProvider;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureFileStorageService;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.network.api.control.entity.INetworkSpace;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ICNBC;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.INBC;
import de.thatsich.openfx.network.intern.control.provider.INetworkCommandProvider;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.provider.IPreProcessingCommandProvider;
import de.thatsich.openfx.preprocessing.intern.control.command.service.TrainedPreProcessorFileStorageService;
import javafx.application.Platform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author thatsIch
 * @since 10.06.2014.
 */
public class NetworkSpace implements INetworkSpace
{
	private static final int ERROR_ITERATION = 1;
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

	@Inject private IErrorCommandProvider errorProvider;
	@Inject private IFeatureCommandProvider featureProvider;
	@Inject private IPreProcessingCommandProvider preProcessingProvider;
	@Inject private INetworkCommandProvider networkProvider;

	@Inject private FeatureFileStorageService featureStorage;

	@Inject private IErrors errors;
	@Inject private IFeatures features;

	@Inject private Log log;

	@Override
	public ITrainedNetwork train(List<IImage> trainingImages, List<IErrorGenerator> errorGenerators, ErrorFileStorageService errorStorage, List<IFeatureExtractor> featureExtractors, List<IPreProcessor> preProcessors, TrainedPreProcessorFileStorageService preproStorage, List<IBinaryClassifier> binaryClassifiers) throws Exception
	{
		final long startTime = System.currentTimeMillis();
		this.log.info("Started training at " + startTime);

		final List<ErrorClass> errorClasses = this.getErrorClasses(trainingImages, errorGenerators, errorStorage);
		final List<IFeature> features = this.getFeatures(errorClasses, featureExtractors);
		final ICNBC cnbc = this.getCNBC(features);

		final long stopTime = System.currentTimeMillis();

		final String creationTime = format.format(new Date());
		final String id = UUID.randomUUID().toString();
		final long trainTime = stopTime - startTime;
		final NetworkConfig config = new NetworkConfig(creationTime, id, trainTime);

		return this.networkProvider.createTrainedNetwork(config, featureExtractors, cnbc);
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
	private List<ErrorClass> getErrorClasses(List<IImage> images, List<IErrorGenerator> errorGenerators, ErrorFileStorageService storage) throws Exception
	{
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		final int imageBound = images.size();
		this.log.info("Prepared error generation.");

		final List<ErrorClass> errorClasses = new LinkedList<>();
		for (IErrorGenerator errorGenerator : errorGenerators)
		{
			final String errorClassName = errorGenerator.getName();
			final List<IError> errors = new LinkedList<>();
			for (int index = 0; index < ERROR_ITERATION; index++)
			{
				final int randomImageIndex = random.nextInt(imageBound);
				final IImage randomImage = images.get(randomImageIndex);

				final CreateErrorCommand create = this.errorProvider.createCreateErrorCommand(randomImage, errorGenerator, false, false, false);
				final IError error = create.call();
				this.log.info("Created error " + error);

				Platform.runLater(() -> this.errors.list().add(error));

				errors.add(error);
			}

			final ErrorClass errorClass = new ErrorClass(errorClassName, errors);
			errorClasses.add(errorClass);
		}

		this.log.info("Generated errors " + errorClasses);

		return errorClasses;
	}

	/**
	 * Extracts features out of a given list of featureextractors and errorClasses.
	 * All possible permutations are being generated
	 *
	 * @param errorClasses      given errorClasses
	 * @param featureExtractors given feature extractors
	 *
	 * @return list of features
	 *
	 * @throws Exception when a feature cant be stored
	 */
	private List<IFeature> getFeatures(List<ErrorClass> errorClasses, List<IFeatureExtractor> featureExtractors) throws Exception
	{
		final List<IFeature> features = new LinkedList<>();

		for (IFeatureExtractor featureExtractor : featureExtractors)
		{
			this.log.info("Feature Extractor " + featureExtractor.getName());
			this.log.info("ErrorClasses: " + errorClasses.size());
			for (ErrorClass errorClass : errorClasses)
			{
				final List<IError> errors = errorClass.errors;
				IFeature feature = null;
				for (IError error : errors)
				{
					final CreateExtractedFeatureCommand create = this.featureProvider.createExtractFeatureCommand(error, featureExtractor, 15);
					final IFeature subFeature = create.call();
					this.log.info("Created subFeature " + subFeature + " through " + error.clazzProperty().get() + ", " + featureExtractor.getName());

					if (feature == null)
					{
						feature = subFeature;
						this.log.info("Initated subFeature.");
					}
					else
					{
						feature.merge(subFeature);
						this.log.info("Merged features.");
					}
				}
				final IFeature finalFeature = feature;
				Platform.runLater(() -> this.features.list().add(finalFeature));
				this.featureStorage.create(finalFeature);

				features.add(feature);
				this.log.info("Added feature " + feature);
			}
		}

		return features;
	}

	private ICNBC getCNBC(List<IFeature> features) throws Exception
	{
		final List<INBC> initialNBCs = new LinkedList<>();
		final Set<String> initialClasses = new HashSet<>();
		final ICNBC cnbc = this.networkProvider.createCollectiveNetworkBinaryClassifiers(initialNBCs, initialClasses);

		for (IFeature preprocessedFeature : features)
		{
			cnbc.addFeature(preprocessedFeature);
		}
		this.log.info("Trained CNBC with " + features + ".");

		return cnbc;
	}

	private class ErrorClass
	{
		public final String errorClassName;
		public final List<IError> errors;

		public ErrorClass(String errorClassName, List<IError> errors)
		{

			this.errorClassName = errorClassName;
			this.errors = errors;
		}
	}
}
