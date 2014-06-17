package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.Injector;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.intern.control.extractor.Gradient;
import de.thatsich.openfx.featureextraction.intern.control.extractor.GrayLevelCooccurenceHistogram;
import de.thatsich.openfx.featureextraction.intern.control.extractor.HuMoments;
import de.thatsich.openfx.featureextraction.intern.control.extractor.LocalBinaryPatternHistogram;
import de.thatsich.openfx.featureextraction.intern.control.extractor.Mean;
import de.thatsich.openfx.featureextraction.intern.control.extractor.Variance;

import java.util.Arrays;
import java.util.List;

public class InitFeatureExtractorsCommand extends ACommand<List<IFeatureExtractor>>
{
	@Inject
	private Injector injector;

	@Override
	protected List<IFeatureExtractor> call() throws Exception
	{
		final List<IFeatureExtractor> featureExtractorList = Arrays.asList(new Gradient(), new GrayLevelCooccurenceHistogram(), new HuMoments(), new LocalBinaryPatternHistogram(), new Mean(), new Variance() //,
			//new GrayLevelCooccurenceMatrix()
		);

		return featureExtractorList;
	}
}
