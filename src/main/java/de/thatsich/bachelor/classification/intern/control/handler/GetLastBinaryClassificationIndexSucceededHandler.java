package de.thatsich.bachelor.classification.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.classification.api.control.IBinaryClassification;
import de.thatsich.bachelor.classification.api.model.IBinaryClassifications;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for getting the LastFeatureVectorIndex
 *
 * @author Minh
 */
public class GetLastBinaryClassificationIndexSucceededHandler extends ACommandHandler<Integer>
{

	@Inject
	private IBinaryClassifications binaryClassifications;

	@Override
	public void handle(Integer value)
	{
		if (value >= 0)
		{
			final IBinaryClassification selected = this.binaryClassifications.binaryClassifications().get(value);
			this.binaryClassifications.selectedBinaryClassification().set(selected);
			log.info("Set last selected BinaryClassification in Model.");
		}
	}
}