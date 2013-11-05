package de.thatsich.bachelor.prediction.intern.control.handler;

import java.util.List;

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
public class DeleteBinaryPredictionSucceededHandler extends ACommandHandler<BinaryPrediction> {
	
	@Inject private IBinaryPredictions binaryPredictions;
	
	@Override public void handle(BinaryPrediction value) {
		final List<BinaryPrediction> binaryPredictionList = this.binaryPredictions.getBinaryPredictionListProperty();
		binaryPredictionList.remove(value);
		this.log.info("Removed BinaryPrediction from List.");
		
		if (binaryPredictionList.size() > 0) {
			final BinaryPrediction first = binaryPredictionList.get(0);
			this.binaryPredictions.setSelectedBinaryPrediction(first);
			this.log.info("Reset to first BinaryPrediction.");
		}
	}
}