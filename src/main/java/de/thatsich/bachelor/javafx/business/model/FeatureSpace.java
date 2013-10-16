package de.thatsich.bachelor.javafx.business.model;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.opencv.core.MatOfFloat;

import com.google.inject.Inject;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;

import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.bachelor.opencv.extractor.Gradient;
import de.thatsich.bachelor.opencv.extractor.GrayLevelCooccurenceHistogram;
import de.thatsich.bachelor.opencv.extractor.HuMoments;
import de.thatsich.bachelor.opencv.extractor.LocalBinaryPatternHistogram;
import de.thatsich.bachelor.opencv.extractor.Mean;
import de.thatsich.bachelor.opencv.extractor.Variance;
import de.thatsich.bachelor.service.CSVService;
import de.thatsich.core.Log;
import de.thatsich.core.opencv.IFeatureExtractor;

/**
 * FeatureSpace Class
 * holds all feature vectors and feature extractors
 * 
 * @author Minh
 *
 */
public class FeatureSpace {

	// Fields
	final private Path featureVectorFolderPath;
	
	
	// Properties
	private final IntegerProperty frameSize = new SimpleIntegerProperty();
	private final ObjectProperty<ObservableList<IFeatureExtractor>> featureExtractors = new SimpleObjectProperty<ObservableList<IFeatureExtractor>>();
	private final ObjectProperty<IFeatureExtractor> selectedFeatureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	private final ObjectProperty<ObservableList<FeatureVector>> featureVectorList = new SimpleObjectProperty<ObservableList<FeatureVector>>();
	private final ObjectProperty<FeatureVector> selectedFeatureVector = new SimpleObjectProperty<FeatureVector>();
	
	// Injections
	private final Log log;
	
	/**
	 * Injected Constructor
	 * 
	 * @param log Logger
	 * @throws IOException When directory can not be created
	 * @throws WrongNumberArgsException 
	 */
	@Inject
	public FeatureSpace(Log log) throws IOException, WrongNumberArgsException {
		this.log = log;
		
		this.featureVectorFolderPath = Paths.get("featurevectors");
		if (Files.notExists(this.featureVectorFolderPath) || !Files.isDirectory(this.featureVectorFolderPath)) Files.createDirectory(this.featureVectorFolderPath);
		
		
		ObservableList<FeatureVector> featureVectors = FXCollections.observableArrayList();
		ObservableList<IFeatureExtractor> featureExtractors = FXCollections.observableArrayList();
		
		this.featureVectorList.set(featureVectors);
		this.featureExtractors.set(featureExtractors);
		
		this.initFeatureVectors();
		this.initFeatureExtractors();
	}
	
	
//	public void addFeatureVector(FeatureVector featureVector) {
////		this.featureVectors.push_back(featureVector.getFeatureVector().t());
////		this.classificationLabels.push_back(featureVector.getClassificationLabel().t());
//	}

	
	/**
	 * Initialize Feature Vectors
	 * @throws WrongNumberArgsException 
	 */
	private void initFeatureVectors() throws WrongNumberArgsException {
		final String GLOB_PATTERN = "*.{csv}";
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.featureVectorFolderPath, GLOB_PATTERN)) {
			for (Path child : stream) {
				final String fileName = child.getFileName().toString();
				final String[] fileNameSplit = fileName.split("_");
				
				if (fileNameSplit.length != 4) throw new WrongNumberArgsException("Expected 4 encoded information but found " + fileNameSplit.length);
				List<float[]> floatValues = CSVService.read(child);
				
				final String className = fileNameSplit[0];
				final String extractorName = fileNameSplit[1];
				final int frameSize = Integer.parseInt(fileNameSplit[2]);
				final String id = fileNameSplit[3];
				
				for (float[] floatArray : floatValues) {
					final int length = floatArray.length;
					
					final MatOfFloat featureVector = new MatOfFloat(Arrays.copyOfRange(floatArray, 0, length - 2));
					final MatOfFloat featureLabel = new MatOfFloat(floatArray[length - 1]);
					this.featureVectorList.get().add(new FeatureVector(className, extractorName, frameSize, id, featureVector, featureLabel));
				}
				this.log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
			}
		} catch (IOException | DirectoryIteratorException e) {
			e.printStackTrace();
		}
		this.log.info("All FeatureVectors added.");
	}
	
	/**
	 * Initialize Feature Extracotrs
	 * - adding all known to the list
	 * - select the first in list if available
	 */
	private void initFeatureExtractors() {
		this.featureExtractors.get().addAll(
			new Gradient(),
			new GrayLevelCooccurenceHistogram(),
			new HuMoments(),
			new LocalBinaryPatternHistogram(),
			new Mean(),
			new Variance()
		);
		this.log.info("Initialized Feature Extractors.");
		
		if (this.featureExtractors.get().size() > 0) {
			this.selectedFeatureExtractor.set(this.featureExtractors.get().get(0));
			this.log.info("Initialized with first Feature Extractor.");
		}
	}

	// ==================================================
	// Property Implementation
	// ==================================================
	// Frame Size
	public IntegerProperty getFrameSizeProperty() { return this.frameSize; }
	
	// Feature Extractors
	public ObjectProperty<ObservableList<IFeatureExtractor>> getFeatureExtractorsProperty() { return this.featureExtractors; }
	public ObjectProperty<IFeatureExtractor> getSelectedFeatureExtractorProperty() { return this.selectedFeatureExtractor; }
	public ObjectProperty<ObservableList<FeatureVector>> getFeatureVectorListProperty() { return this.featureVectorList; }
	public ObjectProperty<FeatureVector> getSelectedFeatureVectorProperty() { return this.selectedFeatureVector; }
}
