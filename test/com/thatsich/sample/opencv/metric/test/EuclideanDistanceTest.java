package com.thatsich.sample.opencv.metric.test;

import junit.framework.TestCase;
import junit.runner.Version;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.thatsich.sample.opencv.metric.EuclideanDistance;

public class EuclideanDistanceTest extends TestCase {

	private EuclideanDistance euclid;
	
	protected void setUp() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		this.euclid = new EuclideanDistance();
	}
	
	@Test
	public void testIdenticalMatrix() {
		Mat original = Mat.ones(10, 10, CvType.CV_8SC1);
		Mat compare = Mat.ones(10, 10, CvType.CV_8SC1);
		
		assertEquals(0.0, this.euclid.getDistance(original, compare));
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Test
	public void testUnmatchingSize() throws IllegalStateException {
		Mat original = Mat.ones(10, 10, CvType.CV_8SC1);
		Mat compare = Mat.ones(5, 5, CvType.CV_8SC1);

		exception.expect(IllegalStateException.class);
		exception.expectMessage("Size of original and compare differ.");
		
		this.euclid.getDistance(original, compare);
	}
	
	@Test
	public void testVersion() {
		assertEquals("4.11", Version.id());
	}
}
