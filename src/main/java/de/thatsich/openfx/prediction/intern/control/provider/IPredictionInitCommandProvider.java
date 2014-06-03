package de.thatsich.openfx.prediction.intern.control.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.prediction.intern.control.command.commands.GetLastBinaryPredictionIndexCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.InitBinaryPredictionListCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.InitPredictionFolderCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

public interface IPredictionInitCommandProvider extends ICommandProvider
{
	public InitPredictionFolderCommand createInitPredictionFolderCommand(@Assisted Path predictionFolderPath);

	public InitBinaryPredictionListCommand createInitBinaryPredictionListCommand(@Assisted Path predictionFolderPath);

	public GetLastBinaryPredictionIndexCommand createGetLastBinaryPredictionIndexCommand();
}
