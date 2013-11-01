package de.thatsich.bachelor.errorgeneration.restricted.command.handler;

import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.api.core.IErrorGenerators;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for initializing the Error Generator List
 * 
 * @author Minh
 */
public class InitErrorGeneratorListSucceededHandler extends ACommandHandler<List<IErrorGenerator>> {

	@Inject private IErrorGenerators errorGeneratorList;
	
	@Override
	public void handle(List<IErrorGenerator> generatorList) {
		this.errorGeneratorList.getErrorGeneratorListProperty().addAll(generatorList);
		this.log.info("Added all ErrorGenerators.");
	}
}
