package de.thatsich.bachelor.opencv.extractor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.opencv.core.MatOfInt;

import de.thatsich.bachelor.featureextraction.restricted.command.extractor.GrayLevelCooccurenceHistogram;

@RunWith(JUnit4.class)
public class GrayLevelCooccurenceHistogramTest extends AFeatureExtractorTest {

	public GrayLevelCooccurenceHistogramTest() {
		super(new GrayLevelCooccurenceHistogram());
	}
	
	@Test
	public void test_ExtractFeature_ShouldPass() {
		String test = super.extractor.extractFeature(eye2x2).dump();
		
		int[] compareHistogram = new int[256];
		compareHistogram[0] = 1;
		compareHistogram[1] = 1;
		MatOfInt compareMat = new MatOfInt(compareHistogram);
		String compare = compareMat.dump();
		
		assertEquals(test + " ~= " + compare, test, compare);
	}
	
	
}
