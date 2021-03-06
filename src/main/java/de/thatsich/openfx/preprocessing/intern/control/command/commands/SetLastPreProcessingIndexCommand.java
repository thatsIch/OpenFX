package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.preprocessing.intern.control.command.service.EPreProcessingConfigType;
import de.thatsich.openfx.preprocessing.intern.control.command.service.PreProcessingConfigService;


public class SetLastPreProcessingIndexCommand extends ACommand<Void>
{
	// Fields
	private final int index;
	private final PreProcessingConfigService config;

	// CTOR
	@Inject
	protected SetLastPreProcessingIndexCommand(@Assisted int index, PreProcessingConfigService config)
	{
		this.index = index;
		this.config = config;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.set(EPreProcessingConfigType.LAST_PRE_PROCESSING_INDEX, this.index);

		return null;
	}
}
