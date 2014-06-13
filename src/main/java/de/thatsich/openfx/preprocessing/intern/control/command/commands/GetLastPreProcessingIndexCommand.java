package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.preprocessing.intern.control.command.service.EPreProcessingConfigType;
import de.thatsich.openfx.preprocessing.intern.control.command.service.PreProcessingConfigService;


public class GetLastPreProcessingIndexCommand extends ACommand<Integer>
{
	@Inject
	private PreProcessingConfigService config;

	@Override
	protected Integer call() throws Exception
	{
		return this.config.get(EPreProcessingConfigType.LAST_PRE_PROCESSING_INDEX);
	}
}
