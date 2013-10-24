package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

import com.google.inject.Inject;

import de.thatsich.bachelor.featureextraction.api.entities.Gradient;
import de.thatsich.bachelor.featureextraction.api.entities.GrayLevelCooccurenceHistogram;
import de.thatsich.bachelor.featureextraction.api.entities.HuMoments;
import de.thatsich.bachelor.featureextraction.api.entities.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.api.entities.LocalBinaryPatternHistogram;
import de.thatsich.bachelor.featureextraction.api.entities.Mean;
import de.thatsich.bachelor.featureextraction.api.entities.Variance;
import de.thatsich.bachelor.featureextraction.restricted.application.guice.FeatureExtractorProvider;
import de.thatsich.core.javafx.Command;

public class InitFeatureExtractorListCommand extends Command<List<IFeatureExtractor>> {

	@Inject FeatureExtractorProvider provider;
	
	@Override
	protected Task<List<IFeatureExtractor>> createTask() {
		return new Task<List<IFeatureExtractor>>() {
			
			@Override
			protected List<IFeatureExtractor> call() throws Exception {
				final List<IFeatureExtractor> featureExtractorList = new ArrayList<IFeatureExtractor>();
				
				featureExtractorList.add(provider.get(Gradient.class));
				featureExtractorList.add(provider.get(GrayLevelCooccurenceHistogram.class));
				featureExtractorList.add(provider.get(HuMoments.class));
				featureExtractorList.add(provider.get(LocalBinaryPatternHistogram.class));
				featureExtractorList.add(provider.get(Mean.class));
				featureExtractorList.add(provider.get(Variance.class));
				
				return featureExtractorList;
			}
		};
	}

}