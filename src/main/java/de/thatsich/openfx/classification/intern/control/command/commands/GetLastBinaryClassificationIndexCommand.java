package de.thatsich.openfx.classification.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationConfigService;

public class GetLastBinaryClassificationIndexCommand extends ACommand<Integer>
{
	// Injections
	@Inject
	private ClassificationConfigService config;

	@Override
	protected Integer call() throws Exception
	{
		return this.config.getLastBinaryClassificationIndexInt();
	}
}
