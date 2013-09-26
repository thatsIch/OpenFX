package de.thatsich.core.opencv;

import java.io.ByteArrayInputStream;

import javafx.scene.image.Image;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

// TODO Check if this was implemented correctly, getting only black image
public abstract class ImageConverter {
	
	public static Image matToImage(Mat m) {
		MatOfByte buffer = new MatOfByte();
		Highgui.imencode(".png", m, buffer);
		
		return new Image(new ByteArrayInputStream(buffer.toArray()));
	}
	
	public static Mat toGrayScale(Mat m) {
		Imgproc.cvtColor(m, m, Imgproc.COLOR_RGB2GRAY);

		return m;
	}
}
