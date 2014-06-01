package de.thatsich.bachelor.featureextraction.api.control;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;

import java.nio.file.Path;
import java.util.List;

/**
 * Represents on set of FeatureVectors extracted in one swoop sharing the same information
 * as frame size or which extractor was used. Helps to distinguish between sets
 * by comparing the source of the FeatureVectors and not the actual values.
 *
 * @author Minh
 */
public class FeatureVectorSet
{

	// Properties
	private final ReadOnlyObjectWrapper<Path> path = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyStringWrapper className = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper extractorName = new ReadOnlyStringWrapper();
	private final ReadOnlyIntegerWrapper frameSize = new ReadOnlyIntegerWrapper();
	private final ReadOnlyStringWrapper id = new ReadOnlyStringWrapper();
	private final ReadOnlyListWrapper<FeatureVector> featureVectorList = new ReadOnlyListWrapper<>(FXCollections.<FeatureVector>observableArrayList());

	/**
	 * CTOR
	 *
	 * @param className         Name of the ErrorClass
	 * @param extractorName     Used FeatureExtractor to obain this vector
	 * @param frameSize         FrameSize of the sample
	 * @param id                Identifier to make the FeatureVector unique
	 * @param featureVectorList List of FeatureVectors
	 */
	public FeatureVectorSet(Path path, String className, String extractorName, int frameSize, String id, List<FeatureVector> featureVectorList)
	{
		this.path.set(path);
		this.className.set(className);
		this.extractorName.set(extractorName);
		this.frameSize.set(frameSize);
		this.id.set(id);
		this.featureVectorList.setAll(featureVectorList);
	}

	// Property Getter
	public ReadOnlyObjectProperty<Path> getPathProperty()
	{ return this.path.getReadOnlyProperty(); }

	public ReadOnlyStringProperty getClassNameProperty() { return this.className.getReadOnlyProperty(); }

	public ReadOnlyStringProperty getExtractorNameProperty() { return this.extractorName.getReadOnlyProperty(); }

	public ReadOnlyIntegerProperty getFrameSizeProperty() { return this.frameSize.getReadOnlyProperty(); }

	public ReadOnlyStringProperty getIdProperty() { return this.id.getReadOnlyProperty(); }

	public ReadOnlyListProperty<FeatureVector> getFeatureVectorList() { return this.featureVectorList.getReadOnlyProperty(); }
}
