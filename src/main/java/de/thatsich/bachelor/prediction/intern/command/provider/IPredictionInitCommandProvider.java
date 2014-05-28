package de.thatsich.bachelor.prediction.intern.command.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.prediction.intern.command.commands.GetLastBinaryPredictionIndexCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.InitBinaryPredictionListCommand;
import de.thatsich.bachelor.prediction.intern.command.commands.InitPredictionFolderCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

public interface IPredictionInitCommandProvider extends ICommandProvider
{
	public InitPredictionFolderCommand createInitPredictionFolderCommand(@Assisted Path predictionFolderPath);

	public InitBinaryPredictionListCommand createInitBinaryPredictionListCommand(@Assisted Path predictionFolderPath);

	public GetLastBinaryPredictionIndexCommand createGetLastBinaryPredictionIndexCommand();
}
