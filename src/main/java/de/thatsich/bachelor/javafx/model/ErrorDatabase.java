package de.thatsich.bachelor.javafx.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import com.google.inject.Inject;

import de.thatsich.bachelor.opencv.error.LineError;
import de.thatsich.core.Log;
import de.thatsich.core.opencv.error.IErrorGenerator;

public class ErrorDatabase {
	
	// Injects
	final private Log log;
	
	// Properties
	final private ObjectProperty<ObservableList<IErrorGenerator>> errorGenerators = new ChoiceBox<IErrorGenerator>().itemsProperty();
	final private ObjectProperty<IErrorGenerator> errorGenerator = new SimpleObjectProperty<IErrorGenerator>();
	
	@Inject
	private ErrorDatabase(Log log) {
		this.log = log;
		
		this.initErrorGenerators();
	}
	
	/**
	 * Initialize all wanted Error Generators implementing Interface IErrorGenerator
	 */
	private void initErrorGenerators() {
		this.errorGenerators.get().addAll(
			new LineError()
		);
		this.log.info("Initialized Error Generators.");
	}
	// ==================================================
	// Getter Implementation
	// ==================================================
//	public ObservableList<IErrorGenerator> getErrorGenerators() { return this.errorGenerators.get(); }
//	public IErrorGenerator getErrorGenerator() { return this.errorGenerator.get(); }
	
	// ==================================================
	// Setter Implementation
	// ==================================================
//	public void setErrorGenerators(ObservableList<IErrorGenerator> errorGenerators) { this.errorGenerators.set(errorGenerators); }
//	public void setErrorGenerator(IErrorGenerator generator) { this.errorGenerator.set(generator); }

	// ==================================================
	// Property Implementation
	// ==================================================
	public ObjectProperty<ObservableList<IErrorGenerator>> getErrorGeneratorsProperty() { return this.errorGenerators; }
	public ObjectProperty<IErrorGenerator> getErrorGeneratorProperty() { return this.errorGenerator; }
	
}
