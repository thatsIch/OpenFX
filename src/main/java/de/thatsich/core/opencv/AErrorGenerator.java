package de.thatsich.core.opencv;

public abstract class AErrorGenerator implements IErrorGenerator {

	public String getName() {
		return this.getClass().getSimpleName();
	}
}
