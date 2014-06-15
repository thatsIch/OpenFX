package de.thatsich.openfx.prediction.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.intern.control.command.commands.DeleteBinaryPredictionCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.PredictNetworkCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.SetLastBinaryPredictionIndexCommand;


public interface IPredictionCommandProvider extends ICommandProvider
{
	SetLastBinaryPredictionIndexCommand createSetLastBinaryPredictionIndexCommand(int lastBinaryPredictionIndex);

	DeleteBinaryPredictionCommand createDeleteBinaryPredictionCommand(INetworkPrediction toBeDeletedBinaryPrediction);

	PredictNetworkCommand createPredictNetworkCommand(IImage image, IErrorGenerator errorGenerator, ITrainedNetwork network);
}
