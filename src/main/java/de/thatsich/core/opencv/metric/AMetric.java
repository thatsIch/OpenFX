package de.thatsich.core.opencv.metric;

import com.google.inject.Inject;

import de.thatsich.core.Log;

public abstract class AMetric implements IMetric {

	@Inject
	protected Log log;
	
	@Override 
	public String getName() { 
		return this.getClass().getSimpleName(); 
	}
}
