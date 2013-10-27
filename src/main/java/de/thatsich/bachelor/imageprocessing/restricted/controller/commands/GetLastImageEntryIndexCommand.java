package de.thatsich.bachelor.imageprocessing.restricted.controller.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.restricted.services.ImageConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastImageEntryIndexCommand extends ACommand<Integer> {

	// Injects
	@Inject private ImageConfigService config;

	@Override
	protected Integer call() throws Exception {
		return config.getLastImageIndexInt();
	}
}
