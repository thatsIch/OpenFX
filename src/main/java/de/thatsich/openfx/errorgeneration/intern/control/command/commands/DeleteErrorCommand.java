package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorFileStorageService;

public class DeleteErrorCommand extends ACommand<IError>
{
	final private IError entry;
	private final ErrorFileStorageService storage;

	@Inject
	public DeleteErrorCommand(@Assisted IError entry, ErrorFileStorageService storage)
	{
		this.entry = entry;
		this.storage = storage;
	}

	@Override
	protected IError call() throws Exception
	{
		this.storage.delete(this.entry);
		this.log.info("Error deleted.");

		return this.entry;
	}

}
