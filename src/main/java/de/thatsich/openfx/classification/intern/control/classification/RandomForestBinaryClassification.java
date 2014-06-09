package de.thatsich.openfx.classification.intern.control.classification;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.classification.intern.control.classification.core.ABinaryClassification;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import org.opencv.core.Mat;
import org.opencv.ml.CvRTrees;


public class RandomForestBinaryClassification extends ABinaryClassification
{

	private final CvRTrees trees;

	@Inject
	public RandomForestBinaryClassification(@Assisted CvRTrees trees, @Assisted BinaryClassificationConfig config)
	{
		super(config);
		this.trees = trees;
	}

	@Override
	public double predict(Mat image)
	{
		return this.trees.predict_prob(image);
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
