package de.thatsich.bachelor.errorgeneration.intern.command;

import java.nio.file.Path;

import de.thatsich.bachelor.errorgeneration.intern.command.commands.GetLastErrorCountCommand;
import de.thatsich.bachelor.errorgeneration.intern.command.commands.GetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.errorgeneration.intern.command.commands.GetLastErrorGeneratorIndexCommand;
import de.thatsich.bachelor.errorgeneration.intern.command.commands.InitErrorEntryListCommand;
import de.thatsich.bachelor.errorgeneration.intern.command.commands.InitErrorGeneratorListCommand;
import de.thatsich.core.guice.ICommandProvider;

public interface IErrorInitCommandProvider extends ICommandProvider {
	public InitErrorEntryListCommand createInitErrorEntryListCommand(Path errorInputFolderPath);
	public InitErrorGeneratorListCommand createInitErrorGeneratorListCommand(); 
	
	public GetLastErrorGeneratorIndexCommand createGetLastErrorGeneratorIndexCommand();
	public GetLastErrorEntryIndexCommand createGetLastErrorEntryIndexCommand();
	public GetLastErrorCountCommand createGetLastErrorCountCommand();
}
