package de.thatsich.openfx.classification.intern.control.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.classification.intern.control.classification.RandomForestBinaryClassification;
import de.thatsich.openfx.classification.intern.control.classification.SVMBinaryClassification;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import org.opencv.ml.CvRTrees;
import org.opencv.ml.CvSVM;

public interface IBinaryClassificationProvider extends ICommandProvider
{
	SVMBinaryClassification createSVMBinaryClassification(@Assisted CvSVM svm, @Assisted BinaryClassificationConfig config);

	RandomForestBinaryClassification createRandomForestBinaryClassification(@Assisted CvRTrees tree, @Assisted BinaryClassificationConfig config);
}
