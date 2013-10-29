package de.thatsich.bachelor.classification.intern.command.commands;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import org.opencv.core.MatOfFloat;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classification.intern.command.classifier.BinaryClassifierConfiguration;
import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassification;
import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassifier;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.core.javafx.ACommand;

public class TrainBinaryClassifierCommand extends ACommand<IBinaryClassification> {

	// Properties
	private final ObjectProperty<Path> binaryClassifierFolderPath = new SimpleObjectProperty<Path>();
	private final ObjectProperty<IBinaryClassifier> binaryClassifier = new SimpleObjectProperty<IBinaryClassifier>();
	private final ObjectProperty<FeatureVectorSet> selectedFeatureVector = new SimpleObjectProperty<FeatureVectorSet>();
	private final ListProperty<FeatureVectorSet> featureVectorList = new SimpleListProperty<FeatureVectorSet>(FXCollections.<FeatureVectorSet> observableArrayList());

	@Inject
	public TrainBinaryClassifierCommand(
			@Assisted Path binaryClassifierFolderPath,
			@Assisted IBinaryClassifier classifier,
			@Assisted FeatureVectorSet selected,
			@Assisted List<FeatureVectorSet> all) {
		this.binaryClassifierFolderPath.set(binaryClassifierFolderPath);
		this.binaryClassifier.set(classifier);
		this.selectedFeatureVector.set(selected);
		this.featureVectorList.addAll(all);
	}

	@Override
	protected IBinaryClassification call() throws Exception {
		final IBinaryClassifier bc = binaryClassifier.get();
		final FeatureVectorSet selected = selectedFeatureVector.get();
		final List<FeatureVectorSet> list = featureVectorList.get();

		final String binaryClassifierName = bc.getName();
		final String featureExtractorName = selected.getExtractorNameProperty().get();
		final int frameSize = selected.getFrameSizeProperty().get();
		final String errorClassName = selected.getClassNameProperty().get();
		final String id = UUID.randomUUID().toString();

		final MatOfFloat positive = new MatOfFloat();
		final MatOfFloat negative = new MatOfFloat();
		log.info("Prepared all data for Training.");

		// run through all FeatureVectorSets matching same categories
		// (same FrameSize, same Extractor, same ErrorClass)
		// which is not the selected one and their data to train
		// extract all float lists and transform them into MatOfFloats
		// use .t() on them to transpose them
		// TODO Matching of same category
		for (FeatureVectorSet current : list) {
			for (FeatureVector vector : current.getFeatureVectorList()) {

				final float[] floatArray = new float[vector.getVectorProperty().size()];
				int index = 0;
				for (Float f : vector.getVectorProperty()) {
					floatArray[index] = f;
					index++;
				}

				if (vector.getIsPositiveProperty().get()) {
					positive.push_back(new MatOfFloat(floatArray).t());
				} else {
					negative.push_back(new MatOfFloat(floatArray).t());
				}
			}
		}
		log.info("Prepared Negative and Positive DataSets.");

		StringBuffer buffer = new StringBuffer();
		buffer.append(binaryClassifierName + "_");
		buffer.append(featureExtractorName + "_");
		buffer.append(frameSize + "_");
		buffer.append(errorClassName + "_");
		buffer.append(id + ".yaml");
		final Path filePath = binaryClassifierFolderPath.get().resolve(buffer.toString());
		log.info("Created FilePath");

		final BinaryClassifierConfiguration config = new BinaryClassifierConfiguration(filePath, binaryClassifierName, featureExtractorName, frameSize, errorClassName, id);
		log.info("Created BinaryClassifierConfiguration.");

		final IBinaryClassification classification = bc.train(positive, negative, config);
		log.info("Trained Binary Classifier.");

		classification.save(filePath.toString());
		log.info("Saved File to FileSystem.");

		return classification;
	}

}
