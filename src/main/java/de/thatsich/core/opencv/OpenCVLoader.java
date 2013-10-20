package de.thatsich.core.opencv;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OpenCVLoader {

	public static void loadLibrary() {
		System.out.println(Paths.get("").toAbsolutePath());
		try {
			Path fileIn = null;
			Path fileOut = null;
			String osName = System.getProperty("os.name");
			System.out.println(osName);
			if(osName.startsWith("Windows")){
				int bitness = Integer.parseInt(System.getProperty("sun.arch.data.model"));
				if(bitness == 32){
					System.out.println("32 bit detected");
//					fileIn = Paths.get("/opencv/x86/opencv_java245.dll");
//					fileOut = Files.createTempFile("lib", ".dll");
				}
				else if (bitness == 64){
					System.out.println("64 bit detected");
//					fileIn = Paths.get("/opencv/x64/opencv_java245.dll");
//					fileOut = Files.createTempFile("lib", ".dll");
				}
				else{
					System.out.println("Unknown bit detected - trying with 32 bit");
//					fileIn = Paths.get("/opencv/x86/opencv_java245.dll");
//					fileOut = Files.createTempFile("lib", ".dll");
				}
//
//				fileOut.toFile().deleteOnExit();
//				Files.copy(fileIn, fileOut);
//				System.loadLibrary(fileOut.toString());
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to load opencv native library", e);
		}
	}
}
