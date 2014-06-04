package de.thatsich.openfx.featureextraction.intern.control.extractor;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.intern.control.extractor.core.AFeatureExtractor;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;


/**
 * Calculates the Co-occurence Histrogram for Gray-Levels
 *
 * @author Minh
 */
public class GrayLevelCooccurenceHistogram extends AFeatureExtractor implements IFeatureExtractor
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

		float[] histogram = new float[256];

		// Runtime Variables
		int rows = image.rows();
		int cols = image.cols();
		for (int row = 0; row < rows; row++)
		{
			final boolean hasTop = this.hasTop(row, cols, rows, cols);

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
					histogram[center]++;
					histogram[direction]++;
				}

				if (hasTop)
				{
					final int direction = (int) image.get(row - 1, col)[0];
					histogram[center]++;
					histogram[direction]++;
				}

				if (hasTopRight)
				{
					final int direction = (int) image.get(row - 1, col + 1)[0];
					histogram[center]++;
					histogram[direction]++;
				}

				if (hasRight)
				{
					final int direction = (int) image.get(row, col + 1)[0];
					histogram[center]++;
					histogram[direction]++;
				}
			}
		}

		return new MatOfFloat(histogram);
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
