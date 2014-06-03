package de.thatsich.openfx.imageprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.openfx.imageprocessing.intern.control.command.service.ImageConfigService;
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
