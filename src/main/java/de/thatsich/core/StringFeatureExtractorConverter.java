package de.thatsich.core;

import javafx.util.StringConverter;
import de.thatsich.core.opencv.extractor.IFeatureExtractor;

public class StringFeatureExtractorConverter extends StringConverter<IFeatureExtractor> {

	@Override
	public IFeatureExtractor fromString(String string) {
		return null;
	}

	@Override
	public String toString(IFeatureExtractor featureGenerator) {
		return featureGenerator.getName();
	}

}
