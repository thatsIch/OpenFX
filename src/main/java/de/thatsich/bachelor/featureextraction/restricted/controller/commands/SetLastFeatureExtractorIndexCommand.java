package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.featureextraction.restricted.services.FeatureConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastFeatureExtractorIndexCommand extends ACommand<Void> {

	// Properties
	private final IntegerProperty lastFeatureExtractorIndex = new SimpleIntegerProperty();
	
	// Injects
	@Inject private FeatureConfigService config;
	
	@Inject
	protected SetLastFeatureExtractorIndexCommand(@Assisted int lastFeatureExtractorIndex) {
		this.lastFeatureExtractorIndex.set(lastFeatureExtractorIndex);
	}

	@Override
	protected Void call() throws Exception {
		this.config.setLastFeatureExtractorIndexInt(this.lastFeatureExtractorIndex.get());
		
		return null;
	}
}