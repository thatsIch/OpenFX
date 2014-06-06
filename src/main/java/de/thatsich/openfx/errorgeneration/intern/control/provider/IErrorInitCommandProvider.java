package de.thatsich.openfx.errorgeneration.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.GetLastErrorCountCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.GetLastErrorEntryIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.GetLastErrorGeneratorIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.InitErrorGeneratorListCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.InitErrorsCommand;

import java.nio.file.Path;

public interface IErrorInitCommandProvider extends ICommandProvider
{
	InitErrorsCommand createInitErrorEntryListCommand(Path errorInputFolderPath);

	InitErrorGeneratorListCommand createInitErrorGeneratorListCommand();

	GetLastErrorGeneratorIndexCommand createGetLastErrorGeneratorIndexCommand();

	GetLastErrorEntryIndexCommand createGetLastErrorEntryIndexCommand();

	GetLastErrorCountCommand createGetLastErrorCountCommand();
}
