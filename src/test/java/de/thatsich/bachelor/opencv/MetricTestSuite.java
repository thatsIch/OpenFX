package de.thatsich.bachelor.opencv;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.opencv.core.Core;

import de.thatsich.bachelor.opencv.metric.EuclideanDistanceTest;
import de.thatsich.bachelor.opencv.metric.HammingDistanceTest;
import de.thatsich.bachelor.opencv.metric.ManhattenDistanceTest;
import de.thatsich.bachelor.opencv.metric.MaximumDistanceTest;
import de.thatsich.bachelor.opencv.metric.SquaredEuclideanDistanceTest;

@RunWith(Suite.class)
@SuiteClasses({
	EuclideanDistanceTest.class,
	HammingDistanceTest.class,
	ManhattenDistanceTest.class,
	MaximumDistanceTest.class,
	SquaredEuclideanDistanceTest.class
})
public class MetricTestSuite {
	
	/**
	 * Initializing OpenCV Library for all TestClasses
	 */
	@BeforeClass
	public static void init() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
}
