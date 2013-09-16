package com.thatsich.sample.javafx.model;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

import com.thatsich.core.opencv.error.generator.IErrorGenerator;
import com.thatsich.core.opencv.metric.IMetric;

public interface IStateModel {

	// Getter
	public Path getInputPath();
	public Path getOutputPath();
	
	public ObservableList<Path> getImagePaths();
	public ObservableList<IErrorGenerator> getErrorGenerators();
	public ObservableList<IMetric> getMetrics();
	
	public Path getImagePath();
	public IErrorGenerator getErrorGenerator();
	public IMetric getMetric();
	
	public int getFrameSize();
	public int getThreshold();
	
	
	// Setter
	public void setImagePaths(ObservableList<Path> imagePaths);
	public void setErrorGenerators(ObservableList<IErrorGenerator> errorGenerators);
	public void setMetrics(ObservableList<IMetric> metrics);
	
	public void setImagePath(Path path);
	public void setErrorGenerator(IErrorGenerator generator);
	public void setMetric(IMetric metric);
	
	public void setFrameSize(int size);
	public void setThreshold(int threshold);
	
	
	// Property
	public ListProperty<Path> getImagePathsProperty();
	public ListProperty<IErrorGenerator> getErrorGeneratorsProperty();
	public ListProperty<IMetric> getMetricsProperty();
	
	public ObjectProperty<Path> getImagePathProperty();
	public ObjectProperty<IErrorGenerator> getErrorGeneratorProperty();
	public ObjectProperty<IMetric> getMetricProperty();
	
	public IntegerProperty getFrameSizeProperty();
	public IntegerProperty getThresholdProperty();
}