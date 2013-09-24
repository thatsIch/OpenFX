package com.thatsich.opencv;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

import de.thatsich.bachelor.opencv.LocalBinaryPatternHistogram;

@RunWith(JUnit4.class)
public class LocalBinaryPatternTest {

	private final LocalBinaryPatternHistogram lbp = new LocalBinaryPatternHistogram();
	private final Mat eye1x1 = Mat.eye(1,1, CvType.CV_8U); 
	private final Mat eye3x3 = Mat.eye(3,3, CvType.CV_8U); 
	private final Mat eye3x3NotGrayScale = Mat.eye(3,3, CvType.CV_8UC4); 
	
	@BeforeClass
	public static void init() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testExtractFeature_NullImage_ShouldThrowException() {
		lbp.extractFeature(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testExtractFeature_NotGrayScale_ShouldThrowException() {
		lbp.extractFeature(this.eye3x3NotGrayScale);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testExtractFeature_TooSmall_ShouldThrowException() {
		lbp.extractFeature(this.eye1x1);
	}
	
	@Test
	public void testExtractFeature_ShouldPass() {
		Mat histogram = lbp.extractFeature(this.eye3x3);
		String test = histogram.dump();

		// Because no neighbors are higher than center you have an entry in bin 0
		int[] compareHistogram = new int[256];
		compareHistogram[0] = 1;
		MatOfInt compareMat = new MatOfInt(compareHistogram);
		String compare = compareMat.dump();
		
		assertEquals(test + " ~= " + compare, test, compare);
	}
	
}
