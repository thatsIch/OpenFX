package de.thatsich.bachelor.classificationtesting.restricted.controller.commands;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtesting.api.entities.BinaryPrediction;
import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.featureextraction.api.entities.IFeatureExtractor;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.core.javafx.Command;

public class TestBinaryClassificationCommand extends Command<BinaryPrediction> {

	// Properties
	private final ReadOnlyObjectWrapper<ImageEntry> imageEntry = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyIntegerWrapper frameSize = new ReadOnlyIntegerWrapper();
	private final ReadOnlyObjectWrapper<IErrorGenerator> errorGenerator = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<IFeatureExtractor> featureExtractor = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<IBinaryClassification> binaryClassification = new ReadOnlyObjectWrapper<>();
	
	@Inject
	private TestBinaryClassificationCommand(
			@Assisted ImageEntry imageEntry,
			@Assisted int frameSize,
			@Assisted IErrorGenerator errorGenerator,
			@Assisted IFeatureExtractor featureExtractor,
			@Assisted IBinaryClassification binaryClassification) {
		this.imageEntry.set(imageEntry);
		this.frameSize.set(frameSize);
		this.errorGenerator.set(errorGenerator);
		this.featureExtractor.set(featureExtractor);
		this.binaryClassification.set(binaryClassification);
	}
	
	@Override
	protected Task<BinaryPrediction> createTask() {
		return new Task<BinaryPrediction>() {

			@Override
			protected BinaryPrediction call() throws Exception {
				return null;
			}
		};
	}

}
