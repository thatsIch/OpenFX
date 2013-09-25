package de.thatsich.bachelor.opencv.metric;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import de.thatsich.core.opencv.metric.IMetric;

@RunWith(JUnit4.class)
public abstract class AMetricTest {
	
	protected IMetric metric;
	
	protected final Mat zero1x1 = Mat.zeros(1, 1, CvType.CV_8U);
	protected final Mat zero3x3 = Mat.zeros(3, 3, CvType.CV_8U);
	protected final Mat one1x1 = Mat.ones(1, 1, CvType.CV_8U);
	protected final Mat one3x3 = Mat.ones(3, 3, CvType.CV_8U);
	protected final Mat eye2x2 = Mat.eye(2, 2, CvType.CV_8U);
	protected final Mat eye3x3 = Mat.eye(3,3, CvType.CV_8U); 
	protected final Mat empty = new Mat();
	protected final Mat eye3x3NotGrayScale = Mat.eye(3,3, CvType.CV_8UC4); 
	
	/**
	 * Super CTOR
	 * 
	 * @param metric Metric which should be used
	 */
	public AMetricTest(IMetric metric) {
		this.metric = metric;
	}
	
	/**
	 * Initializing OpenCV Library for all TestClasses
	 */
	@BeforeClass
	public static void init() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getDistance_FirstNull_ShouldThrowException() {
		this.metric.getDistance(null, this.zero1x1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getDistance_SecNull_ShouldThrowException() {
		this.metric.getDistance(this.zero1x1, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getDistance_FirstNotGrayScale_ShouldThrowException() {
		this.metric.getDistance(this.eye3x3NotGrayScale, this.zero1x1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getDistance_SecNotGrayScale_ShouldThrowException() {
		this.metric.getDistance(this.zero1x1, this.eye3x3NotGrayScale);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getDistance_FirstEmpty_ShouldThrowException() {
		this.metric.getDistance(this.empty, this.zero1x1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getDistance_SecEmpty_ShouldThrowException() {
		this.metric.getDistance(this.zero1x1, this.empty);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getDistance_UnequalSize_ShouldThrowException() {
		this.metric.getDistance(this.eye2x2, this.eye3x3);
	}

	@Test
	public void getDistance_ShouldPass() {
		double test = this.metric.getDistance(this.eye2x2, this.eye2x2);
		double compare = 0;

		assertEquals(test + " ~= " + compare, test, compare, 1);
	}
}
