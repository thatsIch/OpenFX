package de.thatsich.bachelor.preprocessing.intern.control.command.provider;

import de.thatsich.bachelor.preprocessing.intern.control.command.commands.GetLastPreProcessingIndexCommand;
import de.thatsich.bachelor.preprocessing.intern.control.command.commands.GetLastPreProcessorIndexCommand;
import de.thatsich.bachelor.preprocessing.intern.control.command.commands.InitPreProcessingListCommand;
import de.thatsich.bachelor.preprocessing.intern.control.command.commands.InitPreProcessorListCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

public interface IPreProcessingInitCommandProvider extends ICommandProvider
{
	InitPreProcessorListCommand createInitPreProcessorListCommand();

	InitPreProcessingListCommand createInitPreProcessingListCommand(Path path);

	GetLastPreProcessorIndexCommand createGetLastPreProcessorIndexCommand();

	GetLastPreProcessingIndexCommand createGetLastPreProcessingIndexCommand();
}
