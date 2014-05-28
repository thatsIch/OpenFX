package de.thatsich.bachelor.errorgeneration.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import de.thatsich.bachelor.errorgeneration.api.entities.CircleError;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.errorgeneration.api.entities.LineError;
import de.thatsich.core.javafx.ACommand;

import java.util.ArrayList;
import java.util.List;

public class InitErrorGeneratorListCommand extends ACommand<List<IErrorGenerator>>
{

	@Inject
	private Injector injector;

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
		return injector.getInstance(type);
	}
}
