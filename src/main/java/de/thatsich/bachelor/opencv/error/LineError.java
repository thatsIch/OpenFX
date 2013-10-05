package de.thatsich.bachelor.opencv.error;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import de.thatsich.core.opencv.AErrorGenerator;
import de.thatsich.core.opencv.IErrorGenerator;

public class LineError extends AErrorGenerator implements IErrorGenerator {

	private final int MEAN_LENGTH = 100;
	private final int STDDEV_LENTH = 50;
	private final int MIN_LINE_THICKNESS = 2;
	private final int MAX_LINE_THICKNESS = 5;
	
	@Override
	public Mat generateError(Mat in) {
		// Initialize a Radian and Length based on the Settings
		final int length = (int) Math.round(Math.random() * this.STDDEV_LENTH + this.MEAN_LENGTH);
		final double radian = (3 * Math.PI / 2) + (Math.random() * Math.PI);
		
		// Calculates the Bounding Box of the Error
		final int offset_x = (int) Math.round(Math.cos(radian) * length);
		final int offset_y = (int) Math.round(Math.sin(radian) * length);
		
		// Check only the rest area if we want to see the whole boundary Box
		final int leftWidth = in.width() - offset_x;
		final int leftHeight = in.height() - Math.abs(offset_y);
		
		// Randomize First Point and add Offset to get Second.
		final int x1 = (int) Math.round(Math.random() * leftWidth);
		final int y1 = (int) Math.round(Math.random() * leftHeight) - (offset_y  < 0 ? offset_y : 0) ;
		final int x2 = x1 + offset_x;
		final int y2 = y1 + offset_y;
		
		// Randomize Color and Thickness
		final Scalar color = new Scalar((int) Math.round(Math.random() * 255 + 1));
		final int thickness = (int) Math.round(Math.random() * (this.MAX_LINE_THICKNESS - MIN_LINE_THICKNESS) + MIN_LINE_THICKNESS);
		
		Core.line(in, new Point(x1, y1), new Point(x2, y2), color, thickness);
		// circle
		// ellipse
		// rectangle
		// polylines
		return in;
	}

}
