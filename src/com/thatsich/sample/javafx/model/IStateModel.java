package com.thatsich.sample.javafx.model;

import java.io.File;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

import com.thatsich.core.opencv.error.generator.IErrorGenerator;
import com.thatsich.core.opencv.metric.IDistance;

public interface IStateModel {

	// Getter
	public ObservableList<File> getImageFiles();
	public File getOriginalFile();
	public ObservableList<IErrorGenerator> getErrorGenerators();
	public int getFrameSize();
	public ObservableList<IDistance> getMetrics();
	public int getThreshold();
	
	// Setter
	public void setImageFiles(ObservableList<File> imageFiles);
	public void setOriginalFile(File file);
	public void setErrorGenerators(ObservableList<IErrorGenerator> errorGenerators);
	public void setFrameSize(int size);
	public void setMetrics(ObservableList<IDistance> metrics);
	public void setThreshold(int threshold);
	
	// Property
	public ListProperty<File> getImageFilesProperty();
	public ObjectProperty<File> getOriginalFileProperty();
	public ListProperty<IErrorGenerator> getErrorGeneratorsProperty();
	public IntegerProperty getFrameSizeProperty();
	public ListProperty<IDistance> getMetricsProperty();
	public IntegerProperty getThresholdProperty();
}