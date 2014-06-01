package de.thatsich.bachelor.errorgeneration.intern.control.provider;

import de.thatsich.bachelor.errorgeneration.intern.control.command.commands.GetLastErrorCountCommand;
import de.thatsich.bachelor.errorgeneration.intern.control.command.commands.GetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.errorgeneration.intern.control.command.commands.GetLastErrorGeneratorIndexCommand;
import de.thatsich.bachelor.errorgeneration.intern.control.command.commands.InitErrorEntryListCommand;
import de.thatsich.bachelor.errorgeneration.intern.control.command.commands.InitErrorGeneratorListCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

public interface IErrorInitCommandProvider extends ICommandProvider
{
	public InitErrorEntryListCommand createInitErrorEntryListCommand(Path errorInputFolderPath);

	public InitErrorGeneratorListCommand createInitErrorGeneratorListCommand();

	public GetLastErrorGeneratorIndexCommand createGetLastErrorGeneratorIndexCommand();

	public GetLastErrorEntryIndexCommand createGetLastErrorEntryIndexCommand();

	public GetLastErrorCountCommand createGetLastErrorCountCommand();
}
