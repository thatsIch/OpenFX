package de.thatsich.core.opencv.classifier;


public abstract class ABinaryClassifier implements IBinaryClassifier {
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
