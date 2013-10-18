package de.thatsich.core.opencv;


// TODO BlurError
// TODO ScaleError
// TODO RotationError
public abstract class AErrorGenerator implements IErrorGenerator {
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
