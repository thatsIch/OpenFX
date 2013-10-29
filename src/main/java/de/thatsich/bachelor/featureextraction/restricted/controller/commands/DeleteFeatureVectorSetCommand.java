package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import java.io.IOException;
import java.nio.file.Files;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.core.javafx.ACommand;

public class DeleteFeatureVectorSetCommand extends ACommand<FeatureVectorSet> {

	// Properties
	private final FeatureVectorSet featureVectorSet;

	@Inject
	public DeleteFeatureVectorSetCommand(@Assisted FeatureVectorSet featureVectorSet) {
		this.featureVectorSet = featureVectorSet;
	}

	@Override
	protected FeatureVectorSet call() throws Exception {
		try {
			this.log.info(this.featureVectorSet.getPathProperty().get().toString());
			Files.delete(this.featureVectorSet.getPathProperty().get());
			
			this.log.info("Deleted FeatureVectorSet from FileSystem.");
		}
		catch (IOException e) {
			e.printStackTrace();
			this.log.info("Exception from deleting FeatureVectorSet from FileSystem.");
		}
		
		return this.featureVectorSet;
	}

}
