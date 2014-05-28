package de.thatsich.bachelor.featureextraction.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.Gradient;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.GrayLevelCooccurenceHistogram;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.GrayLevelCooccurenceMatrix;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.HuMoments;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.LocalBinaryPatternHistogram;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.Mean;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.Variance;
import de.thatsich.core.javafx.ACommand;

import java.util.ArrayList;
import java.util.List;

public class InitFeatureExtractorListCommand extends ACommand<List<IFeatureExtractor>>
{

	@Inject
	private Injector injector;

	@Override
	protected List<IFeatureExtractor> call() throws Exception
	{
		final List<IFeatureExtractor> featureExtractorList = new ArrayList<>();

		featureExtractorList.add(this.get(Gradient.class));
		featureExtractorList.add(this.get(GrayLevelCooccurenceHistogram.class));
		featureExtractorList.add(this.get(HuMoments.class));
		featureExtractorList.add(this.get(LocalBinaryPatternHistogram.class));
		featureExtractorList.add(this.get(Mean.class));
		featureExtractorList.add(this.get(Variance.class));
		featureExtractorList.add(this.get(GrayLevelCooccurenceMatrix.class));

		return featureExtractorList;
	}

	private <T extends IFeatureExtractor> T get(Class<T> type)
	{
		return injector.getInstance(type);
	}
}
