package de.thatsich.openfx.imageprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.service.ImageConfigService;

public class GetLastImageEntryIndexCommand extends ACommand<Integer>
{
	@Inject private ImageConfigService config;

	@Override
	protected Integer call() throws Exception
	{
		return this.config.getLastImageIndexInt();
	}
}
