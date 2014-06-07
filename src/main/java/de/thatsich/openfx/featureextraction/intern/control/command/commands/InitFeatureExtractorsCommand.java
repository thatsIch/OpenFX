package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.intern.control.extractor.Gradient;
import de.thatsich.openfx.featureextraction.intern.control.extractor.GrayLevelCooccurenceHistogram;
import de.thatsich.openfx.featureextraction.intern.control.extractor.GrayLevelCooccurenceMatrix;
import de.thatsich.openfx.featureextraction.intern.control.extractor.HuMoments;
import de.thatsich.openfx.featureextraction.intern.control.extractor.LocalBinaryPatternHistogram;
import de.thatsich.openfx.featureextraction.intern.control.extractor.Mean;
import de.thatsich.openfx.featureextraction.intern.control.extractor.Variance;

import java.util.ArrayList;
import java.util.List;

public class InitFeatureExtractorsCommand extends ACommand<List<IFeatureExtractor>>
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
