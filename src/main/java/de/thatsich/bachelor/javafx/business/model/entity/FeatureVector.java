package de.thatsich.bachelor.javafx.business.model.entity;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import org.opencv.core.MatOfFloat;

/**
 * Represents a FeatureVector with serveral information 
 * how it was created.
 * 
 * @author Minh
 *
 */
public class FeatureVector {
	
	// Properties
	private final ReadOnlyStringWrapper className = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper extractorName = new ReadOnlyStringWrapper();
	private final ReadOnlyIntegerWrapper frameSize = new ReadOnlyIntegerWrapper();
	private final ReadOnlyStringWrapper id = new ReadOnlyStringWrapper();
	private final ReadOnlyObjectWrapper<MatOfFloat> featureVector = new ReadOnlyObjectWrapper<MatOfFloat>();
	private final ReadOnlyObjectWrapper<MatOfFloat> featureLabel = new ReadOnlyObjectWrapper<MatOfFloat>();
	
	/**
	 * CTOR
	 * 
	 * @param className Name of the ErrorClass
	 * @param extractorName Used FeatureExtractor to obain this vector
	 * @param frameSize FrameSize of the sample
	 * @param id Identifier to make the FeatureVector unique
	 * @param featureVector FeatureVector
	 * @param featureLabel Label of the FeatureVector
	 */
	public FeatureVector(String className, String extractorName, int frameSize, String id, MatOfFloat featureVector, MatOfFloat featureLabel) {
		this.className.set(className);
		this.extractorName.set(extractorName);
		this.frameSize.set(frameSize);
		this.id.set(id);
		this.featureVector.set(featureVector);
		this.featureLabel.set(featureLabel);
	}

	// Property Getter
	public ReadOnlyStringProperty getClassNameProperty() { return this.className.getReadOnlyProperty(); }
	public ReadOnlyStringProperty getExtractorNameProperty() { return this.extractorName.getReadOnlyProperty(); }
	public ReadOnlyIntegerProperty getFrameSizeProperty() { return this.frameSize.getReadOnlyProperty(); }
	public ReadOnlyStringProperty getIdProperty() { return this.id.getReadOnlyProperty(); }
	public ReadOnlyObjectProperty<MatOfFloat> getFeatureVectorProperty() { return this.featureVector.getReadOnlyProperty(); }
	public ReadOnlyObjectProperty<MatOfFloat> getFeatureLabelProperty() { return this.featureLabel.getReadOnlyProperty(); }
//	public ReadOnlyObjectProperty<String> getFeatureLabelNameProperty() { return this.featureLabel.get().get(0, 0)[0]; }
}
