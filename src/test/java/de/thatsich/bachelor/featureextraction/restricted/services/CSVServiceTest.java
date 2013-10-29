package de.thatsich.bachelor.featureextraction.restricted.services;

import java.io.IOException;
import java.security.InvalidParameterException;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JukitoRunner.class)
public class CSVServiceTest {

	// Injects
//	@Inject private CSVService csvService;
	
	@Before
	public void setupMocks() {
		
	}
	
	@Test(expected = InvalidParameterException.class)
	public void testWrite_PathNull_ShouldThrowException() throws IOException {
//		List mockedList = mock
//		Blockin
//		List<List<Float>> test = FXCollections.observableArrayList();
//		test.add(new float[]{1});
//		csvService.write(null, test);
	}

	@Test(expected = InvalidParameterException.class)
	public void testWrite_ValuesNull_ShouldThrowException() throws IOException {
//		csvService.write(Paths.get("").resolve("Silly Path"), null);
	}

	@Test(expected = InvalidParameterException.class)
	public void testWrite_ValuesEmpty_ShouldThrowException() throws IOException {
//		CSVService.write(Paths.get(""), new ArrayList<float[]>());
	}

	@Test(expected = InvalidParameterException.class)
	public void testRead_PathNull_ShouldThrowException() throws IOException {
//		csvService.read(null);
	}
	
	@Test(expected = IOException.class)
	public void testRead_PathSilly_ShouldThrowException() throws IOException {
//		csvService.read(Paths.get("").resolve("Silly Path"));
	}
}
