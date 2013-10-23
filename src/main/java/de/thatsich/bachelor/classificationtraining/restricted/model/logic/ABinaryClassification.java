package de.thatsich.bachelor.classificationtraining.restricted.model.logic;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;
import de.thatsich.core.Log;

public abstract class ABinaryClassification implements IBinaryClassification {
	
	// Properties
	private final ReadOnlyObjectWrapper<BinaryClassifierConfiguration> config = new ReadOnlyObjectWrapper<>();
	
	// Injects
	@Inject protected Log log; 
	
	protected ABinaryClassification(BinaryClassifierConfiguration config) {
		this.config.set(config);
	}
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	// Property Getter
	public ReadOnlyStringProperty getClassificationName() { return this.config.get().getClassificationName(); }
	public ReadOnlyStringProperty getExtractorName() { return this.config.get().getExtractorName(); }
	public ReadOnlyIntegerProperty getFrameSize() { return this.config.get().getFrameSize(); }
	public ReadOnlyStringProperty getErrorName() { return this.config.get().getErrorName(); }
	public ReadOnlyStringProperty getId() { return this.config.get().getId(); }
}
