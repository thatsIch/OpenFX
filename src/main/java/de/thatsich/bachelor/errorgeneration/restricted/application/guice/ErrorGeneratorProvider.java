package de.thatsich.bachelor.errorgeneration.restricted.application.guice;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;

public class ErrorGeneratorProvider {
	@Inject private Injector injector;

    public <T extends IErrorGenerator> T get(Class<T> type) {
        return injector.getInstance(type);
    }
}
