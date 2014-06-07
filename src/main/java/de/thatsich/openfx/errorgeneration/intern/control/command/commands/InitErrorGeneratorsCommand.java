package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.entity.errorgenerator.CircleError;
import de.thatsich.openfx.errorgeneration.intern.control.entity.errorgenerator.LineError;

import java.util.Arrays;
import java.util.List;

public class InitErrorGeneratorsCommand extends ACommand<List<IErrorGenerator>>
{
	@Override
	protected List<IErrorGenerator> call() throws Exception
	{
		final List<IErrorGenerator> errorGeneratorList = Arrays.asList(new LineError(), new CircleError());

		return errorGeneratorList;
	}
}
