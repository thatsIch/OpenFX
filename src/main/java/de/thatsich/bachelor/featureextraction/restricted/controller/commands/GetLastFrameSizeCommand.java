package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.featureextraction.restricted.services.FeatureConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastFrameSizeCommand extends ACommand<Integer> {

	// Injects
	@Inject private FeatureConfigService config;

	@Override
	protected Integer call() throws Exception {
		return config.getLastFrameSizeInt();
	}
}
