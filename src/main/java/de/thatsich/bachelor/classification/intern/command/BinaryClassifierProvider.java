package de.thatsich.bachelor.classification.intern.command;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassifier;

public class BinaryClassifierProvider {
	
	@Inject private Injector injector;

    public <T extends IBinaryClassifier> T get(Class<T> type) {
        return injector.getInstance(type);
    }
}
