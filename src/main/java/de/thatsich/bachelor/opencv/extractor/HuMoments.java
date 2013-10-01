package de.thatsich.bachelor.opencv.extractor;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import com.google.inject.Singleton;

import de.thatsich.core.opencv.extractor.AFeatureExtractor;
import de.thatsich.core.opencv.extractor.IFeatureExtractor;

/**
 * 
 * @author Minh
 *
 */
@Singleton
public class HuMoments extends AFeatureExtractor implements IFeatureExtractor {

	@Override
	public Mat extractFeature(Mat image) {		
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale.");
		if (image.empty()) throw new IllegalArgumentException("Image is empty.");
		
		Mat hu = new Mat();
		Moments moments = Imgproc.moments(image);
		Imgproc.HuMoments(moments, hu);
		
		return hu;
	}
}
