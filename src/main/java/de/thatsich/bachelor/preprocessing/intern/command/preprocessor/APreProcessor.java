package de.thatsich.bachelor.preprocessing.intern.command.preprocessor;

import com.google.inject.Inject;

import de.thatsich.core.Log;


public abstract class APreProcessor implements IPreProcessor {
	// Injects
	@Inject protected Log log; 
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
