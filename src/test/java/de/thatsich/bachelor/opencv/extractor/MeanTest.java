package de.thatsich.bachelor.opencv.extractor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.opencv.core.Mat;

import de.thatsich.bachelor.featureextraction.restricted.command.extractor.Mean;

@RunWith(JUnit4.class)
public class MeanTest extends AFeatureExtractorTest {

	/**
	 * CTOR
	 */
	public MeanTest() {
		super(new Mean());
	}
	
	@Test
	public void testExtractFeature_One1x1_ShouldPass() {
		Mat meanEye1x1 = super.extractor.extractFeature(super.one1x1);
		String test = meanEye1x1.dump();
		String compare = super.one1x1.dump();
		
		assertEquals(test + " ~= " + compare, test, compare);
	}
	
	@Test
	public void testExtractFeature_One3x3_ShouldPass() {
		Mat meanOne3x3 = super.extractor.extractFeature(super.one3x3);
		String test = meanOne3x3.dump();
		String compare = super.one1x1.dump();
		
		assertEquals(test + " ~= " + compare, test, compare);
	}
}
