package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorFileStorageService;

import java.util.List;


public class InitErrorsCommand extends ACommand<List<IError>>
{
	private final ErrorFileStorageService storage;

	@Inject
	protected InitErrorsCommand(ErrorFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<IError> call() throws Exception
	{
		return this.storage.init();
	}
}
