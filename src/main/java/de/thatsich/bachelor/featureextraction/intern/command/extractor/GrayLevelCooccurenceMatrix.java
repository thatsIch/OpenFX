package de.thatsich.bachelor.featureextraction.intern.command.extractor;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;

public class GrayLevelCooccurenceMatrix extends AFeatureExtractor
{

	@Override
	public MatOfFloat extractFeature(Mat image)
	{
		if (image == null)
		{
			throw new IllegalArgumentException("Image is null.");
		}
		if (image.type() != CvType.CV_8U)
		{
			throw new IllegalArgumentException("Image is not grayscale.");
		}
		if (image.empty())
		{
			throw new IllegalArgumentException("Image is empty.");
		}

		Mat GLCM = new Mat(256, 256, CvType.CV_8UC1);

		// Runtime Variables
		final int rows = image.rows();
		final int cols = image.cols();
		for (int row = 0; row < rows; row++)
		{
			final boolean hasTop = this.hasTop(row, 0, rows, cols);

			for (int col = 0; col < cols; col++)
			{
				final boolean hasLeft = this.hasLeft(row, col, rows, cols);
				final boolean hasRight = this.hasRight(row, col, rows, cols);
				final boolean hasTopLeft = hasTop && hasLeft;
				final boolean hasTopRight = hasTop && hasRight;

				final int center = (int) image.get(row, col)[0];

				if (hasTopLeft)
				{
					final int direction = (int) image.get(row - 1, col - 1)[0];
					final double currentValue = GLCM.get(center, direction)[0];
					GLCM.put(center, direction, currentValue + 1);
					GLCM.put(direction, center, currentValue + 1);
				}

				if (hasTop)
				{
					final int direction = (int) image.get(row - 1, col)[0];
					final double currentValue = GLCM.get(center, direction)[0];
					GLCM.put(center, direction, currentValue + 1);
					GLCM.put(direction, center, currentValue + 1);
				}

				if (hasTopRight)
				{
					final int direction = (int) image.get(row - 1, col + 1)[0];
					final double currentValue = GLCM.get(center, direction)[0];
					GLCM.put(center, direction, currentValue + 1);
					GLCM.put(direction, center, currentValue + 1);
				}

				if (hasRight)
				{
					final int direction = (int) image.get(row, col + 1)[0];
					final double currentValue = GLCM.get(center, direction)[0];
					GLCM.put(center, direction, currentValue + 1);
					GLCM.put(direction, center, currentValue + 1);
				}
			}
		}

		// Transform the GLCM to be used;
		GLCM = GLCM.reshape(1, 1);
		GLCM.convertTo(GLCM, CvType.CV_32F);

		return new MatOfFloat(GLCM);
	}

	// Directional Helper function
	private boolean hasTop(int row, int col, int rows, int cols)
	{
		return row > 0;
	}

	private boolean hasLeft(int row, int col, int rows, int cols)
	{
		return col > 0;
	}

	private boolean hasRight(int row, int col, int rows, int cols)
	{
		return col < col - 1;
	}
}
