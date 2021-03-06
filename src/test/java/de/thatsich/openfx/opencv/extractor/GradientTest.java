package de.thatsich.openfx.opencv.extractor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.thatsich.openfx.featureextraction.intern.control.extractor.Gradient;

@RunWith(JUnit4.class) 
public class GradientTest extends AFeatureExtractorTest {

	public GradientTest() {
		super(new Gradient());
	}
	
	@Test
	public void testExtractFeature_One1x1_ShouldPass() {
		String test = super.extractor.extractFeature(one1x1).dump();
		String compare = super.zero1x1.dump();
		
		assertEquals(test + " ~= " + compare, test, compare);
	}
}
