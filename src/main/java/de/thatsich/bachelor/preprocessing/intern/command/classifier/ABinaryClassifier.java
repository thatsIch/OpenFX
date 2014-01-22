package de.thatsich.bachelor.classification.intern.command.classifier;

import com.google.inject.Inject;

import de.thatsich.core.Log;


public abstract class ABinaryClassifier implements IBinaryClassifier {
	// Injects
	@Inject protected Log log; 
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
