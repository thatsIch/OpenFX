package de.thatsich.bachelor.classification.intern.control.handler;

import javafx.collections.ObservableList;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.api.core.IBinaryClassifications;
import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for deleting the error
 * 
 * @author Minh
 */
public class RemoveBinaryClassificationSucceededHandler extends ACommandHandler<IBinaryClassification> {
	
	@Inject private IBinaryClassifications binaryClassifications;

	@Override
	public void handle(IBinaryClassification deletion) {
		final ObservableList<IBinaryClassification> binaryClassificationList = this.binaryClassifications.getBinaryClassificationListProperty();
		binaryClassificationList.remove(deletion);
		log.info("Removed BinaryClassification from Database.");
		
		if (binaryClassificationList.size() > 0) {
			final IBinaryClassification first = binaryClassificationList.get(0);
			this.binaryClassifications.getSelectedBinaryClassificationProperty().set(first);
			this.log.info("Reset Selection to first BinaryClassifcation.");
		} else {
			this.binaryClassifications.getSelectedBinaryClassificationProperty().set(null);
			this.log.info("Reset Selection to null.");
		}
	}
}
