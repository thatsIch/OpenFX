package de.thatsich.openfx.classification.intern.control.classifier.core;

import com.google.inject.Inject;
import de.thatsich.core.Log;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;


public abstract class ABinaryClassifier implements IBinaryClassifier
{
	// Injects
	@Inject protected Log log;

	@Override
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
}
