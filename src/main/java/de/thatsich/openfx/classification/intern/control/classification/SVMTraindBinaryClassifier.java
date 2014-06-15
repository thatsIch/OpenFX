package de.thatsich.openfx.classification.intern.control.classification;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.classification.intern.control.classification.core.ATraindBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import org.opencv.core.MatOfFloat;
import org.opencv.ml.CvSVM;


public class SVMTraindBinaryClassifier extends ATraindBinaryClassifier
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
	public SVMTraindBinaryClassifier(@Assisted CvSVM svm, @Assisted BinaryClassificationConfig config)
	{
		super(config);
		this.svm = svm;
	}

	@Override
	public double predict(IFeatureVector fv)
	{
		final float[] featureArray = new float[fv.vector().size()];

		for (int i = 0; i < fv.vector().size(); i++)
		{
			featureArray[i] = fv.vector().get(i).floatValue();
		}

		final MatOfFloat floatMat = new MatOfFloat(featureArray);
		final float predict = this.svm.predict(floatMat);
		this.log.info("Predicted " + predict);

		return predict;
	}

	@Override
	public void save(String filePath)
	{
		this.svm.save(filePath);
	}

	@Override
	public void load(String filePath)
	{
		this.svm.load(filePath);
	}
}