package de.thatsich.bachelor.classificationtraining.restricted.model.logic;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.opencv.core.Mat;
import org.opencv.ml.CvSVM;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;


public class SVMBinaryClassification extends ABinaryClassification {

	private final ObjectProperty<CvSVM> svm = new SimpleObjectProperty<>();
	
	@Inject
	public SVMBinaryClassification(@Assisted CvSVM svm, @Assisted BinaryClassifierConfiguration config) {
		super(config);
		this.svm.set(svm);
	}
	
	@Override
	public double predict(Mat image) {
		return this.svm.get().predict(image);
	}

	@Override
	public void load(String fileName) {
		this.svm.get().load(fileName);
	}

	@Override
	public void save(String fileName) {
		this.svm.get().save(fileName);
	}
}
