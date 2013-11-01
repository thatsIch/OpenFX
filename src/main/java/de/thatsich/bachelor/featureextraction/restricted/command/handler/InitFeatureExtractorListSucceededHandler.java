package de.thatsich.bachelor.featureextraction.restricted.command.handler;

import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractors;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.IFeatureExtractor;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for initializing the feature extractor list
 * 
 * @author Minh
 */
public class InitFeatureExtractorListSucceededHandler extends ACommandHandler<List<IFeatureExtractor>> {
	
	@Inject private IFeatureExtractors featureExtractors;
	
	@Override
	public void handle(List<IFeatureExtractor> list) {
		this.featureExtractors.getFeatureExtractorsProperty().addAll(list);
		this.log.info("Added FeatureExtractor to Database.");
	}
}
