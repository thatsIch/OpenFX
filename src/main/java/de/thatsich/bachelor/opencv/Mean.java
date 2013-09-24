package de.thatsich.bachelor.opencv;

import java.security.InvalidParameterException;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import de.thatsich.core.opencv.AFeatureExtractor;
import de.thatsich.core.opencv.IFeatureExtractor;

public class Mean extends AFeatureExtractor implements IFeatureExtractor {

	@Override
	public Mat extractFeature(Mat image) {
		if (image == null) throw new InvalidParameterException("Mean.extractFeature: image was null.");
		
		// returns a RBGA scalar, since grayscale only first is needed
		Scalar meanRGBA = Core.mean(image);
		
		Mat result = new Mat(1, 1, CvType.CV_8UC1);
		result.put(0, 0, meanRGBA.val[0]);
		
		return result;
	}

	@Override
	public double compareFeature(Mat leftImage, Mat rightImage) {
		// TODO Auto-generated method stub
		return 0;
	}

}
