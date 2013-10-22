package de.thatsich.bachelor.classificationtraining.api.entities;

import java.nio.file.Path;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class TrainedBinaryClassifier {
	
	@Inject
	public TrainedBinaryClassifier(
			@Assisted Path trainedBinaryClassifierPath,
			@Assisted IBinaryClassifier binaryClassifier,
			@Assisted String featureExtractorName,
			@Assisted int frameSize,
			@Assisted String errorClassName,
			@Assisted String id) {

	}
}
