package de.thatsich.bachelor.errorgeneration.restricted.command;

import java.nio.file.Path;

import org.opencv.core.Mat;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.ApplyErrorCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.CreateErrorImageCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.DeleteErrorEntryCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.GetLastErrorCountCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.GetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.GetLastErrorGeneratorIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.InitErrorEntryListCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.InitErrorGeneratorListCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.SetLastErrorCountCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.SetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.SetLastErrorGeneratorIndexCommand;
import de.thatsich.core.guice.ICommandProvider;

public interface ErrorCommandProvider extends ICommandProvider {
	public InitErrorEntryListCommand createInitErrorEntryListCommand(Path errorInputFolderPath);
	public InitErrorGeneratorListCommand createInitErrorGeneratorListCommand(); 
	
	public GetLastErrorGeneratorIndexCommand createGetLastErrorGeneratorIndexCommand();
	public GetLastErrorEntryIndexCommand createGetLastErrorEntryIndexCommand();
	public GetLastErrorCountCommand createGetLastErrorCountCommand();
	
	public SetLastErrorEntryIndexCommand createSetLastErrorEntryIndexCommand(int lastErrorEntryIndex);
	public SetLastErrorGeneratorIndexCommand createSetLastErrorGeneratorIndexCommand(int lastErrorGeneratorIndex);
	public SetLastErrorCountCommand createSetLastErrorCountCommand(int lastErrorLoopCount);
	
	public ApplyErrorCommand createApplyErrorCommand(Mat imageMat, Path imagePath, IErrorGenerator generator);
	public DeleteErrorEntryCommand createDeleteErrorEntryCommand(ErrorEntry entry);
	public CreateErrorImageCommand createCreateErrorImageCommand(ErrorEntry entry);
}
