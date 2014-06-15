package de.thatsich.openfx.classification.intern.control.classification;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.classification.intern.control.classification.core.ATraindBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import org.opencv.core.MatOfDouble;
import org.opencv.ml.CvRTrees;


public class RandomForestTraindBinaryClassifier extends ATraindBinaryClassifier
{

	private final CvRTrees trees;

	@Inject
	public RandomForestTraindBinaryClassifier(@Assisted CvRTrees trees, @Assisted BinaryClassificationConfig config)
	{
		super(config);
		this.trees = trees;
	}

	@Override
	public double predict(IFeatureVector fv)
	{
		final double[] featureArray = new double[fv.vector().size()];

		for (int i = 0; i < fv.vector().size(); i++)
		{
			featureArray[i] = fv.vector().get(i);
		}

		final MatOfDouble featureMat = new MatOfDouble(featureArray);
		final float predict = this.trees.predict_prob(featureMat);
		this.log.info("Predicted " + predict);

		return predict;
	}

	@Override
	public void save(String filePath)
	{
		this.trees.save(filePath);
	}

	@Override
	public void load(String filePath)
	{
		this.trees.load(filePath);
	}
}
