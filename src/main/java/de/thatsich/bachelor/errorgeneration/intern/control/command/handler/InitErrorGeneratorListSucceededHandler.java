package de.thatsich.bachelor.errorgeneration.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.errorgeneration.api.model.IErrorGenerators;
import de.thatsich.bachelor.errorgeneration.api.control.IErrorGenerator;
import de.thatsich.core.javafx.ACommandHandler;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the Error Generator List
 *
 * @author Minh
 */
public class InitErrorGeneratorListSucceededHandler extends ACommandHandler<List<IErrorGenerator>>
{
	@Inject	private IErrorGenerators errorGeneratorList;

	@Override
	public void handle(List<IErrorGenerator> generatorList)
	{
		this.errorGeneratorList.errorGenerators().addAll(generatorList);
		this.log.info("Added all ErrorGenerators.");
	}
}
