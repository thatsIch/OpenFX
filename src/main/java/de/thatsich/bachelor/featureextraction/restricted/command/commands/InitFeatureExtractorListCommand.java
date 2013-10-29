package de.thatsich.bachelor.featureextraction.restricted.command.commands;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.restricted.command.FeatureExtractorProvider;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.Gradient;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.GrayLevelCooccurenceHistogram;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.HuMoments;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.LocalBinaryPatternHistogram;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.Mean;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.Variance;
import de.thatsich.core.javafx.ACommand;

public class InitFeatureExtractorListCommand extends ACommand<List<IFeatureExtractor>> {

	@Inject FeatureExtractorProvider provider;

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

}
