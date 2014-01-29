package de.thatsich.bachelor.classification.intern.command.preprocessing.core;

import com.google.inject.Inject;

import de.thatsich.core.Log;

public abstract class APreProcessing implements IPreProcessing
{
	// Injects
	@Inject protected Log log; 
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
