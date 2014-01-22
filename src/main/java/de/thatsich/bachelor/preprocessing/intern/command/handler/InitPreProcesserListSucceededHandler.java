package de.thatsich.bachelor.preprocessing.intern.command.handler;

import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.api.core.IBinaryClassifiers;
import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassifier;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for initializing the BinaryClassifierList
 * 
 * @author Minh
 */
public class InitPreProcesserListSucceededHandler extends ACommandHandler<List<IBinaryClassifier>> {

	@Inject private IBinaryClassifiers binaryClassifiers;

	@Override
	public void handle(List<IBinaryClassifier> classifierList) {
		this.binaryClassifiers.getBinaryClassifierListProperty().addAll(classifierList);
		this.log.info("Added BinaryClassifier to Database.");
	}
}	
