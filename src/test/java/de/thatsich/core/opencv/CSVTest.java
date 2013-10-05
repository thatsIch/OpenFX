package de.thatsich.core.opencv;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.opencv.core.Core;
import org.opencv.core.Mat;

@RunWith(JUnit4.class)
public class CSVTest {

	/**
	 * Initializing OpenCV Library for all TestClasses
	 */
	@BeforeClass
	public static void init() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	@Test
	public void testWrite() throws IOException {
		Mat m = Mat.zeros(2, 2, 0);

		CSV.write(Paths.get("test.csv"), m);
	}
	
	@Test
	public void testRead() throws IOException {
		Mat m = CSV.read(Paths.get("test.csv"));
		
		m.dump();
	}

}
