package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.featureextraction.restricted.services.FeatureConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastFeatureVectorIndexCommand extends ACommand<Void> {

	// Properties
	private final IntegerProperty lastFeatureVectorIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject private FeatureConfigService config;
	
	@Inject
	protected SetLastFeatureVectorIndexCommand(@Assisted int lastFeatureVectorIndex) {
		this.lastFeatureVectorIndex.set(lastFeatureVectorIndex);
	}
	
	@Override
	protected Void call() throws Exception {
		config.setLastFeatureVectorIndexInt(lastFeatureVectorIndex.get());
		
		return null;
	}
}