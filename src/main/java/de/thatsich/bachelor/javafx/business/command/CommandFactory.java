package de.thatsich.bachelor.javafx.business.command;

import java.nio.file.Path;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import org.opencv.core.Mat;

import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;
import de.thatsich.core.opencv.IErrorGenerator;
import de.thatsich.core.opencv.IFeatureExtractor;

public interface CommandFactory {
	public InitImageEntryListCommand createInitImageEntryListCommand(EventHandler<WorkerStateEvent> handler, Path imageInputFolderPath);
	public SetLastImageEntryIndexCommand createSetLastImageEntryIndexCommand(EventHandler<WorkerStateEvent> handler, int lastImageEntryIndex);
	public GetLastImageEntryIndexCommand createGetLastImageEntryIndexCommand(EventHandler<WorkerStateEvent> handler);
	public CopyFileCommand createCopyFileCommand(EventHandler<WorkerStateEvent> handler, @Assisted("origin") Path originPath, @Assisted("copy") Path copyPath);
	public DeleteImageEntryCommand createDeleteImageEntryCommand(EventHandler<WorkerStateEvent> handler, ImageEntry entry);
	
	public InitErrorEntryListCommand createInitErrorEntryListCommand(EventHandler<WorkerStateEvent> handler, Path errorInputFolderPath);
	public InitErrorGeneratorListCommand createInitErrorGeneratorListCommand(EventHandler<WorkerStateEvent> handler); 
	public GetLastErrorGeneratorIndexCommand createGetLastErrorGeneratorIndexCommand(EventHandler<WorkerStateEvent> handler);
	public GetLastErrorEntryIndexCommand createGetLastErrorEntryIndexCommand(EventHandler<WorkerStateEvent> handler);
	public SetLastErrorEntryIndexCommand createSetLastErrorEntryIndexCommand(EventHandler<WorkerStateEvent> handler, int lastErrorEntryIndex);
	public ApplyErrorCommand createApplyErrorCommand(EventHandler<WorkerStateEvent> handler, Mat imageMat, Path imagePath, IErrorGenerator generator);
	public DeleteErrorEntryCommand createDeleteErrorEntryCommand(EventHandler<WorkerStateEvent> handler, ErrorEntry entry);
	public CreateErrorImageCommand createCreateErrorImageCommand(EventHandler<WorkerStateEvent> handler, ErrorEntry entry);
	
	public InitFeatureExtractorListCommand createInitFeatureExtractorListCommand(EventHandler<WorkerStateEvent> handler);
	public InitFeatureVectorListCommand createInitFeatureVectorListCommand(EventHandler<WorkerStateEvent> handler, Path folderPath);
	public ExtractFeatureVectorFromErrorEntryCommand createExtractFeatureVectorCommand(EventHandler<WorkerStateEvent> handler, ErrorEntry errorEntry, IFeatureExtractor extractor, int frameSize);
	public DeleteFeatureVectorCommand createRemoveFeatureVectorCommand(EventHandler<WorkerStateEvent> handler, FeatureVector featureVector);
}