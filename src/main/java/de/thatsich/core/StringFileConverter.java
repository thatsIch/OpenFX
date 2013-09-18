package de.thatsich.core;

import java.io.File;

import javafx.util.StringConverter;

public class StringFileConverter extends StringConverter<File> {

	@Override
	public String toString(File file) {
		return file.getName();
	}
	
	@Override
	public File fromString(String string) {
		return null;
	}

}
