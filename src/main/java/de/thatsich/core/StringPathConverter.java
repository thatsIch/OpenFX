package de.thatsich.core;

import java.nio.file.Path;

import javafx.util.StringConverter;

public class StringPathConverter extends StringConverter<Path> {

	@Override
	public Path fromString(String string) {
		return null;
	}

	@Override
	public String toString(Path path) {
		return path.getFileName().toString();
	}

}
