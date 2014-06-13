package de.thatsich.openfx.network.intern.control.prediction;

import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.CreateExtractedFeatureCommand;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ClassSelection;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ICNBC;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class TrainedNetwork implements ITrainedNetwork
{
	private final NetworkConfig config;
	private final List<IFeatureExtractor> featureExtractors;
	private final List<ITrainedPreProcessor> trainedPreProcessors;
	private final ICNBC cnbc;
	private final ClassSelection cs;
	private final long trainTime;

	public TrainedNetwork(NetworkConfig config, List<IFeatureExtractor> featureExtractors, List<ITrainedPreProcessor> trainedPreProcessors, ICNBC cnbc, long trainTime)
	{
		this.config = config;
		this.featureExtractors = featureExtractors;
		this.trainedPreProcessors = trainedPreProcessors;
		this.cnbc = cnbc;
		this.cs = new ClassSelection();

		this.trainTime = trainTime;
	}

	@Override
	public long getTrainTime()
	{
		return this.trainTime;
	}

	@Override
	public String predict(IError error) throws Exception
	{
		final List<IFeature> features = this.getFeatures(error, this.featureExtractors);
		final List<IFeature> preProcessedFeatures = this.getPreProcessedFeatures(features, this.trainedPreProcessors);
		final List<IFeatureVector> preProcessedFeatureVectors = this.getPreProcessedFeatureVectors(preProcessedFeatures);
		final List<Pair<String, Double>> predictions = this.getPredictions(preProcessedFeatureVectors, this.cnbc);

		return this.cs.predict(predictions);
	}

	@Override
	public NetworkConfig getConfig()
	{
		return this.config;
	}

	private List<IFeature> getFeatures(IError error, List<IFeatureExtractor> featureExtractors) throws Exception
	{
		final List<IFeature> features = new LinkedList<>();

		for (IFeatureExtractor featureExtractor : featureExtractors)
		{
			final IFeature feature = this.getFeature(error, featureExtractor);
			features.add(feature);
		}

		return features;
	}

	private List<IFeature> getPreProcessedFeatures(List<IFeature> features, List<ITrainedPreProcessor> trainedPreProcessors)
	{
		final List<IFeature> preprocessedFeatures = new LinkedList<>();

		for (ITrainedPreProcessor trainedPreProcessor : trainedPreProcessors)
		{
			for (IFeature feature : features)
			{
				final IFeature preprocess = trainedPreProcessor.preprocess(feature);
				preprocessedFeatures.add(preprocess);
			}
		}

		return preprocessedFeatures;
	}

	private List<IFeatureVector> getPreProcessedFeatureVectors(List<IFeature> preProcessedFeatures)
	{
		final List<IFeatureVector> preprocessedFeatureVectors = new LinkedList<>();

		for (IFeature preprocessedFeature : preProcessedFeatures)
		{
			final List<IFeatureVector> vectors = preprocessedFeature.vectors();
			preprocessedFeatureVectors.addAll(vectors);
		}

		return preprocessedFeatureVectors;
	}

	private List<Pair<String, Double>> getPredictions(List<IFeatureVector> preProcessedFeatureVectors, ICNBC cnbc)
	{
		final List<Pair<String, Double>> predictions = new LinkedList<>();

		for (IFeatureVector preprocessedFeatureVector : preProcessedFeatureVectors)
		{
			final List<Pair<String, Double>> predict = cnbc.predict(preprocessedFeatureVector);
			predictions.addAll(predict);
		}

		return predictions;
	}

	private IFeature getFeature(IError error, IFeatureExtractor featureExtractor) throws Exception
	{
		final CreateExtractedFeatureCommand command = new CreateExtractedFeatureCommand(error, featureExtractor, 15);
		final IFeature feature = command.call();

		return feature;
	}
}
