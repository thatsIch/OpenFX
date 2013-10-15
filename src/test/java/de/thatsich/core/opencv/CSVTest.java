package de.thatsich.core.opencv;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CSVTest {

	@Test(expected = InvalidParameterException.class)
	public void testWrite_PathNull_ShouldThrowException() throws IOException {
		List<float[]> test = new ArrayList<float[]>();
		test.add(new float[]{1});
		CSVService.write(null, test);
	}

	@Test(expected = InvalidParameterException.class)
	public void testWrite_ValuesNull_ShouldThrowException() throws IOException {
		CSVService.write(Paths.get("").resolve("Silly Path"), null);
	}

	@Test(expected = InvalidParameterException.class)
	public void testWrite_ValuesEmpty_ShouldThrowException() throws IOException {
		CSVService.write(Paths.get(""), new ArrayList<float[]>());
	}

	@Test(expected = InvalidParameterException.class)
	public void testRead_PathNull_ShouldThrowException() throws IOException {
		CSVService.read(null);
	}
	
	@Test(expected = IOException.class)
	public void testRead_PathSilly_ShouldThrowException() throws IOException {
		CSVService.read(Paths.get("").resolve("Silly Path"));
	}
}
