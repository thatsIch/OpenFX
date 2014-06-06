package de.thatsich.openfx.errorgeneration.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.GetLastErrorCountCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.GetLastErrorGeneratorIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.GetLastErrorIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.InitErrorGeneratorsCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.InitErrorsCommand;

public interface IErrorInitCommandProvider extends ICommandProvider
{
	InitErrorsCommand createInitErrorsCommand();

	InitErrorGeneratorsCommand createInitErrorGeneratorsCommand();

	GetLastErrorGeneratorIndexCommand createGetLastErrorGeneratorIndexCommand();

	GetLastErrorIndexCommand createGetLastErrorIndexCommand();

	GetLastErrorCountCommand createGetLastErrorCountCommand();
}
