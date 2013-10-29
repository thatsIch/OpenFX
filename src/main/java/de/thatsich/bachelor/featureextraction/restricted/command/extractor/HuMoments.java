package de.thatsich.bachelor.featureextraction.restricted.command.extractor;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import com.google.inject.Singleton;

/**
 * 
 * @author Minh
 *
 */
@Singleton
public class HuMoments extends AFeatureExtractor implements IFeatureExtractor {

	@Override
	public MatOfFloat extractFeature(Mat image) {		
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale.");
		if (image.empty()) throw new IllegalArgumentException("Image is empty.");
		
		Mat hu = new Mat();
		Moments moments = Imgproc.moments(image);
		Imgproc.HuMoments(moments, hu);
		hu.convertTo(hu, CvType.CV_32F);
		
		return new MatOfFloat(hu);
	}
}
