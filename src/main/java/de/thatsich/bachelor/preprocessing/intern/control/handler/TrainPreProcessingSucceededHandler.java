package de.thatsich.bachelor.preprocessing.intern.control.handler;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.api.core.IBinaryClassifications;
import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for training BinaryClassifier
 * 
 * @author Minh
 */
public class TrainPreProcessingSucceededHandler extends ACommandHandler<IBinaryClassification> {

	@Inject private IBinaryClassifications binaryClassifications;

	@Override
	public void handle(IBinaryClassification classification) {
		this.binaryClassifications.getBinaryClassificationListProperty().add(classification);
		this.log.info("Added BinaryClassification to Database.");
		
		this.binaryClassifications.getSelectedBinaryClassificationProperty().set(classification);
		this.log.info("Select BinaryClassifcation.");
	}
}
