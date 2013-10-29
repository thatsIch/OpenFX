package de.thatsich.bachelor.classification.intern.command.classifier;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;

import org.opencv.core.Mat;

public interface IBinaryClassification {
	public String getName();
	public double predict(Mat image);
	public void load(String fileName);
	public void save(String fileName);
	public ReadOnlyObjectProperty<Path> getFilePathProperty();
	public ReadOnlyStringProperty getClassificationNameProperty();
	public ReadOnlyStringProperty getExtractorNameProperty();
	public ReadOnlyIntegerProperty getFrameSizeProperty();
	public ReadOnlyStringProperty getErrorNameProperty();
	public ReadOnlyStringProperty getIdProperty();
}
