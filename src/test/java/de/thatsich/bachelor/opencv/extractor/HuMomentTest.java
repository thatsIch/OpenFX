package de.thatsich.bachelor.opencv.extractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class HuMomentTest extends AFeatureExtractorTest {

	public HuMomentTest() {
		super(new HuMoments());
	}
	
	// TODO wirklichen Test schreiben
	@Test
	public void testExtractFeature_ShouldPass() {
		super.extractor.extractFeature(super.one1x1);
	}
}
