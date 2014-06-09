package de.thatsich.openfx.preprocessing.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.GetLastPreProcessingIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.GetLastPreProcessorIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.InitPreProcessingsCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.InitPreProcessorsCommand;

import java.nio.file.Path;

public interface IPreProcessingInitCommandProvider extends ICommandProvider
{
	InitPreProcessorsCommand createInitPreProcessorListCommand();

	InitPreProcessingsCommand createInitPreProcessingsCommand(Path path);

	GetLastPreProcessorIndexCommand createGetLastPreProcessorIndexCommand();

	GetLastPreProcessingIndexCommand createGetLastPreProcessingIndexCommand();
}
