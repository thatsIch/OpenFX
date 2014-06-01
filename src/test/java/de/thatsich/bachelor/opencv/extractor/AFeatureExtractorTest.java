package de.thatsich.bachelor.opencv.extractor;

import de.thatsich.bachelor.featureextraction.api.control.IFeatureExtractor;
import de.thatsich.core.opencv.OpenCVLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

@RunWith(JUnit4.class)
public abstract class AFeatureExtractorTest
{

	protected final IFeatureExtractor extractor;
	protected final Mat zero1x1 = Mat.zeros(1, 1, CvType.CV_8U);
	protected final Mat one1x1 = Mat.ones(1, 1, CvType.CV_8U);
	protected final Mat one3x3 = Mat.ones(3, 3, CvType.CV_8U);
	protected final Mat eye2x2 = Mat.eye(2, 2, CvType.CV_8U);
	protected final Mat eye3x3 = Mat.eye(3, 3, CvType.CV_8U);
	protected final Mat eye3x3NotGrayScale = Mat.eye(3, 3, CvType.CV_8UC4);

	public AFeatureExtractorTest(IFeatureExtractor extractor)
	{
		this.extractor = extractor;
	}

	/**
	 * Initializing OpenCV Library for all TestClasses
	 */
	@BeforeClass
	public static void init()
	{
		OpenCVLoader.loadLibrary();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExtractFeature_NullImage_ShouldThrowException()
	{
		extractor.extractFeature(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExtractFeature_NotGrayScale_ShouldThrowException()
	{
		extractor.extractFeature(this.eye3x3NotGrayScale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExtractFeature_EmptyImage_ShouldThrowException()
	{
		extractor.extractFeature(new Mat());
	}
}
