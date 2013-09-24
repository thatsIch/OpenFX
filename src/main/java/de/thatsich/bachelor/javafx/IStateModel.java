package de.thatsich.bachelor.javafx;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import de.thatsich.core.opencv.error.IErrorGenerator;
import de.thatsich.core.opencv.extractor.IFeatureExtractor;
import de.thatsich.core.opencv.metric.IMetric;

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
	public void setFeatureExtractors(ObservableList<IFeatureExtractor> featureExtractors);
	
	public void setImagePath(Path path);
	public void setErrorGenerator(IErrorGenerator generator);
	public void setMetric(IMetric metric);
	public void setFeatureExtractor(IFeatureExtractor featureExtractor);
	
	public void setFrameSize(int size);
	public void setThreshold(int threshold);
	
	
	// Property
	public ObjectProperty<ObservableList<Path>> getImagePathsProperty();
	public ObjectProperty<ObservableList<IErrorGenerator>> getErrorGeneratorsProperty();
	public ObjectProperty<ObservableList<IMetric>> getMetricsProperty();
	public ObjectProperty<ObservableList<IFeatureExtractor>> getFeatureExtractorsProperty();
	
	public ObjectProperty<Path> getImagePathProperty();
	public ObjectProperty<IErrorGenerator> getErrorGeneratorProperty();
	public ObjectProperty<IMetric> getMetricProperty();
	public ObjectProperty<IFeatureExtractor> getFeatureExtractorProperty();
	
	public IntegerProperty getFrameSizeProperty();
	public IntegerProperty getThresholdProperty();
	
}