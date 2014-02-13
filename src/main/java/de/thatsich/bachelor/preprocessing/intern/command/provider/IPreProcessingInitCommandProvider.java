package de.thatsich.bachelor.preprocessing.intern.command.provider;

import java.nio.file.Path;

import de.thatsich.bachelor.preprocessing.intern.command.commands.GetLastPreProcessingIndexCommand;
import de.thatsich.bachelor.preprocessing.intern.command.commands.GetLastPreProcessorIndexCommand;
import de.thatsich.bachelor.preprocessing.intern.command.commands.InitPreProcessingListCommand;
import de.thatsich.bachelor.preprocessing.intern.command.commands.InitPreProcessorListCommand;
import de.thatsich.core.guice.ICommandProvider;

public interface IPreProcessingInitCommandProvider extends ICommandProvider
{
	InitPreProcessorListCommand createInitPreProcessorListCommand();
	InitPreProcessingListCommand createInitPreProcessingListCommand( Path path );

	GetLastPreProcessorIndexCommand createGetLastPreProcessorIndexCommand();
	GetLastPreProcessingIndexCommand createGetLastPreProcessingIndexCommand();
}
