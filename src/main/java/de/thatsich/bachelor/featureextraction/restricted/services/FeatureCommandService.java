package de.thatsich.bachelor.featureextraction.restricted.services;

import java.nio.file.Path;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.DeleteFeatureVectorCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.ExtractFeatureVectorFromErrorEntryCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.GetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.GetLastFrameSizeCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.InitFeatureExtractorListCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.InitFeatureVectorListCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.SetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.SetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.controller.commands.SetLastFrameSizeCommand;

public interface FeatureCommandService {
	public InitFeatureExtractorListCommand createInitFeatureExtractorListCommand();
	public InitFeatureVectorListCommand createInitFeatureVectorListCommand(Path folderPath);
	
	public GetLastFrameSizeCommand createGetLastFrameSizeCommand();
	public GetLastFeatureVectorIndexCommand createGetLastFeatureVectorIndexCommand();
	public GetLastFeatureExtractorIndexCommand createGetLastFeatureExtractorIndexCommand();
	
	public SetLastFrameSizeCommand createSetLastFrameSizeCommand(int lastFrameSize);
	public SetLastFeatureVectorIndexCommand createSetLastFeatureVectorIndexCommand(int lastFeatureVectorIndex);
	public SetLastFeatureExtractorIndexCommand createSetLastFeatureExtractorIndexCommand(int lastFeatureExtractorIndex);
	
	
	public ExtractFeatureVectorFromErrorEntryCommand createExtractFeatureVectorCommand(Path folderPath, ErrorEntry errorEntry, IFeatureExtractor extractor, int frameSize);
	public DeleteFeatureVectorCommand createRemoveFeatureVectorCommand(FeatureVector featureVector);
}
