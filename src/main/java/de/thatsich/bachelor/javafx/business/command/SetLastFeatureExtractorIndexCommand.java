package de.thatsich.bachelor.javafx.business.command;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.service.ConfigService;
import de.thatsich.core.javafx.Command;

public class SetLastFeatureExtractorIndexCommand extends Command<Void> {

	// Properties
	private final IntegerProperty lastFeatureExtractorIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject ConfigService config;
	
	@Inject
	protected SetLastFeatureExtractorIndexCommand(@Assisted int lastFeatureExtractorIndex) {
		this.lastFeatureExtractorIndex.set(lastFeatureExtractorIndex);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override protected Void call() throws Exception {
				config.setLastFeatureExtractorIndexInt(lastFeatureExtractorIndex.get());
				
				return null;
			}
		};
	}
}