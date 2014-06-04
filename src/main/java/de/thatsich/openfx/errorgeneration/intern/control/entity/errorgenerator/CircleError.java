package de.thatsich.openfx.errorgeneration.intern.control.entity.errorgenerator;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class CircleError extends AErrorGenerator
{
	private static final int RADIUS_MEAN = 50;
	private static final int RADIUS_STDDEV = 25;
	private static final int MIN_LINE_THICKNESS = 2;
	private static final int MAX_LINE_THICKNESS = 5;

	@Override
	public Mat generateError(Mat in)
	{
		// Initialize a Radian and Length based on the Settings
		final int radius = (int) Math.round(Math.random() * RADIUS_STDDEV + RADIUS_MEAN);

		// Calculates the Center Bounding Box of the Error
		final int offset_x = 2 * radius;
		final int offset_y = 2 * radius;

		// Check only the rest area if we want to see the whole boundary Box
		final int leftWidth = in.width() - offset_x;
		final int leftHeight = in.height() - offset_y;

		// Randomize First Point and add Offset to get Second.
		final int x1 = (int) Math.round(Math.random() * leftWidth) + radius;
		final int y1 = (int) Math.round(Math.random() * leftHeight) + radius;

		// Randomize Color and Thickness
		final Scalar color = new Scalar((int) Math.round(Math.random() * 255 + 1));
		final int thickness = (int) Math.round(Math.random() * (MAX_LINE_THICKNESS - MIN_LINE_THICKNESS) + MIN_LINE_THICKNESS);

		Core.circle(in, new Point(x1, y1), radius, color, thickness);
		// ellipse
		// rectangle
		// polylines
		return in;
	}

}
