package de.thatsich.openfx.errorgeneration.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.CreateErrorEntryCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.DeleteErrorEntryCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorCountCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorEntryIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorGeneratorIndexCommand;
import org.opencv.core.Mat;

public interface IErrorCommandProvider extends ICommandProvider
{
	SetLastErrorEntryIndexCommand createSetLastErrorEntryIndexCommand(int lastErrorEntryIndex);

	SetLastErrorGeneratorIndexCommand createSetLastErrorGeneratorIndexCommand(int lastErrorGeneratorIndex);

	SetLastErrorCountCommand createSetLastErrorCountCommand(int lastErrorLoopCount);

	CreateErrorEntryCommand createApplyErrorCommand(String errorClass, Mat imageMat, IErrorGenerator generator);

	DeleteErrorEntryCommand createDeleteErrorEntryCommand(IError entry);
}
