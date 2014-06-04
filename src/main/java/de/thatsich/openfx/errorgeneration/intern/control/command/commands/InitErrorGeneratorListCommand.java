package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.entity.errorgenerator.CircleError;
import de.thatsich.openfx.errorgeneration.intern.control.entity.errorgenerator.LineError;

import java.util.ArrayList;
import java.util.List;

public class InitErrorGeneratorListCommand extends ACommand<List<IErrorGenerator>>
{
	@Override
	protected List<IErrorGenerator> call() throws Exception
	{
		final List<IErrorGenerator> errorGeneratorList = new ArrayList<>();

		errorGeneratorList.add(this.get(LineError.class));
		errorGeneratorList.add(this.get(CircleError.class));

		return errorGeneratorList;
	}

	private <T extends IErrorGenerator> T get(Class<T> type)
	{
		return super.injector.getInstance(type);
	}
}
