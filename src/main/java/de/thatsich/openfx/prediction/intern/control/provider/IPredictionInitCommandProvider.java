package de.thatsich.openfx.prediction.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.prediction.intern.control.command.commands.GetLastBinaryPredictionIndexCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.InitBinaryPredictionsCommand;

public interface IPredictionInitCommandProvider extends ICommandProvider
{
	public InitBinaryPredictionsCommand createInitBinaryPredictionsCommand();

	public GetLastBinaryPredictionIndexCommand createGetLastBinaryPredictionIndexCommand();
}
