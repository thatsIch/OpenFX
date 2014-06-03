package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.preprocessing.intern.control.command.service.EPreProcessingConfigType;
import de.thatsich.openfx.preprocessing.intern.control.command.service.PreProcessingConfigService;
import de.thatsich.core.javafx.ACommand;


public class SetLastPreProcessorIndexCommand extends ACommand<Void>
{
	private final int lastPreProcessorIndex;
	private final PreProcessingConfigService config;

	@Inject
	protected SetLastPreProcessorIndexCommand(@Assisted int lastPreProcessorIndex, PreProcessingConfigService config)
	{
		this.lastPreProcessorIndex = lastPreProcessorIndex;
		this.config = config;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.set(EPreProcessingConfigType.LAST_PRE_PROCESSOR_INDEX, this.lastPreProcessorIndex);

		return null;
	}

}
