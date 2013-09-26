package de.thatsich.core.opencv;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
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
		final Image img = matToImage(image);
		
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
	public static Image matToImage(Mat m) {
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
