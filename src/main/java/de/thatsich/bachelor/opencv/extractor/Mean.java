package de.thatsich.bachelor.opencv.extractor;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Scalar;

import de.thatsich.core.opencv.extractor.AFeatureExtractor;
import de.thatsich.core.opencv.extractor.IFeatureExtractor;

/**
 * Calculates the mean of an Image.
 * Mostly Wrapper for OpenCV Core.mean
 * 
 * @author Tran Minh Do
 *
 */
public class Mean extends AFeatureExtractor implements IFeatureExtractor {

	@Override
	public Mat extractFeature(Mat image) {
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale.");
		if (image.empty()) throw new IllegalArgumentException("Image is empty.");
		
		// returns a RBGA scalar, since grayscale only first is needed
		Scalar meanRGBA = Core.mean(image);

		return new MatOfDouble(meanRGBA.val[0]);
	}
}
