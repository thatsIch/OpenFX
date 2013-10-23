package de.thatsich.bachelor.classificationtraining.restricted.application.guice;

import org.opencv.ml.CvRTrees;
import org.opencv.ml.CvSVM;

import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtraining.restricted.model.logic.BinaryClassifierConfiguration;
import de.thatsich.bachelor.classificationtraining.restricted.model.logic.RandomForestBinaryClassification;
import de.thatsich.bachelor.classificationtraining.restricted.model.logic.SVMBinaryClassification;

public interface BinaryClassificationProvider {
	public SVMBinaryClassification createSVMBinaryClassification(@Assisted CvSVM svm, @Assisted BinaryClassifierConfiguration config);
	public RandomForestBinaryClassification createRandomForestBinaryClassification(@Assisted CvRTrees tree, @Assisted BinaryClassifierConfiguration config);
}
