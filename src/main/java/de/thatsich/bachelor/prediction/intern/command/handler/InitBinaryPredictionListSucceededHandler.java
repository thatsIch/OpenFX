package de.thatsich.bachelor.prediction.intern.command.handler;

import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.prediction.api.core.IBinaryPredictions;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for initializing the feature vector list
 * 
 * @author Minh
 */
public class InitBinaryPredictionListSucceededHandler extends ACommandHandler<List<BinaryPrediction>> {

	@Inject private IBinaryPredictions binaryPredictions;
	
	@Override
	public void handle(List<BinaryPrediction> binaryPredictionList) {
		this.binaryPredictions.getBinaryPredictionListProperty().addAll(binaryPredictionList);
		this.log.info("Added BinaryPredictionList to Database.");
	}
}