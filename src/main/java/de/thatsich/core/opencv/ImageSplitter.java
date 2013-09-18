package de.thatsich.core.opencv;

import org.opencv.core.Mat;

public class ImageSplitter {

	/**
	 * Splits an image into several Sub-images.
	 * Is depended on the specified Frame.
	 * 
	 * @param image Image to be split
	 * @param frameWidth Width of the ROI
	 * @param frameHeight Height of the ROI
	 * 
	 * @return result Matrix with split part of input image
	 */
	public static Mat[][] split(Mat image, int frameWidth, int frameHeight) {
		// catch if frame width % frame size != 0 
		final int frameCountX = (int) Math.round((image.width() / frameWidth) + 0.5) ;
		final int frameCountY = (int) Math.round((image.height() / frameHeight) + 0.5) ;

		Mat result[][] = new Mat[frameCountX][frameCountY];
		
		for (int frameX = 0; frameX < frameCountX; frameX++) {
			for (int frameY = 0; frameY < frameCountY; frameY++) {
				// care to stay in image border
				final int rowStart = frameY * frameHeight;
				final int rowEnd = Math.min((frameY + 1) * frameHeight, image.height());
				final int colStart = frameX * frameWidth;
				final int colEnd = Math.min((frameX + 1) * frameWidth, image.width());
				
				result[frameX][frameY] = image.submat(rowStart, rowEnd, colStart, colEnd);
			}
		}

		return result;
	}
}
