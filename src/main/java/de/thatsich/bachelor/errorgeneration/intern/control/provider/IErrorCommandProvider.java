package de.thatsich.bachelor.errorgeneration.intern.control.provider;

import de.thatsich.bachelor.errorgeneration.intern.control.error.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.api.control.IErrorGenerator;
import de.thatsich.bachelor.errorgeneration.intern.control.command.commands.CreateErrorEntryCommand;
import de.thatsich.bachelor.errorgeneration.intern.control.command.commands.DeleteErrorEntryCommand;
import de.thatsich.bachelor.errorgeneration.intern.control.command.commands.SetLastErrorCountCommand;
import de.thatsich.bachelor.errorgeneration.intern.control.command.commands.SetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.errorgeneration.intern.control.command.commands.SetLastErrorGeneratorIndexCommand;
import de.thatsich.core.guice.ICommandProvider;
import org.opencv.core.Mat;

import java.nio.file.Path;

public interface IErrorCommandProvider extends ICommandProvider
{
	public SetLastErrorEntryIndexCommand createSetLastErrorEntryIndexCommand(int lastErrorEntryIndex);

	public SetLastErrorGeneratorIndexCommand createSetLastErrorGeneratorIndexCommand(int lastErrorGeneratorIndex);

	public SetLastErrorCountCommand createSetLastErrorCountCommand(int lastErrorLoopCount);

	public CreateErrorEntryCommand createApplyErrorCommand(Mat imageMat, Path imagePath, IErrorGenerator generator);

	public DeleteErrorEntryCommand createDeleteErrorEntryCommand(ErrorEntry entry);
}
