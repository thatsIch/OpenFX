package de.thatsich.bachelor.prediction.intern.control.handler;

import com.google.inject.Inject;

import de.thatsich.bachelor.prediction.api.core.IBinaryPredictions;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for testing the binary classification
 * 
 * @author Minh
 */
public class PredictBinaryClassificationSucceededHandler extends ACommandHandler<BinaryPrediction> {

	@Inject private IBinaryPredictions binaryPredictions;
	
	@Override
	public void handle(BinaryPrediction prediction) {
		this.binaryPredictions.getBinaryPredictionListProperty().add(prediction);
		this.log.info("Added BinaryPrediction to Database.");
		
		this.binaryPredictions.getSelectedBinaryPredictionProperty().set(prediction);
		this.log.info("Set current to selected BinaryPrediction.");
	}
}
