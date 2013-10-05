package de.thatsich.core.javafx;

import java.nio.file.Path;

import javafx.scene.image.Image;


public abstract class PathImageConverter {

	public static Path toPath(Image i) {
		return null;
	}
	
	public static Image toImage(Path p) {
		return new Image("file:" + p);
	}
	
}
