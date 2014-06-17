package de.thatsich.openfx.classification.intern.control.classification;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.classification.intern.control.classification.core.ATrainedBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import org.opencv.core.MatOfFloat;
import org.opencv.ml.CvRTrees;


public class RandomForestTrainedBinaryClassifier extends ATrainedBinaryClassifier
{

	private final CvRTrees trees;

	@Inject
	public RandomForestTrainedBinaryClassifier(@Assisted CvRTrees trees, @Assisted BinaryClassificationConfig config)
	{
		super(config);
		this.trees = trees;
	}

	@Override
	public double predict(IFeatureVector fv)
	{
		final float[] featureArray = new float[fv.vector().size()];

		for (int i = 0; i < fv.vector().size(); i++)
		{
			featureArray[i] = fv.vector().get(i).floatValue();
		}

		final MatOfFloat floatMat = new MatOfFloat(featureArray);
		final float predict = this.trees.predict_prob(floatMat);

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
