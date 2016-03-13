package de.thatsich.openfx.opencv.extractor;

import de.thatsich.openfx.featureextraction.intern.control.extractor.GrayLevelCooccurenceHistogram;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.opencv.core.MatOfInt;

@RunWith(JUnit4.class)
public class GrayLevelCooccurenceHistogramTest extends AFeatureExtractorTest {

	public GrayLevelCooccurenceHistogramTest() {
		super(new GrayLevelCooccurenceHistogram());
	}
	
	@Test
	public void test_ExtractFeature_ShouldPass() {
		final String actual = super.extractor.extractFeature(eye2x2).dump();
		
		final int[] compareHistogram = new int[256];
		compareHistogram[0] = 2;
		compareHistogram[1] = 4;
		final MatOfInt compareMat = new MatOfInt(compareHistogram);
		final String expected = compareMat.dump();

		Assertions.assertThat(actual).isEqualTo(expected);
	}
}
