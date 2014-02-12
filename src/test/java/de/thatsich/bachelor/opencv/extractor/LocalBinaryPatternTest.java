package de.thatsich.bachelor.opencv.extractor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

import de.thatsich.bachelor.featureextraction.intern.command.extractor.LocalBinaryPatternHistogram;

@RunWith(JUnit4.class)
public class LocalBinaryPatternTest extends AFeatureExtractorTest {

	public LocalBinaryPatternTest() {
		super(new LocalBinaryPatternHistogram());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testExtractFeature_TooSmall_ShouldThrowException() {
		super.extractor.extractFeature(super.one1x1);
	}
	
	@Test
	public void testExtractFeature_ShouldPass() {
		Mat histogram = super.extractor.extractFeature(super.eye3x3);
		String test = histogram.dump();

		// Because no neighbors are higher than center you have an entry in bin 0
		int[] compareHistogram = new int[256];
		compareHistogram[0] = 1;
		MatOfInt compareMat = new MatOfInt(compareHistogram);
		String compare = compareMat.dump();
		
		assertEquals(test + " ~= " + compare, test, compare);
	}
	
}
