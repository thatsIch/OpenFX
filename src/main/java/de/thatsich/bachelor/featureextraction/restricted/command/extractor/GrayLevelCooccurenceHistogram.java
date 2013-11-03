package de.thatsich.bachelor.featureextraction.restricted.command.extractor;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;


/**
 * Calculates the Co-occurence Histrogram for Gray-Levels
 * 
 * @author Minh
 */
//TODO fix GLCM
public class GrayLevelCooccurenceHistogram extends AFeatureExtractor implements IFeatureExtractor {

	/**
	 * Specify the Direction and Angle of the GLCM
	 * 
	 * (1)
	 * (0)
	 * 
	 * means the angle is 0° and distance is 1
	 * 
	 * (0)
	 * (2)
	 * 
	 * would be angle = 90° and distance = 2 
	 */
	private final int OFFSET_COL = 1;
	private final int OFFSET_ROW = 0;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param rangeX
	 * @param rangeY
	 * @return
	 */
	private boolean isWithinRange(int x, int y, int rangeX, int rangeY) {
		return (0 <= x && x < rangeX && 0 <= y && y < rangeY );
	}
	
	@Override
	public MatOfFloat extractFeature(Mat image) {
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale.");
		if (image.empty()) throw new IllegalArgumentException("Image is empty.");
		
		float[] histogram = new float[256];
		System.out.println(image.size());
		// Runtime Variables
		int rows = image.rows();
		int cols = image.cols();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {

				if (this.isWithinRange(row + this.OFFSET_ROW, col + this.OFFSET_COL, rows, cols)) {
					// because of grayscale direct access
					double[] pixelRGBA = image.get(row + this.OFFSET_ROW, col + this.OFFSET_COL);
					double pixel = pixelRGBA[0];
					int pixelValue = (int) pixel;

					histogram[pixelValue]++;
				}
			}
		}
		
		return new MatOfFloat(histogram);
	}
}
