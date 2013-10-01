package de.thatsich.bachelor.opencv.extractor;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

import de.thatsich.core.opencv.extractor.AFeatureExtractor;
import de.thatsich.core.opencv.extractor.IFeatureExtractor;

public class LocalBinaryPatternHistogram extends AFeatureExtractor implements
		IFeatureExtractor {

	@Override
	public Mat extractFeature(Mat image) {
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale.");
		if (image.rows() < 3 || image.cols() < 3) throw new IllegalArgumentException("Image is smaller than 3x3");
		
		// 8 neighbors equal to 256 bins
		int[] histogram = new int[256];
		
		// Traverse the mat
		for (int row = 1; row < image.rows() - 1; row++) {
			for (int col = 1; col < image.cols() - 1; col++) {
				
				// since grayscale, can directly access to first element
				double center = image.get(row, col)[0];
				int sum = 0;
				
				sum += (image.get(row - 1, col - 1)[0] > center) ? 1 << 0 : 0; 
				sum += (image.get(row - 1, col - 0)[0] > center) ? 1 << 1 : 0;
				sum += (image.get(row - 1, col + 1)[0] > center) ? 1 << 2 : 0;
				sum += (image.get(row - 0, col + 1)[0] > center) ? 1 << 3 : 0;
				sum += (image.get(row + 1, col + 1)[0] > center) ? 1 << 4 : 0;
				sum += (image.get(row + 1, col - 0)[0] > center) ? 1 << 5 : 0;
				sum += (image.get(row + 1, col - 1)[0] > center) ? 1 << 6 : 0;
				sum += (image.get(row - 0, col - 1)[0] > center) ? 1 << 7 : 0;
				
				System.out.println(sum);
				
				histogram[sum] += 1;
			}
		}
		
		// wrap it around a mat
		MatOfInt histogramContainer = new MatOfInt(histogram);
		
		return histogramContainer;
	}
}