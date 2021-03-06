package de.thatsich.openfx.featureextraction.intern.control.extractor;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.intern.control.extractor.core.AFeatureExtractor;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;

public class Variance extends AFeatureExtractor implements IFeatureExtractor
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

		MatOfDouble variance = new MatOfDouble();
		Core.meanStdDev(image, new MatOfDouble(), variance);
		double[] varianceArray = variance.toArray();

		// convert to float array
		float floatVal = (float) varianceArray[0];

		return new MatOfFloat(floatVal);
	}
}
