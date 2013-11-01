package de.thatsich.bachelor.classification.intern.command.handler;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.api.core.IBinaryClassifications;
import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for getting the LastFeatureVectorIndex
 * 
 * @author Minh
 */
public class GetLastBinaryClassificationIndexSucceededHandler extends ACommandHandler<Integer> {

	@Inject private IBinaryClassifications binaryClassifications;
	
	@Override
	public void handle(Integer value) {
		final IBinaryClassification selectedBinaryClassification = this.binaryClassifications.getBinaryClassificationListProperty().get(value);
		this.binaryClassifications.setSelectedBinaryClassification(selectedBinaryClassification);
		log.info("Set last selected BinaryClassification in Model.");
	}
}