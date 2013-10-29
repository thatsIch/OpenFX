package de.thatsich.bachelor.featureextraction.restricted.command.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.featureextraction.restricted.services.FeatureConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastFeatureExtractorIndexCommand extends ACommand<Integer> {

	// Injections
	@Inject private FeatureConfigService config;

	@Override
	protected Integer call() throws Exception {
		return config.getLastFeatureExtractorIndexInt();
	}
}