package de.thatsich.openfx.imageprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.imageprocessing.intern.control.command.service.ImageConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastImageEntryIndexCommand extends ACommand<Void>
{

	// Properties
	private final int lastImageEntryIndex;

	// Injects
	@Inject ImageConfigService config;

	@Inject
	protected SetLastImageEntryIndexCommand(@Assisted int lastImageEntryIndex)
	{
		this.lastImageEntryIndex = lastImageEntryIndex;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastImageIndexInt(this.lastImageEntryIndex);

		return null;
	}
}
