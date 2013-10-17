package de.thatsich.bachelor.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV Service to read and write to a CSV filled with float values
 * 
 * @author Minh
 *
 */
public abstract class CSVService {
	
	
	/**
	 * writes a csv filled with a list of float-arrays to a specified path
	 * 
	 * @param csvPath Path to CSV
	 * @param values List of FloatArrays
	 * 
	 * @throws IOException when writing is not possible
	 */
	public static void write(Path csvPath, List<List<Float>> values) throws IOException {
		if (csvPath == null) throw new InvalidParameterException("Path is null.");
		if (values == null) throw new InvalidParameterException("Values is null.");
		if (values.size() == 0) throw new InvalidParameterException("Values are empty.");
		
		final BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.US_ASCII);

		for (int row = 0; row < values.size(); row++) {
			for (int col = 0; col < values.get(row).size(); col++) {
				writer.write(String.valueOf(values.get(row).get(col)).toString());
				
				if (col < values.get(row).size() - 1) writer.write(",");
			}
			
			if (row < values.size() - 1) writer.write("\n");
		}

		writer.close();
	}
	
	
	/**
	 * Reads a CSV File and splits them up into a List of floatarrays
	 * 
	 * @param csvPath Path to CSV File
	 * 
	 * @return List of the comma seperated float values in a row
	 * 
	 * @throws IOException when reading was not possible
	 */
	public static List<float[]> read(Path csvPath) throws IOException {
		if (csvPath == null) throw new InvalidParameterException();
		if (Files.notExists(csvPath)) throw new IOException("Path " + csvPath.toString() + " does not exist.");
		
		final List<float[]> result = new ArrayList<float[]>();
		
		final BufferedReader reader = Files.newBufferedReader(csvPath, StandardCharsets.US_ASCII);
		
		String stringRow;
		while((stringRow = reader.readLine()) != null) {
			final String[] stringFloats = stringRow.split(",");
			float[] floats = new float[stringFloats.length];
			
			for (int col = 0; col < stringFloats.length; col++) {
				floats[col] = Float.parseFloat(stringFloats[col]);
			}
			result.add(floats);
		}
		reader.close();

		return result;
	}
	
	
}
