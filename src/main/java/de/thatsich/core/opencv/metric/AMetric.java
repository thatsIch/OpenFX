package de.thatsich.core.opencv.metric;


public abstract class AMetric implements IMetric {
	@Override 
	public String getName() { 
		return this.getClass().getSimpleName(); 
	}
}
