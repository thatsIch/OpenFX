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
		final List<IFeature> features = new LinkedList<>();
		for (IFeatureExtractor featureExtractor : this.featureExtractors)
		{
			final CreateExtractedFeatureCommand created = new CreateExtractedFeatureCommand(error, featureExtractor, 31);
			final IFeature feature = created.call();
			features.add(feature);
		}

		final List<IFeature> preprocessedFeatures = new LinkedList<>();
		for (ITrainedPreProcessor trainedPreProcessor : this.trainedPreProcessors)
		{
			final List<IFeature> preprocess = trainedPreProcessor.preprocess(features);
			preprocessedFeatures.addAll(preprocess);
		}

		final List<IFeatureVector> preprocessedFeatureVectors = new LinkedList<>();
		for (IFeature preprocessedFeature : preprocessedFeatures)
		{
			final List<IFeatureVector> vectors = preprocessedFeature.vectors();
			preprocessedFeatureVectors.addAll(vectors);
		}

		final List<Pair<String, Double>> predictions = new LinkedList<>();
		for (IFeatureVector preprocessedFeatureVector : preprocessedFeatureVectors)
		{
			final List<Pair<String, Double>> predict = this.cnbc.predict(preprocessedFeatureVector);
			predictions.addAll(predict);
		}

		return this.cs.predict(predictions);
	}

	@Override
	public NetworkConfig getConfig()
	{
		return this.config;
	}
}
