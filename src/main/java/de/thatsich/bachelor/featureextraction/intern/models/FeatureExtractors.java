package de.thatsich.bachelor.featureextraction.intern.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractors;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.IFeatureExtractor;

public class FeatureExtractors implements IFeatureExtractors {

	// Properties
	private final ListProperty<IFeatureExtractor> featureExtractors = new SimpleListProperty<IFeatureExtractor>(FXCollections.<IFeatureExtractor>observableArrayList());
	private final ObjectProperty<IFeatureExtractor> selectedFeatureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	
	// Property Getter
	public ListProperty<IFeatureExtractor> getFeatureExtractorsProperty() { return this.featureExtractors; }
	public ObjectProperty<IFeatureExtractor> getSelectedFeatureExtractorProperty() { return this.selectedFeatureExtractor; }
	
	// Getter
	public IFeatureExtractor getSelectedFeatureExtractor() { return this.selectedFeatureExtractor.get(); }
	
	// Setter
	public void setSelectedFeatureExtractor(IFeatureExtractor extractor) { this.selectedFeatureExtractor.set(extractor); }
}
