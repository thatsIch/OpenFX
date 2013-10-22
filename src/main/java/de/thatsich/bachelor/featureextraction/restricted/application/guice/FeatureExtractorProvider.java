package de.thatsich.bachelor.featureextraction.restricted.application.guice;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.thatsich.bachelor.featureextraction.api.entities.IFeatureExtractor;

public class FeatureExtractorProvider {
	@Inject private Injector injector;

    public <T extends IFeatureExtractor> T get(Class<T> type) {
        return injector.getInstance(type);
    }
}
