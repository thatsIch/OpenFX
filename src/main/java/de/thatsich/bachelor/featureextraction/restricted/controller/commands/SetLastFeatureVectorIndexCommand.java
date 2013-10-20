package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import de.thatsich.bachelor.featureextraction.restricted.services.FeatureConfigService;
import de.thatsich.core.javafx.Command;

public class SetLastFeatureVectorIndexCommand extends Command<Void> {

	// Properties
	private final IntegerProperty lastFeatureVectorIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject private FeatureConfigService config;
	
	@Inject
	protected SetLastFeatureVectorIndexCommand(@Assisted int lastFeatureVectorIndex) {
		this.lastFeatureVectorIndex.set(lastFeatureVectorIndex);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override protected Void call() throws Exception {
				config.setLastFeatureVectorIndexInt(lastFeatureVectorIndex.get());
				
				return null;
			}
		};
	}
}