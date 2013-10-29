package de.thatsich.bachelor.classification.intern.command.classifier;

import org.opencv.core.Mat;
import org.opencv.ml.CvSVM;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;


public class SVMBinaryClassification extends ABinaryClassification {

	private final CvSVM svm;
	
	@Inject
	public SVMBinaryClassification(@Assisted CvSVM svm, @Assisted BinaryClassifierConfiguration config) {
		super(config);
		this.svm = svm;
	}
	
	@Override public double predict(Mat image) { return this.svm.predict(image); }
	@Override public void load(String fileName) { this.svm.load(fileName); this.log.info("SVM loaded from " + fileName); }
	@Override public void save(String fileName) { this.svm.save(fileName); this.log.info("SVM saved to " + fileName);}
}
