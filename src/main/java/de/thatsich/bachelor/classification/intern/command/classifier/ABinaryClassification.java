package de.thatsich.bachelor.classification.intern.command.classifier;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;

import com.google.inject.Inject;

import de.thatsich.core.Log;

public abstract class ABinaryClassification implements IBinaryClassification {
	
	// Properties
	private final ReadOnlyObjectWrapper<BinaryClassifierConfiguration> config = new ReadOnlyObjectWrapper<>();
	
	// Injects
	@Inject protected Log log; 
	
	protected ABinaryClassification(BinaryClassifierConfiguration config) {
		this.config.set(config);
	}
	
	// Getter
	@Override public String getName() { return this.getClass().getSimpleName(); }
	
	// Property Getter
	@Override public ReadOnlyObjectProperty<Path> getFilePathProperty() { return this.config.get().getFilePath(); }
	@Override public ReadOnlyStringProperty getClassificationNameProperty() { return this.config.get().getClassificationName(); }
	@Override public ReadOnlyStringProperty getExtractorNameProperty() { return this.config.get().getExtractorName(); }
	@Override public ReadOnlyIntegerProperty getFrameSizeProperty() { return this.config.get().getFrameSize(); }
	@Override public ReadOnlyStringProperty getErrorNameProperty() { return this.config.get().getErrorName(); }
	@Override public ReadOnlyStringProperty getIdProperty() { return this.config.get().getId(); }
}
