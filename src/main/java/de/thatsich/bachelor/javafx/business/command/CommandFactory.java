package de.thatsich.bachelor.javafx.business.command;

import java.nio.file.Path;
import java.util.List;

import org.opencv.core.Mat;

import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.bachelor.javafx.business.model.entity.FeatureVector;
import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;
import de.thatsich.core.opencv.IBinaryClassifier;
import de.thatsich.core.opencv.IErrorGenerator;
import de.thatsich.core.opencv.IFeatureExtractor;

public interface CommandFactory {
	public InitImageEntryListCommand createInitImageEntryListCommand(Path imageInputFolderPath);
	public SetLastImageEntryIndexCommand createSetLastImageEntryIndexCommand(int lastImageEntryIndex);
	public GetLastImageEntryIndexCommand createGetLastImageEntryIndexCommand();
	public CopyFileCommand createCopyFileCommand(@Assisted("origin") Path originPath, @Assisted("copy") Path copyPath);
	public DeleteImageEntryCommand createDeleteImageEntryCommand(ImageEntry entry);
	
	public InitErrorEntryListCommand createInitErrorEntryListCommand(Path errorInputFolderPath);
	public InitErrorGeneratorListCommand createInitErrorGeneratorListCommand(); 
	public GetLastErrorGeneratorIndexCommand createGetLastErrorGeneratorIndexCommand();
	public GetLastErrorEntryIndexCommand createGetLastErrorEntryIndexCommand();
	public SetLastErrorEntryIndexCommand createSetLastErrorEntryIndexCommand(int lastErrorEntryIndex);
	public ApplyErrorCommand createApplyErrorCommand(Mat imageMat, Path imagePath, IErrorGenerator generator);
	public DeleteErrorEntryCommand createDeleteErrorEntryCommand(ErrorEntry entry);
	public CreateErrorImageCommand createCreateErrorImageCommand(ErrorEntry entry);
	
	public InitFeatureExtractorListCommand createInitFeatureExtractorListCommand();
	public InitFeatureVectorListCommand createInitFeatureVectorListCommand(Path folderPath);
	public GetLastFeatureExtractorIndexCommand createGetLastFeatureExtractorIndexCommand();
	public GetLastFeatureVectorIndexCommand createGetLastFeatureVectorIndexCommand();
	public GetLastFrameSizeCommand createGetLastFrameSizeCommand();
	public SetLastFeatureExtractorIndexCommand createSetLastFeatureExtractorIndexCommand(int lastFeatureExtractorIndex);
	public SetLastFrameSizeCommand createSetLastFrameSizeCommand(int lastFrameSize);
	public ExtractFeatureVectorFromErrorEntryCommand createExtractFeatureVectorCommand(Path folderPath, ErrorEntry errorEntry, IFeatureExtractor extractor, int frameSize);
	public DeleteFeatureVectorCommand createRemoveFeatureVectorCommand(FeatureVector featureVector);
	
	public InitBinaryClassifierListCommand createInitBinaryClassifierListCommand();
	public GetLastBinaryClassifierIndexCommand createGetLastBinaryClassifierIndexCommand();
	public SetLastBinaryClassifierIndexCommand createSetLastBinaryClassifierIndexCommand(int lastBinaryClassifierIndex);
	public TrainBinaryClassifierCommand createTrainBinaryClassifierCommand(IBinaryClassifier classifier, FeatureVector selected, List<FeatureVector> all);
}
