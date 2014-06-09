package de.thatsich.openfx.preprocessing.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.GetLastPreProcessingIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.GetLastPreProcessorIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.InitPreProcessingsCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.InitPreProcessorsCommand;

public interface IPreProcessingInitCommandProvider extends ICommandProvider
{
	InitPreProcessorsCommand createInitPreProcessorListCommand();

	InitPreProcessingsCommand createInitPreProcessingsCommand();

	GetLastPreProcessorIndexCommand createGetLastPreProcessorIndexCommand();

	GetLastPreProcessingIndexCommand createGetLastPreProcessingIndexCommand();
}
