package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import de.thatsich.bachelor.featureextraction.api.entities.Gradient;
import de.thatsich.bachelor.featureextraction.api.entities.GrayLevelCooccurenceHistogram;
import de.thatsich.bachelor.featureextraction.api.entities.HuMoments;
import de.thatsich.bachelor.featureextraction.api.entities.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.api.entities.LocalBinaryPatternHistogram;
import de.thatsich.bachelor.featureextraction.api.entities.Mean;
import de.thatsich.bachelor.featureextraction.api.entities.Variance;
import de.thatsich.core.javafx.Command;

public class InitFeatureExtractorListCommand extends Command<List<IFeatureExtractor>> {

	@Override
	protected Task<List<IFeatureExtractor>> createTask() {
		return new Task<List<IFeatureExtractor>>() {
			
			@Override
			protected List<IFeatureExtractor> call() throws Exception {
				final List<IFeatureExtractor> featureExtractorList = new ArrayList<IFeatureExtractor>();
				
				featureExtractorList.add(new Gradient());
				featureExtractorList.add(new GrayLevelCooccurenceHistogram());
				featureExtractorList.add(new HuMoments());
				featureExtractorList.add(new LocalBinaryPatternHistogram());
				featureExtractorList.add(new Mean());
				featureExtractorList.add(new Variance());
				
				return featureExtractorList;
			}
		};
	}

}
