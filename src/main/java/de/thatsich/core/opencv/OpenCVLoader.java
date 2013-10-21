package de.thatsich.core.opencv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.opencv.core.Core;

public class OpenCVLoader {

	public static void loadLibrary() {
		final String libName = getBitness() + "_" + Core.NATIVE_LIBRARY_NAME;
//		loadFromJar(libName);
		try {
			loadFromFileSystem(libName);	
		} catch (Exception e) {
			loadFromJar(libName);
		}
	}
	
	private static String getBitness() {
		final int bitness = Integer.parseInt(System.getProperty("sun.arch.data.model"));
		switch (bitness) {
			case 64: return "x64";
			case 32:
			default: return "x86";
		}
	}
	
	private static void loadFromFileSystem(String libName) throws Exception {
		System.out.println("Try loading CV-Lib from FileSystem.");
		try {
			System.loadLibrary(libName);
		} catch (Exception e) {
			throw new Exception("Could not load from FileSystem.");
		}
		System.out.println("Loaded OpenCV-Lib from FileSystem.");
	}
	
	private static void loadFromJar(String libName) {
		try {
			System.out.println("Try loading CV-Lib from JAR.");
			final InputStream is = OpenCVLoader.class.getClass().getResourceAsStream("/de/thatsich/core/opencv/" + libName + ".dll");
			final byte[] buffer = new byte[1024];
			int read = -1;
			final File temp = File.createTempFile(libName, ".dll");
			temp.deleteOnExit();
			final FileOutputStream fos = new FileOutputStream(temp);
			
			while ((read = is.read(buffer)) != -1) {
				fos.write(buffer, 0, read);
			}
			
			fos.close();
			is.close();
			
			System.load(temp.getAbsolutePath());
			System.out.println("Loaded OpenCV-Lib from JAR.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
