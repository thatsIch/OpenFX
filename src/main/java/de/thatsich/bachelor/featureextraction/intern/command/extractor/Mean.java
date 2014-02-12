package de.thatsich.bachelor.featureextraction.intern.command.extractor;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Scalar;

/**
 * Calculates the mean of an Image.
 * Mostly Wrapper for OpenCV Core.mean
 * 
 * @author Tran Minh Do
 *
 */
public class Mean extends AFeatureExtractor implements IFeatureExtractor {

	@Override
	public MatOfFloat extractFeature(Mat image) {
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale.");
		if (image.empty()) throw new IllegalArgumentException("Image is empty.");
		
		// returns a RBGA scalar, since grayscale only first is needed
		Scalar meanRGBA = Core.mean(image);

		// convert to float array
		float floatVal = (float) meanRGBA.val[0];

		return new MatOfFloat(floatVal);
	}
}
