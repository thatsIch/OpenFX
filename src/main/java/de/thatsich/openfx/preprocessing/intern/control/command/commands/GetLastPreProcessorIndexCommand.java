package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.openfx.preprocessing.intern.control.command.service.EPreProcessingConfigType;
import de.thatsich.openfx.preprocessing.intern.control.command.service.PreProcessingConfigService;
import de.thatsich.core.javafx.ACommand;


public class GetLastPreProcessorIndexCommand extends ACommand<Integer>
{
	@Inject
	private PreProcessingConfigService config;

	@Override
	protected Integer call() throws Exception
	{
		return this.config.get(EPreProcessingConfigType.LAST_PRE_PROCESSOR_INDEX);
	}
}
