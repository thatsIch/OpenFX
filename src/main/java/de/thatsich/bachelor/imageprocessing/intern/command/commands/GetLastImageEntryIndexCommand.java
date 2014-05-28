package de.thatsich.bachelor.imageprocessing.intern.command.commands;

import com.google.inject.Inject;
import de.thatsich.bachelor.imageprocessing.intern.service.ImageConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastImageEntryIndexCommand extends ACommand<Integer>
{

	// Injects
	@Inject
	private ImageConfigService config;

	@Override
	protected Integer call() throws Exception
	{
		return config.getLastImageIndexInt();
	}
}
