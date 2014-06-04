package de.thatsich.openfx.errorgeneration.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.errorgeneration.api.control.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.CreateErrorEntryCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.DeleteErrorEntryCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorCountCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorEntryIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorGeneratorIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;
import org.opencv.core.Mat;

import java.nio.file.Path;

public interface IErrorCommandProvider extends ICommandProvider
{
	SetLastErrorEntryIndexCommand createSetLastErrorEntryIndexCommand(int lastErrorEntryIndex);

	SetLastErrorGeneratorIndexCommand createSetLastErrorGeneratorIndexCommand(int lastErrorGeneratorIndex);

	SetLastErrorCountCommand createSetLastErrorCountCommand(int lastErrorLoopCount);

	CreateErrorEntryCommand createApplyErrorCommand(Path errorFolder, String errorClass, Mat imageMat, IErrorGenerator generator);

	DeleteErrorEntryCommand createDeleteErrorEntryCommand(ErrorEntry entry);
}
