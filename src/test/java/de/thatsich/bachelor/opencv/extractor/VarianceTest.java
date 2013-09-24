package de.thatsich.bachelor.opencv.extractor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class VarianceTest extends AFeatureExtractorTest {
	
	public VarianceTest() {
		super(new Variance());
	}
	
	@Test
	public void testExtractFeature_One1x1_ShouldPass() {
		String test = super.extractor.extractFeature(super.one1x1).dump();
		String compare = super.zero1x1.dump();
		
		assertEquals(test + " ~= " + compare, test, compare);
	}
}
