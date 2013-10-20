package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.core.javafx.Command;

public class DeleteFeatureVectorCommand extends Command<FeatureVector> {

	// Properties
	private final ObjectProperty<FeatureVector> featureVector = new SimpleObjectProperty<FeatureVector>();
	
	// Injections
	
	@Inject
	public DeleteFeatureVectorCommand(@Assisted FeatureVector featureVector) {
		this.featureVector.set(featureVector);
	}
	
	@Override
	protected Task<FeatureVector> createTask() {
		return new Task<FeatureVector>() {

			@Override
			protected FeatureVector call() throws Exception {
				return featureVector.get();
			}
		};
	}

}
