package de.thatsich.bachelor.featureextraction.intern.controller.handler;

import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.featureextraction.api.core.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull 
 * for removing the featurevector
 * 
 * @author Minh
 */
public class RemoveFeatureVectorSetSucceededHandler extends ACommandHandler<FeatureVectorSet> {
	
	@Inject private IFeatureVectorSets featureVectors;
	
	@Override
	public void handle(FeatureVectorSet fv) {
		final List<FeatureVectorSet> list = this.featureVectors.getFeatureVectorSetListProperty();
		list.remove(fv);
		this.log.info("Removed FeatureVector from Database.");
		
		if (list.size() > 0) {
			final FeatureVectorSet first = list.get(0);
			this.featureVectors.setSelectedFeatureVectorSet(first);
			this.log.info("Reset Selection to first FeatureVectorSet.");
		}
		else {
			this.featureVectors.setSelectedFeatureVectorSet(null);
			this.log.info("Reset Selection to null.");
		}
	}
}
