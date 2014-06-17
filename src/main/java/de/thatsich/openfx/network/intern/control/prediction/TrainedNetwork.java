package de.thatsich.openfx.network.intern.control.prediction;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.Log;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.CreateExtractedFeatureCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.provider.IFeatureCommandProvider;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ClassSelection;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ICNBC;
import de.thatsich.openfx.network.intern.control.provider.INetworkCommandProvider;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyStringProperty;
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
	private final ICNBC cnbc;
	private final IFeatureCommandProvider featureProvider;
	private final Log log;
	private final ClassSelection cs;

	@Inject
	public TrainedNetwork(@Assisted NetworkConfig config, @Assisted List<IFeatureExtractor> featureExtractors, @Assisted ICNBC cnbc, IFeatureCommandProvider featureProvider, INetworkCommandProvider networkProvider, Log log)
	{
		this.config = config;
		this.featureExtractors = featureExtractors;
		this.cnbc = cnbc;
		this.featureProvider = featureProvider;
		this.log = log;
		this.cs = networkProvider.createClassSelection();
	}

	@Override
	public ICNBC getCnbc()
	{
		return this.cnbc;
	}

	@Override
	public Pair<String, Double> predict(IError error) throws Exception
	{
		final List<IFeature> features = this.getFeatures(error, this.featureExtractors);
		this.log.info("Extracted features: " + features.size());

		final List<Pair<String, Double>> predictions = this.getPredictions(features, this.cnbc);
		this.log.info("Extracted predictions: " + predictions.size());

		return this.cs.predict(predictions);
	}

	@Override
	public ReadOnlyStringProperty date()
	{
		return this.config.date;
	}

	@Override
	public ReadOnlyStringProperty id()
	{
		return this.config.id;
	}

	@Override
	public ReadOnlyLongProperty trainTime()
	{
		return this.config.trainTime;
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

	private List<Pair<String, Double>> getPredictions(List<IFeature> features, ICNBC cnbc)
	{
		final List<Pair<String, Double>> predictions = new LinkedList<>();

		for (IFeature f : features)
		{
			final List<Pair<String, Double>> predict = cnbc.predict(f);
			predictions.addAll(predict);
		}

		return predictions;
	}

	private IFeature getFeature(IError error, IFeatureExtractor featureExtractor) throws Exception
	{
		final CreateExtractedFeatureCommand command = this.featureProvider.createExtractFeatureCommand(error, featureExtractor, 15);
		final IFeature feature = command.call();

		return feature;
	}
}
