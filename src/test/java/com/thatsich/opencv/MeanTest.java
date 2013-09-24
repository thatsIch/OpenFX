package com.thatsich.opencv;

import static org.junit.Assert.assertEquals;

import java.security.InvalidParameterException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import de.thatsich.bachelor.opencv.Mean;

@RunWith(JUnit4.class)
public class MeanTest {

	private final Mean mean = new Mean();
	private final Mat eye1x1 = Mat.eye(1, 1, CvType.CV_8U);
	
	@BeforeClass
	public static void init() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	@Test(expected = InvalidParameterException.class)
	public void testExtractFeature_NullImage_ShouldThrowException() {
		@SuppressWarnings("unused")
		Mat np = mean.extractFeature(null);
	}
	
	@Test
	public void testExtractFeatureEye1x1() {
		Mat meanEye1x1 = mean.extractFeature(eye1x1);
		String test = meanEye1x1.dump();
		String compare = eye1x1.dump();
		
		assertEquals(test + " ~= " + compare, test, compare);
	}
	
	@Test
	public void testExtractFeatureOne3x3() {
		Mat one1x1 = Mat.ones(1, 1, CvType.CV_8U);
		Mat one3x3 = Mat.ones(3, 3, CvType.CV_8U);
		Mat meanOne3x3 = mean.extractFeature(one3x3);

		assertEquals(meanOne3x3.dump() + " ~= " + one1x1.dump(), meanOne3x3.dump(), one1x1.dump());
	}
	
	@Test
	@Ignore
	public void testCompareFeature() {
		
	}
}
