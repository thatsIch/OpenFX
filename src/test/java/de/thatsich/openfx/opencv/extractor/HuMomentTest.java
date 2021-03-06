package de.thatsich.openfx.opencv.extractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.thatsich.openfx.featureextraction.intern.control.extractor.HuMoments;

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
