package de.thatsich.core.opencv;

import javafx.util.StringConverter;

// TODO BlurError
// TODO ScaleError
// TODO RotationError
public abstract class AErrorGenerator implements IErrorGenerator {

	public static final StringConverter<IErrorGenerator> CONVERTER = new StringErrorGeneratorConverter(); 
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	private static class StringErrorGeneratorConverter extends StringConverter<IErrorGenerator> {

		@Override
		public IErrorGenerator fromString(String string) {
			return null;
		}

		@Override
		public String toString(IErrorGenerator errorGenerator) {
			return errorGenerator.getName();
		}

	}
}
