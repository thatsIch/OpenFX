package de.thatsich.bachelor.classificationtraining.restricted.model.logic;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;
import de.thatsich.core.Log;

public abstract class ABinaryClassification implements IBinaryClassification {
	// Injects
	@Inject protected Log log; 
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
