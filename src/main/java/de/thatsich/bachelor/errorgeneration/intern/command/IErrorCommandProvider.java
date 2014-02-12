package de.thatsich.bachelor.errorgeneration.intern.command;

import java.nio.file.Path;

import org.opencv.core.Mat;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.errorgeneration.intern.command.commands.CreateErrorEntryCommand;
import de.thatsich.bachelor.errorgeneration.intern.command.commands.DeleteErrorEntryCommand;
import de.thatsich.bachelor.errorgeneration.intern.command.commands.SetLastErrorCountCommand;
import de.thatsich.bachelor.errorgeneration.intern.command.commands.SetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.errorgeneration.intern.command.commands.SetLastErrorGeneratorIndexCommand;
import de.thatsich.core.guice.ICommandProvider;

public interface IErrorCommandProvider extends ICommandProvider {
	public SetLastErrorEntryIndexCommand createSetLastErrorEntryIndexCommand(int lastErrorEntryIndex);
	public SetLastErrorGeneratorIndexCommand createSetLastErrorGeneratorIndexCommand(int lastErrorGeneratorIndex);
	public SetLastErrorCountCommand createSetLastErrorCountCommand(int lastErrorLoopCount);
	
	public CreateErrorEntryCommand createApplyErrorCommand(Mat imageMat, Path imagePath, IErrorGenerator generator);
	public DeleteErrorEntryCommand createDeleteErrorEntryCommand(ErrorEntry entry);
}
