package com.thatsich.sample.javafx.model;

import java.io.File;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import com.thatsich.core.opencv.error.generator.IErrorGenerator;
import com.thatsich.core.opencv.metric.IDistance;


/**
 * Stores the State of the application.
 * 
 * @author Tran Minh Do
 *
 */
public class StateModel implements IStateModel {

	/*
	 * ==================================================
	 * Properties
	 * ==================================================
	 */
	private ListProperty<File> imageFiles = new SimpleListProperty<File>(this, "imageFiles");
	private ObjectProperty<File> originalFile = new SimpleObjectProperty<File>(this, "originalFile");
	
	private ListProperty<IErrorGenerator> errorGenerators = new SimpleListProperty<IErrorGenerator>(this, "errorGenerators");
	
	private IntegerProperty frameSize = new SimpleIntegerProperty(this, "frameSize");
	private ListProperty<IDistance> metrics = new SimpleListProperty<IDistance>(this, "metrics");
	private IntegerProperty threshold = new SimpleIntegerProperty(this, "threshold");
	
	
	/*
	 * ==================================================
	 * Getter Implementation
	 * ==================================================
	 */
	@Override
	public ObservableList<File> getImageFiles() {
		return this.imageFiles.get();
	}
	@Override
	public File getOriginalFile() {
		return this.originalFile.get();
	}
	@Override
	public ObservableList<IErrorGenerator> getErrorGenerators() {
		return this.errorGenerators.get();
	}
	@Override
	public int getFrameSize() {
		return this.frameSize.get();
	}
	@Override
	public ObservableList<IDistance> getMetrics() {
		return this.metrics.get();
	}
	@Override
	public int getThreshold() {
		return this.threshold.get();
	}
	
	
	/*
	 * ==================================================
	 * Setter Implementation
	 * ==================================================
	 */
	@Override
	public void setImageFiles(ObservableList<File> imageFiles) {
		this.imageFiles.set(imageFiles);
	}
	@Override
	public void setOriginalFile(File file) {
		this.originalFile.set(file);
	}
	@Override
	public void setErrorGenerators(ObservableList<IErrorGenerator> errorGenerators) {
		this.errorGenerators.set(errorGenerators);
	}
	@Override
	public void setFrameSize(int size) {
		this.frameSize.set(size);
	}
	@Override
	public void setMetrics(ObservableList<IDistance> metrics) {
		this.metrics.set(metrics);
	}
	@Override
	public void setThreshold(int threshold) {
		this.threshold.set(threshold);
	}
	
	
	/*
	 * ==================================================
	 * Property Implementation
	 * ==================================================
	 */
	@Override
	public ListProperty<File> getImageFilesProperty() {
		return this.imageFiles;
	}
	@Override
	public ObjectProperty<File> getOriginalFileProperty() {
		return this.originalFile;
	}
	@Override
	public ListProperty<IErrorGenerator> getErrorGeneratorsProperty() {
		return this.errorGenerators;
	}
	@Override
	public IntegerProperty getFrameSizeProperty() {
		return this.frameSize;
	}
	@Override
	public ListProperty<IDistance> getMetricsProperty() {
		return this.metrics;
	}
	@Override
	public IntegerProperty getThresholdProperty() {
		return this.threshold;
	}
}
