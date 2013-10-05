package de.thatsich.core.opencv;

import javafx.util.StringConverter;


public abstract class ABinaryClassifier implements IBinaryClassifier {
	
	public static Converter CONVERTER = new Converter();
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * Private Class for Converting the BCs 
	 * into a String-Representation
	 * 
	 * @author Minh
	 */
	private static class Converter extends StringConverter<IBinaryClassifier> {
		@Override public String toString(IBinaryClassifier bc) { return bc.getName(); }
		@Override public IBinaryClassifier fromString(String paramString) { return null; }
	}
}
