package de.thatsich.bachelor.opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class Histogram {

//	private List<Mat> list;
//	private final MatOfInt channels;
//	private final Mat mask;
//	private Mat histogram;
//	
//	/**
//	 * for example MatOfInt(256) means 256 pixel high histogram
//	 */
//	private final MatOfInt histSize;
//	private final MatOfFloat histRanges;
//	
//	public Histogram() {
//		this.channels = new MatOfInt(0);
//		this.mask = new Mat();
//		this.histSize = new MatOfInt(256);
//		this.histRanges = new MatOfFloat(0f, 256f);
//	}
//	
//  funktioniert nicht warum auch immer
//	public Mat calc(Mat image) {
//		if (image == null) throw new IllegalArgumentException("Image is null.");
//		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale. " + image.type());
//		if (image.empty()) throw new IllegalArgumentException("Image is empty.");
//		
//		this.list = Arrays.asList(image);
//		this.histogram = new Mat();
//		
//		Imgproc.calcHist(
//			this.list, 
//			this.channels, 
//			this.mask, 
//			this.histogram, 
//			this.histSize, 
//			this.histRanges
//		);
//
//		return histogram;
//	}
	
	private int maxValue(int[] array) {
		int max = 0;
		
		for (int i = 0; i < array.length; i++) {
			max = Math.max(max, array[i]);
		}
		
		return max;
	}
	
	public Mat calc(Mat image) {
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale. " + image.type());
		if (image.empty()) throw new IllegalArgumentException("Image is empty.");
		
		final int bins = 256;
		final int height = 256;
		final int[] histogram = new int[bins];

		// calculate histogram
		for (int row = 0; row < image.rows(); row++) {
			for (int col = 0; col < image.cols(); col++) {
				final int pixelValue = (int) image.get(row, col)[0];
				histogram[pixelValue]++;
			}
		}
		
		// normalize
		final int peak = this.maxValue(histogram);
		
		// convert into displayable matrix
		final Mat canvas = Mat.ones(height, bins, CvType.CV_8U);
		for (int i = 0; i < bins; i++) {
			
			Core.line(
				canvas, 
				new Point(i, canvas.rows()), 
				new Point(i, canvas.rows() - (histogram[i] * canvas.rows() / peak)), 
				new Scalar(200)
			);
		}
		
		return canvas;
	}
	
	public static void main() {
		
	}
}
