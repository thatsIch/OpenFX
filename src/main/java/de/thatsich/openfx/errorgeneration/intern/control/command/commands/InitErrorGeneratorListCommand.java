package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import de.thatsich.openfx.errorgeneration.api.control.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.error.CircleError;
import de.thatsich.openfx.errorgeneration.intern.control.error.LineError;
import de.thatsich.core.javafx.ACommand;

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
