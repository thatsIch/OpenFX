package de.thatsich.bachelor.featureextraction.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.featureextraction.intern.services.FeatureConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastFrameSizeCommand extends ACommand<Void> {

	// Properties
	private final int lastFrameSize;
	
	// Injects
	@Inject private FeatureConfigService config;
	
	@Inject
	protected SetLastFrameSizeCommand(@Assisted int lastFrameSize) {
		this.lastFrameSize = lastFrameSize;
	}

	@Override
	protected Void call() throws Exception {
		config.setLastFrameSizeInt(this.lastFrameSize);
		
		return null;
	}
}
