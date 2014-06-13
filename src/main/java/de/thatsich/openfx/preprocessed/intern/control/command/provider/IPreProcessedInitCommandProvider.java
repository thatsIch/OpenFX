package de.thatsich.openfx.preprocessed.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.preprocessed.intern.control.command.commands.InitPreProcessedsCommand;

public interface IPreProcessedInitCommandProvider extends ICommandProvider
{
	InitPreProcessedsCommand createInitPreProcessedsCommand();
}
