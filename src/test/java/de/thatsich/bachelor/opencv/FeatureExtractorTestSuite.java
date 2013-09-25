package de.thatsich.bachelor.opencv;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.opencv.core.Core;

import de.thatsich.bachelor.opencv.extractor.GradientTest;
import de.thatsich.bachelor.opencv.extractor.GrayLevelCooccurenceHistogramTest;
import de.thatsich.bachelor.opencv.extractor.HuMomentTest;
import de.thatsich.bachelor.opencv.extractor.LocalBinaryPatternTest;
import de.thatsich.bachelor.opencv.extractor.MeanTest;
import de.thatsich.bachelor.opencv.extractor.VarianceTest;

@RunWith(Suite.class)
@SuiteClasses({
	GradientTest.class,
	GrayLevelCooccurenceHistogramTest.class,
	HuMomentTest.class,
	LocalBinaryPatternTest.class,
	MeanTest.class,
	VarianceTest.class
})

public class FeatureExtractorTestSuite {
	
	/**
	 * Initializing OpenCV Library for all TestClasses
	 */
	@BeforeClass
	public static void init() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
}
