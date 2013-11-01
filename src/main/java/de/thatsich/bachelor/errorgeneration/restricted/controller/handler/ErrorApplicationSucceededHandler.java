package de.thatsich.bachelor.errorgeneration.restricted.controller.handler;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.restricted.command.IErrorCommandProvider;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.CreateErrorImageCommand;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for applying the error
 * 
 * @author Minh
 */
public class ErrorApplicationSucceededHandler extends ACommandHandler<ErrorEntry> {

	@Inject private IErrorCommandProvider commander;
	
	@Override
	public void handle(ErrorEntry error) {
		final CreateErrorImageCommand command = this.commander.createCreateErrorImageCommand(error);
		command.setOnSucceededCommandHandler(CreateErrorEntrySucceededHandler.class);
		command.start();
	}
}
