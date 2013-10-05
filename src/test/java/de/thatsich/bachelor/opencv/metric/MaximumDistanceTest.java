package de.thatsich.bachelor.opencv.metric;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.thatsich.core.opencv.MaximumDistance;

public class MaximumDistanceTest extends AMetricTest {
	public MaximumDistanceTest() {
		super(new MaximumDistance());
	}

	@Test
	public void getDistance_UnequalMatrix_ShouldPass() {
		double test = super.metric.getDistance(super.zero1x1, super.one1x1);
		double compare = 1;
		
		assertEquals(test + " ~= " + compare, test, compare, 0);
	}

	@Test
	public void getDistance_UnequalBiggerMatrix_ShouldPass() {
		double test = super.metric.getDistance(super.zero3x3, super.one3x3);
		double compare = 1;
		
		assertEquals(test + " ~= " + compare, test, compare, 0);
	}
}
