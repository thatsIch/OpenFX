package de.thatsich.bachelor.classification.intern.command.provider;

import org.opencv.ml.CvRTrees;
import org.opencv.ml.CvSVM;

import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classification.intern.command.classification.RandomForestBinaryClassification;
import de.thatsich.bachelor.classification.intern.command.classification.SVMBinaryClassification;
import de.thatsich.bachelor.classification.intern.command.classifier.BinaryClassifierConfiguration;
import de.thatsich.core.guice.ICommandProvider;

public interface IBinaryClassificationProvider extends ICommandProvider {
	public SVMBinaryClassification createSVMBinaryClassification(@Assisted CvSVM svm, @Assisted BinaryClassifierConfiguration config);
	public RandomForestBinaryClassification createRandomForestBinaryClassification(@Assisted CvRTrees tree, @Assisted BinaryClassifierConfiguration config);
}
