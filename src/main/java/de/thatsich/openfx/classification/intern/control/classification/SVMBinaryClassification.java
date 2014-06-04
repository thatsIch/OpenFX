package de.thatsich.openfx.classification.intern.control.classification;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.classification.intern.control.classification.core.ABinaryClassification;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassifierConfiguration;
import org.opencv.core.Mat;
import org.opencv.ml.CvSVM;


public class SVMBinaryClassification extends ABinaryClassification
{

	private final CvSVM svm;

	/**
	 * CTOR
	 *
	 * saves config to superclass
	 * and svm to field
	 *
	 * @param svm    Assisted Injected SVM
	 * @param config Assisted Injected Config
	 */
	@Inject
	public SVMBinaryClassification(@Assisted CvSVM svm, @Assisted BinaryClassifierConfiguration config)
	{
		super(config);
		this.svm = svm;
	}

	/**
	 * @see ABinaryClassification
	 */
	@Override
	public double predict(Mat image)
	{
		return this.svm.predict(image);
	}

	/**
	 * @see ABinaryClassification
	 */
	@Override
	public void load(String fileName)
	{
		this.svm.load(fileName);
		this.log.info("SVM loaded from " + fileName);
	}

	/**
	 * @see ABinaryClassification
	 */
	@Override
	public void save(String fileName)
	{
		this.svm.save(fileName);
		this.log.info("SVM saved to " + fileName);
	}
}