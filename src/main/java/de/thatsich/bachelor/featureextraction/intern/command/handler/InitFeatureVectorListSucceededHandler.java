package de.thatsich.bachelor.featureextraction.intern.command.handler;

import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.featureextraction.api.core.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for initializing the feature vector list
 * 
 * @author Minh
 */
public class InitFeatureVectorListSucceededHandler extends ACommandHandler<List<FeatureVectorSet>> {
	
	@Inject IFeatureVectorSets featureVectors;
	
	@Override
	public void handle(List<FeatureVectorSet> featureVectorList) {
		featureVectors.getFeatureVectorSetListProperty().addAll(featureVectorList);
		this.log.info("Added FeatureExtractor to Database.");
	}
}
