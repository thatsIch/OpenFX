package de.thatsich.bachelor.classification.intern.control.classification;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.classification.intern.control.classification.core.ABinaryClassification;
import de.thatsich.bachelor.classification.intern.control.classifier.core.BinaryClassifierConfiguration;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.opencv.core.Mat;
import org.opencv.ml.CvRTrees;


public class RandomForestBinaryClassification extends ABinaryClassification
{

	private final ObjectProperty<CvRTrees> trees = new SimpleObjectProperty<>(new CvRTrees());

	@Inject
	public RandomForestBinaryClassification(@Assisted CvRTrees trees, @Assisted BinaryClassifierConfiguration config)
	{
		super(config);
		this.trees.set(trees);
	}

	@Override
	public double predict(Mat image)
	{
		return this.trees.get().predict_prob(image);
	}

	@Override
	public void load(String fileName)
	{
		this.trees.get().load(fileName);
	}

	@Override
	public void save(String fileName)
	{
		this.trees.get().save(fileName);
	}
}
