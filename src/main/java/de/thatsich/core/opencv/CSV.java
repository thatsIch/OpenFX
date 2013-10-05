package de.thatsich.core.opencv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;

public abstract class CSV {
	public static Mat read(Path csvPath) throws IOException {
		if (csvPath == null) throw new InvalidParameterException();
		if (Files.notExists(csvPath)) throw new IOException();
		
		final BufferedReader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8);
		
		String stringRow;
//		int row = 0;
		final Mat result = new Mat();
//		final Mat result = new MatOfFloat();
		while((stringRow = reader.readLine()) != null) {
			String[] stringFloats = stringRow.split(",");
			
//			MatOfFloat floatMat = new MatOfFloat();
			float[] floats = new float[stringFloats.length];
			for (int col = 0; col < stringFloats.length; col++) {
				floats[col] = Float.parseFloat(stringFloats[col]);
			}
			result.push_back(new MatOfFloat(floats));
			
//			row++;
		}
		
		reader.close();
		
		System.out.println(result.dump());
		
		return new Mat();
	}
	
	public static void write(Path csvPath, Mat content) throws IOException {
		if (csvPath == null) throw new InvalidParameterException("Path is null.");
		if (content == null) throw new InvalidParameterException("Mat is null.");
		if (content.empty()) throw new InvalidParameterException("Mat is empty.");
		if (content.channels() > 1) throw new InvalidParameterException("MatChannelSize is not applicible (> 1).");
		
		final BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8);

		for (int row = 0; row < content.rows(); row++) {
			for (int col = 0; col < content.cols(); col++) {
				writer.write(String.valueOf(content.get(row, col)[0]));
				
				if (col < content.cols() - 1) writer.write(", ");
			}
			
			writer.write("\n");
		}

		writer.close();
	}
}
