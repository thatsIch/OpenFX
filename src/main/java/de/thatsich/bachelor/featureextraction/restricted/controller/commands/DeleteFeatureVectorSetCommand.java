package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import java.io.IOException;
import java.nio.file.Files;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.core.javafx.Command;

public class DeleteFeatureVectorSetCommand extends Command<FeatureVectorSet> {

	// Properties
	private final ObjectProperty<FeatureVectorSet> featureVectorSet = new SimpleObjectProperty<FeatureVectorSet>();

	@Inject
	public DeleteFeatureVectorSetCommand(@Assisted FeatureVectorSet featureVectorSet) {
		this.featureVectorSet.set(featureVectorSet);
	}
	
	@Override
	protected Task<FeatureVectorSet> createTask() {
		return new Task<FeatureVectorSet>() {
			@Override protected FeatureVectorSet call() throws Exception {
				try {
					log.info(featureVectorSet.get().getPathProperty().get().toString());
					Files.delete(featureVectorSet.get().getPathProperty().get());
					
					log.info("Deleted FeatureVectorSet from FileSystem.");
				}
				catch (IOException e) {
					e.printStackTrace();
					log.info("Exception from deleting FeatureVectorSet from FileSystem.");
				}
				return featureVectorSet.get();
			}
		};
	}

}
