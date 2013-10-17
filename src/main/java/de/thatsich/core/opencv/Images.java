package de.thatsich.core.opencv;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Images {

	/**
	 * Stores an Image Object back to HDD
	 * 
	 * @param image Image to be stored
	 * @param file Path to the resulting Image
	 */
	public static void store(Mat image, Path file) {
		Highgui.imwrite(file.toAbsolutePath().toString(), image);
	}
	
	/**
	 * Creates a new Scene for an image and renders it.
	 * 
	 * @param image Image to be shown
	 */
	public static void show(Image image) {
		final ImageView view = new ImageView();
		final Pane layout = new HBox();
		final Scene scene = new Scene(layout);
		final Stage stage = new Stage();
		
		view.setImage(image);
		layout.getChildren().add(view);
		stage.setScene(scene);
		
		stage.show();
	}
	
	/**
	 * Creates a new Scene for a Mat-Image and renders it
	 * 
	 * @param image Mat to be shown.
	 */
	public static void show(Mat image) {
		final ImageView view = new ImageView();
		final Pane layout = new HBox();
		final Scene scene = new Scene(layout);
		final Stage stage = new Stage();
		final Image img = toImage(image);
		
		view.setImage(img);
		layout.getChildren().add(view);
		stage.setScene(scene);
		
		stage.show();
	}
	
	/**
	 * Converts a Mat to an Image Object
	 * 
	 * @param m Mat to be converted
	 * @return Converted Image
	 */
	public static Image toImage(Mat m) {
		MatOfByte buffer = new MatOfByte();
		Highgui.imencode(".png", m, buffer);
		
		return new Image(new ByteArrayInputStream(buffer.toArray()));
	}
	
	/**
	 * Converts an Mat-Object to GrayScale
	 * 
	 * @param m To be converted Mat
	 * @return GrayScale Mat
	 */
	public static Mat toGrayScale(Mat m) {
		Imgproc.cvtColor(m, m, Imgproc.COLOR_RGB2GRAY);

		return m;
	}
	
	/**
	 * Splits a larger Mat-Image into several smaller ones,
	 * using a specific frame-size specified.
	 * 
	 * @param image Large Mat-Image to be split
	 * @param frameWidth Maximal Width of each resulting Frame-Image
	 * @param frameHeight Maximal Height of each resulting Frame-Image
	 * @return Matrix out of Frame-Size image pieces
	 */
	public static Mat[][] split(Mat image, int frameWidth, int frameHeight) {
		// catch if frame width % frame size != 0 
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (frameHeight == 0) throw new IllegalArgumentException("FrameHeight is 0.");
		if (frameWidth == 0) throw new IllegalArgumentException("FrameHeight is 0.");
		
		final int frameCountX = (int) Math.floor(image.width() / frameWidth) ;
		final int frameCountY = (int) Math.floor(image.height() / frameHeight) ;

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
	
	
	/**
	 * Calculates the max value of an int array.
	 * Faster than the actual Arrays operation since 
	 * there is no need to convert into a list first
	 * 
	 * @param array to be searched array
	 * @return max value of the searched array
	 */
	private static int maxValue(int[] array) {
		int max = 0;
		
		for (int i = 0; i < array.length; i++) {
			max = Math.max(max, array[i]);
		}
		
		return max;
	}
	
	/**
	 * Calculates the histogram of the input image.
	 * It needs to be grayscale (CvType.CV_8U).
	 * 
	 * @param image Image which the histogram is based on
	 * @return Histogram of image
	 */
	public static Mat getHistogram(Mat image) {
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
		final int peak = maxValue(histogram);
		
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
	
	
	/**
	 * Creates a Mat Object for the specified Path to the Image.
	 * 
	 * @param path Path to the Image
	 * @return Image converted to OpenCV Mat Object
	 */
	public static Mat toMat(Path path) {
		return Highgui.imread(path.toAbsolutePath().toString());
	}
}
