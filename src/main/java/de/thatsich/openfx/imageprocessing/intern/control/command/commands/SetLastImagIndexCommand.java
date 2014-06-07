package de.thatsich.openfx.imageprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.service.ImageConfigService;

public class SetLastImagIndexCommand extends ACommand<Void>
{

	// Properties
	private final int lastImageEntryIndex;
	private final ImageConfigService config;

	@Inject
	protected SetLastImagIndexCommand(@Assisted int lastImageEntryIndex, ImageConfigService config)
	{
		this.lastImageEntryIndex = lastImageEntryIndex;
		this.config = config;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastImageIndexInt(this.lastImageEntryIndex);

		return null;
	}
}
