package de.thatsich.openfx.preprocessing.intern.control.command.preprocessing;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.intern.control.entity.Feature;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureConfig;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.ATrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.TrainedPreProcessorConfig;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Result of a AANNPreProcessor
 *
 * @author thatsIch
 */
public class AANNTrainedPreProcessor extends ATrainedPreProcessor
{
	private ReadOnlyObjectProperty<BasicNetwork> networkProperty;

	@Inject
	public AANNTrainedPreProcessor(@Assisted BasicNetwork network, @Assisted TrainedPreProcessorConfig config)
	{
		super(config);
		this.networkProperty = new ReadOnlyObjectWrapper<>(network);
	}

	@Override
	public List<IFeature> preprocess(List<IFeature> features)
	{
		final List<IFeature> preprocessedFeatures = new LinkedList<>();

		for (IFeature feature : features)
		{
			final IFeature preProcessedFeature = this.getPreProcessedFeature(feature);
			preprocessedFeatures.add(preProcessedFeature);
		}

		return preprocessedFeatures;
	}

	@Override
	public ReadOnlyObjectProperty<BasicNetwork> networkProperty()
	{
		return this.networkProperty;
	}

	/**
	 * Preprocesses a feature
	 *
	 * @param feature to be preprocessed feature
	 *
	 * @return preprocessed feature
	 */
	private IFeature getPreProcessedFeature(IFeature feature)
	{
		final List<IFeatureVector> featureVectors = feature.vectors();
		final List<IFeatureVector> preProcessedFeatureVectors = this.getPreProcessedFeatureVectors(featureVectors);

		final FeatureConfig config = feature.getConfig();
		final IFeature proprocessedFeature = new Feature(config, preProcessedFeatureVectors);

		return proprocessedFeature;
	}

	/**
	 * Preprocesses featurevectors
	 *
	 * @param featureVectors to be preprocessed feature vectors
	 *
	 * @return preprocessed feature vectors
	 */
	private List<IFeatureVector> getPreProcessedFeatureVectors(List<IFeatureVector> featureVectors)
	{
		final List<IFeatureVector> preProcessedFeatureVectors = new LinkedList<>();

		for (IFeatureVector featureVector : featureVectors)
		{
			final IFeatureVector preProcessedFeatureVector = this.getPreProcessedFeatureVector(featureVector);
			preProcessedFeatureVectors.add(preProcessedFeatureVector);
		}

		return preProcessedFeatureVectors;
	}

	/**
	 * Used the network to preprocess a feature vector alone.
	 * Handles convertion between primitiv arrays and IFeatureVector
	 *
	 * @param featureVector to be processed feature vector
	 *
	 * @return preprocessed feature vector
	 */
	private IFeatureVector getPreProcessedFeatureVector(IFeatureVector featureVector)
	{
		final ReadOnlyListProperty<Double> lVector = featureVector.vector();
		final double[] aVector = new double[lVector.size()];

		for (int lVectorIndex = 0; lVectorIndex < lVector.size(); lVectorIndex++)
		{
			aVector[lVectorIndex] = lVector.get(lVectorIndex);
		}

		final MLData inputData = new BasicMLData(aVector);
		final MLData outputData = this.networkProperty.get().compute(inputData);
		final double[] preProcessedArray = outputData.getData();
		final List<Double> preProcessedVector = new ArrayList<>(preProcessedArray.length);
		for (double v : preProcessedArray)
		{
			preProcessedVector.add(v);
		}

		final boolean isPositive = featureVector.isPositive().get();
		final FeatureVector preProcessedFeatureVector = new FeatureVector(preProcessedVector, isPositive);

		return preProcessedFeatureVector;
	}
}
