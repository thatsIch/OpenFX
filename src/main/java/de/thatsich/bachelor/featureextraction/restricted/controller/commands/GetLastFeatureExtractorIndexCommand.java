package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import javafx.concurrent.Task;

import com.google.inject.Inject;

import de.thatsich.bachelor.featureextraction.restricted.services.FeatureConfigService;
import de.thatsich.core.javafx.Command;

public class GetLastFeatureExtractorIndexCommand extends Command<Integer> {

	// Injections
	@Inject private FeatureConfigService config;

	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>() {
			@Override protected Integer call() throws Exception {
				return config.getLastFeatureExtractorIndexInt();
			}
		};
	}
}