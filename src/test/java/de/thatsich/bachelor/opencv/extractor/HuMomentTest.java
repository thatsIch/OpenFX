package de.thatsich.bachelor.opencv.extractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.thatsich.bachelor.featureextraction.intern.command.extractor.HuMoments;

@RunWith(JUnit4.class)
public class HuMomentTest extends AFeatureExtractorTest {

	public HuMomentTest() {
		super(new HuMoments());
	}
	
	/**
	 * No real test needed since only wrapper
	 */
	@Test
	public void testExtractFeature_ShouldPass() {
		super.extractor.extractFeature(super.one1x1);
	}
}
