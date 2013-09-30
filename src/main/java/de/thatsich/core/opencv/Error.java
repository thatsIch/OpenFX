package de.thatsich.core.opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;


/**
 * Error Class
 * Bundles the original image to the error together.
 * With this you can calculate the error is made by 
 * calculating the absolute difference between both
 * Images to get the Ground-Truth Error
 * 
 * @author Minh
 *
 */
public class Error {

	private final Mat original;
	private final Mat error;
	
	/**
	 * CTOR
	 * 
	 * @param original Image which the Error Image is based on.
	 * @param error Error Image made from Original Image.
	 */
	public Error(Mat original, Mat error) {
		this.original = Images.toGrayScale(original);
		this.error = Images.toGrayScale(error);
	}
	
	// ==================================================
	// GETTER 
	// ==================================================
	public Mat getOriginal() { return this.original; }
	public Mat getError() { return this.error; }
	
	public Mat getAbsoluteDifference() {
		Mat absDiff = new Mat(this.original.size(), CvType.CV_8U);
		absDiff = Images.toGrayScale(absDiff);
		Core.absdiff(this.original, this.error, absDiff);
		
		return absDiff;
	}
	
	public Mat getErrorMatrix(int frameSize) {
		Mat absDiff = this.getAbsoluteDifference();
		Mat[][] matrix = Images.split(absDiff, frameSize, frameSize);
		Mat errorMatrix = Mat.zeros(matrix.length, matrix[0].length, CvType.CV_8U);
		
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[0].length; col++) {
				if (Core.sumElems(matrix[row][col]).val[0] != 0) {
					errorMatrix.put(row, col, 1);
				}
			}
		}
		
		System.out.println(errorMatrix.dump());
		
		return errorMatrix;
	}
}
