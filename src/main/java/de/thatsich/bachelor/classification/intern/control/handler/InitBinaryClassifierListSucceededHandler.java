package de.thatsich.bachelor.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.classification.api.model.IBinaryClassifiers;
import de.thatsich.bachelor.classification.intern.control.classifier.core.IBinaryClassifier;
import de.thatsich.core.javafx.ACommandHandler;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the BinaryClassifierList
 *
 * @author Minh
 */
public class InitBinaryClassifierListSucceededHandler extends ACommandHandler<List<IBinaryClassifier>>
{

	@Inject
	private IBinaryClassifiers binaryClassifiers;

	@Override
	public void handle(List<IBinaryClassifier> classifierList)
	{
		this.binaryClassifiers.getBinaryClassifierListProperty().addAll(classifierList);
		this.log.info("Added BinaryClassifier to Database.");
	}
}	