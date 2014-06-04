package de.thatsich.openfx.errorgeneration.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.api.model.IErrorGenerators;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the Error Generator List
 *
 * @author Minh
 */
public class InitErrorGeneratorListSucceededHandler extends ACommandHandler<List<IErrorGenerator>>
{
	@Inject private IErrorGenerators errorGeneratorList;

	@Override
	public void handle(List<IErrorGenerator> generatorList)
	{
		this.errorGeneratorList.list().addAll(generatorList);
		this.log.info("Added all ErrorGenerators.");
	}
}
