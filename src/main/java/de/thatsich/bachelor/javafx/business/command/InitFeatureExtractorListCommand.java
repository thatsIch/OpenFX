package de.thatsich.bachelor.javafx.business.command;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.opencv.extractor.Gradient;
import de.thatsich.bachelor.opencv.extractor.GrayLevelCooccurenceHistogram;
import de.thatsich.bachelor.opencv.extractor.HuMoments;
import de.thatsich.bachelor.opencv.extractor.LocalBinaryPatternHistogram;
import de.thatsich.bachelor.opencv.extractor.Mean;
import de.thatsich.bachelor.opencv.extractor.Variance;
import de.thatsich.core.javafx.Command;
import de.thatsich.core.opencv.IFeatureExtractor;

public class InitFeatureExtractorListCommand extends Command<List<IFeatureExtractor>> {

	@Inject
	protected InitFeatureExtractorListCommand(@Assisted EventHandler<WorkerStateEvent> handler) {
		super(handler);
	}

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
