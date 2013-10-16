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
	public InitImageEntryListCommand createInitImageEntryCommand(EventHandler<WorkerStateEvent> handler, Path imageInputFolderPath);
	public CopyFileCommand createCopyFileCommand(EventHandler<WorkerStateEvent> handler, @Assisted("origin") Path originPath, @Assisted("copy") Path copyPath);
	public DeleteImageEntryCommand createDeleteImageEntryCommand(EventHandler<WorkerStateEvent> handler, ImageEntry entry);
	
	public InitErrorEntryListCommand createInitErrorEntryListCommand(EventHandler<WorkerStateEvent> handler, Path errorInputFolderPath);
	public InitErrorGeneratorListCommand createInitErrorGeneratorListCommand(EventHandler<WorkerStateEvent> handler); 
	public ApplyErrorCommand createApplyErrorCommand(EventHandler<WorkerStateEvent> handler, Mat imageMat, Path imagePath, IErrorGenerator generator);
	public DeleteErrorEntryCommand createDeleteErrorEntryCommand(EventHandler<WorkerStateEvent> handler, ErrorEntry entry);
	public CreateErrorImageCommand createCreateErrorImageCommand(EventHandler<WorkerStateEvent> handler, ErrorEntry entry);
	
	public ExtractFeatureVectorFromErrorEntryCommand createExtractFeatureVectorCommand(EventHandler<WorkerStateEvent> handler, ErrorEntry errorEntry, IFeatureExtractor extractor, int frameSize);
	public RemoveFeatureVectorCommand createRemoveFeatureVectorCommand(EventHandler<WorkerStateEvent> handler, FeatureVector featureVector);
}
