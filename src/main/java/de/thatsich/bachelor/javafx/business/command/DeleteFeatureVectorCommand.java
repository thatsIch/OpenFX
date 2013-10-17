package de.thatsich.bachelor.javafx.business.command;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.core.javafx.Command;

public class DeleteFeatureVectorCommand extends Command<FeatureVector> {

	// Properties
	private final ObjectProperty<FeatureVector> featureVector = new SimpleObjectProperty<FeatureVector>();
	
	// Injections
	
	@Inject
	public DeleteFeatureVectorCommand(@Assisted EventHandler<WorkerStateEvent> handler, @Assisted FeatureVector featureVector) {
		super(handler);
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
