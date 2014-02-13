package de.thatsich.bachelor.preprocessing.intern.command.commands;

import com.google.inject.Inject;

import de.thatsich.bachelor.preprocessing.intern.service.EPreProcessingConfigType;
import de.thatsich.bachelor.preprocessing.intern.service.PreProcessingConfigService;
import de.thatsich.core.javafx.ACommand;


public class GetLastPreProcessorIndexCommand extends ACommand<Integer>
{
	@Inject private PreProcessingConfigService	config;

	@Override
	protected Integer call() throws Exception
	{
		return this.config.get( EPreProcessingConfigType.LAST_PRE_PROCESSOR_INDEX );
	}
}
