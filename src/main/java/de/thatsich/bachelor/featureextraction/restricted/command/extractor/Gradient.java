package de.thatsich.bachelor.featureextraction.restricted.command.extractor;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.imgproc.Imgproc;


/**
 * Applies <a href="http://en.wikipedia.org/wiki/Sobel_operator">Sobel Operator</a> 
 * on the Matrix 
 * using the <a href="http://docs.opencv.org/modules/imgproc/doc/filtering.html?highlight=sobel#sobel">OpenCV implementation</a>
 * 
 * @author Tran Minh Do
 *
 */
public class Gradient extends AFeatureExtractor {

	@Override
	public MatOfFloat extractFeature(Mat image) {
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale.");
		if (image.empty()) throw new IllegalArgumentException("Image is empty.");
		
		Mat gradient = new Mat();
		Imgproc.Sobel(image, gradient, image.depth(), 1, 1);
		gradient = gradient.reshape(1, 1);
		gradient.convertTo(gradient, CvType.CV_32F);
		
		return new MatOfFloat(gradient);
	}
}
