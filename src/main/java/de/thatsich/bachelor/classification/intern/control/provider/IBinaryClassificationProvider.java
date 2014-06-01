package de.thatsich.bachelor.classification.intern.control.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.classification.intern.control.classification.RandomForestBinaryClassification;
import de.thatsich.bachelor.classification.intern.control.classification.SVMBinaryClassification;
import de.thatsich.bachelor.classification.intern.control.classifier.core.BinaryClassifierConfiguration;
import de.thatsich.core.guice.ICommandProvider;
import org.opencv.ml.CvRTrees;
import org.opencv.ml.CvSVM;

public interface IBinaryClassificationProvider extends ICommandProvider
{
	public SVMBinaryClassification createSVMBinaryClassification(@Assisted CvSVM svm, @Assisted BinaryClassifierConfiguration config);

	public RandomForestBinaryClassification createRandomForestBinaryClassification(@Assisted CvRTrees tree, @Assisted BinaryClassifierConfiguration config);
}
