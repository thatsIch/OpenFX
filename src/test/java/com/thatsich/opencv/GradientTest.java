package com.thatsich.opencv;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import de.thatsich.bachelor.opencv.Gradient;

@RunWith(JUnit4.class)
public class GradientTest {

	private final Gradient gradient = new Gradient();
	private final Mat one1x1 = Mat.ones(1, 1, CvType.CV_8U);
	private final Mat zero1x1 = Mat.zeros(1, 1, CvType.CV_8U);
	
	@BeforeClass
	public static void init() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	@Test
	public void testExtractFeatureOne1x1() {
		Mat gradOne1x1 = gradient.extractFeature(one1x1);
		String test = gradOne1x1.dump();
		String compare = zero1x1.dump();
		
		assertEquals(test + " ~= " + compare, test, compare);
	}
}
