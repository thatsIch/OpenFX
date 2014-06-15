package de.thatsich.openfx.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.CreateErrorCommand;
import de.thatsich.openfx.errorgeneration.intern.control.provider.IErrorCommandProvider;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.intern.control.command.service.NetworkPredictionFileStorageService;
import de.thatsich.openfx.prediction.intern.control.entity.NetworkPrediction;
import de.thatsich.openfx.prediction.intern.control.entity.NetworkPredictionConfig;
import org.opencv.core.Mat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PredictNetworkCommand extends ACommand<INetworkPrediction>
{
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

	private final IImage image;
	private final ITrainedNetwork network;
	private final IErrorCommandProvider errorProvider;
	private final IErrorGenerator errorGenerator;

	// Injections
	@Inject
	private NetworkPredictionFileStorageService storage;

	@Inject
	private PredictNetworkCommand(@Assisted IImage image, @Assisted IErrorGenerator errorGenerator, @Assisted ITrainedNetwork network, IErrorCommandProvider errorProvider)
	{
		this.image = image;
		this.network = network;
		this.errorProvider = errorProvider;
		this.errorGenerator = errorGenerator;
	}

	/*
	 * Extracts error and determines if a frame has an Error
	 * which will be placed in the second layer.
	 * You run through the prediction of the frame withError
	 * and multiple the prediction value (between 0 and 1)
	 * with 255 to get a decent color value. This will be placed
	 * into the third image layer.
	 */
	@Override
	protected INetworkPrediction call() throws Exception
	{
		final CreateErrorCommand createErrorCommand = this.errorProvider.createCreateErrorCommand(this.image, this.errorGenerator, false, false, false);
		final IError createdError = createErrorCommand.call();
		final Mat modified = createdError.modifiedProperty().get();
		this.log.info("Prepared Image Content.");

		final String creationTime = format.format(new Date());
		final String predictedClassName = this.network.predict(createdError);
		final String strID = UUID.randomUUID().toString();
		this.log.info("Resolved FileName.");

		final NetworkPredictionConfig config = new NetworkPredictionConfig(creationTime, predictedClassName, strID);
		final INetworkPrediction prediction = new NetworkPrediction(config, modified);
		this.log.info("Created Binary Prediction.");

		this.storage.create(prediction);
		this.log.info("Stored Prediction to FileSystem.");


		return prediction;
	}
}
