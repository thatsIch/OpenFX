package de.thatsich.bachelor.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.classification.api.model.IBinaryClassifiers;
import de.thatsich.bachelor.classification.intern.control.classifier.core.IBinaryClassifier;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for getting LastBinaryClassifierIndex
 *
 * @author Minh
 */
public class GetLastBinaryClassifierIndexSucceededHandler extends ACommandHandler<Integer>
{

	@Inject
	private IBinaryClassifiers binaryClassifiers;

	@Override
	public void handle(Integer value)
	{
		final IBinaryClassifier selected = this.binaryClassifiers.binaryClassifiers().get(value);
		this.binaryClassifiers.selectedBinaryClassifier().set(selected);
		this.log.info("Set LastBinaryClassifierIndex in Model.");
	}
}
