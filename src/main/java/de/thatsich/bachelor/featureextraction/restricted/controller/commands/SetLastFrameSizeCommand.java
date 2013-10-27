package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.featureextraction.restricted.services.FeatureConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastFrameSizeCommand extends ACommand<Void> {

	// Properties
	private final IntegerProperty lastFrameSize = new SimpleIntegerProperty();
	
	// Injects
	@Inject private FeatureConfigService config;
	
	@Inject
	protected SetLastFrameSizeCommand(@Assisted int lastFrameSize) {
		this.lastFrameSize.set(lastFrameSize);
	}

	@Override
	protected Void call() throws Exception {
		config.setLastFrameSizeInt(lastFrameSize.get());
		
		return null;
	}
}
