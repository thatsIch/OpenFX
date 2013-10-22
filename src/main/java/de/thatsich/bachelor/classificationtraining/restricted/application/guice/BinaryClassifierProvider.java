package de.thatsich.bachelor.classificationtraining.restricted.application.guice;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;

public class BinaryClassifierProvider {
	
	@Inject private Injector injector;

    public <T extends IBinaryClassifier> T get(Class<T> type) {
        return injector.getInstance(type);
    }
}
