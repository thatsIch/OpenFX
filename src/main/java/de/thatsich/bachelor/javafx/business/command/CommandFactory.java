package de.thatsich.bachelor.javafx.business.command;

import java.nio.file.Path;
import java.util.List;

import org.opencv.core.Mat;

import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.InitBinaryClassifierListCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.TrainBinaryClassifierCommand;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.ApplyErrorCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.CreateErrorImageCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.DeleteErrorEntryCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.GetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.GetLastErrorGeneratorIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.InitErrorEntryListCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.InitErrorGeneratorListCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.SetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.DeleteFeatureVectorCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.ExtractFeatureVectorFromErrorEntryCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.GetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.GetLastFrameSizeCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.InitFeatureExtractorListCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.InitFeatureVectorListCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.SetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.SetLastFrameSizeCommand;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.imageprocessing.restricted.controller.commands.CopyFileCommand;
import de.thatsich.bachelor.imageprocessing.restricted.controller.commands.DeleteImageEntryCommand;
import de.thatsich.bachelor.imageprocessing.restricted.controller.commands.GetLastImageEntryIndexCommand;
import de.thatsich.bachelor.imageprocessing.restricted.controller.commands.InitImageEntryListCommand;
import de.thatsich.bachelor.imageprocessing.restricted.controller.commands.SetLastImageEntryIndexCommand;
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
