package de.thatsich.bachelor.classificationtraining.restricted.application.guice;

import org.opencv.ml.CvRTrees;
import org.opencv.ml.CvSVM;

import de.thatsich.bachelor.classificationtraining.restricted.model.logic.RandomForestBinaryClassification;
import de.thatsich.bachelor.classificationtraining.restricted.model.logic.SVMBinaryClassification;

public interface BinaryClassificationProvider {
	public SVMBinaryClassification createSVMBinaryClassification(CvSVM svm);
	public RandomForestBinaryClassification createRandomForestBinaryClassification(CvRTrees tree);
}
