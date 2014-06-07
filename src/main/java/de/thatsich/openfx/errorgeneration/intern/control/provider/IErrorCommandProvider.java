package de.thatsich.openfx.errorgeneration.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.CreateErrorCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.DeleteErrorCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorCountCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorGeneratorIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorIndexCommand;
import org.opencv.core.Mat;

public interface IErrorCommandProvider extends ICommandProvider
{
	SetLastErrorIndexCommand createSetLastErrorEntryIndexCommand(int lastErrorEntryIndex);

	SetLastErrorGeneratorIndexCommand createSetLastErrorGeneratorIndexCommand(int lastErrorGeneratorIndex);

	SetLastErrorCountCommand createSetLastErrorCountCommand(int lastErrorLoopCount);

	CreateErrorCommand createApplyErrorCommand(String errorClass, Mat imageMat, IErrorGenerator generator);

	DeleteErrorCommand createDeleteErrorEntryCommand(IError entry);
}
