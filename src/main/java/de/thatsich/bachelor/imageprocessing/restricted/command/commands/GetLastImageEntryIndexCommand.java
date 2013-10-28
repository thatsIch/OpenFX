package de.thatsich.bachelor.imageprocessing.restricted.command.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.restricted.service.ImageConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastImageEntryIndexCommand extends ACommand<Integer> {

	// Injects
	@Inject private ImageConfigService config;

	@Override
	protected Integer call() throws Exception {
		return config.getLastImageIndexInt();
	}
}
