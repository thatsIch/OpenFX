package de.thatsich.openfx.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.classification.api.model.IBinaryClassifiers;
import de.thatsich.openfx.classification.intern.control.classifier.core.IBinaryClassifier;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing the BinaryClassifierList
 *
 * @author Minh
 */
public class InitBinaryClassifierListSucceededHandler extends ACommandHandler<List<IBinaryClassifier>>
{
	@Inject private IBinaryClassifiers binaryClassifiers;

	@Override
	public void handle(List<IBinaryClassifier> classifierList)
	{
		this.binaryClassifiers.list().addAll(classifierList);
		this.log.info("Added BinaryClassifier to Database.");
	}
}	
