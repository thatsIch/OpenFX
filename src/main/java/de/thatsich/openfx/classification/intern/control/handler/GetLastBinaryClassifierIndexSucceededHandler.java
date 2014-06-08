package de.thatsich.openfx.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.classification.api.model.IBinaryClassifiers;

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
		final IBinaryClassifier selected = this.binaryClassifiers.list().get(value);
		this.binaryClassifiers.selected().set(selected);
		this.log.info("Set LastBinaryClassifierIndex in Model.");
	}
}
