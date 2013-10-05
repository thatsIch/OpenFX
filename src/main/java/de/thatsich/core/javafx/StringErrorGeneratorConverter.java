package de.thatsich.core.javafx;

import javafx.util.StringConverter;
import de.thatsich.core.opencv.IErrorGenerator;

public class StringErrorGeneratorConverter extends StringConverter<IErrorGenerator> {

	@Override
	public IErrorGenerator fromString(String string) {
		return null;
	}

	@Override
	public String toString(IErrorGenerator errorGenerator) {
		return errorGenerator.getName();
	}

}
