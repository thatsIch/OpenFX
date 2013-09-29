package de.thatsich.core.opencv.error;

public abstract class AErrorGenerator implements IErrorGenerator {

	public String getName() {
		return this.getClass().getSimpleName();
	}
}
