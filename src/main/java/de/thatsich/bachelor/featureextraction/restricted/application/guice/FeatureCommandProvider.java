package de.thatsich.bachelor.featureextraction.restricted.application.guice;

import java.nio.file.Path;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.DeleteFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.ExtractFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.GetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.GetLastFrameSizeCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.InitFeatureExtractorListCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.InitFeatureVectorSetListCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.SetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.SetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.SetLastFrameSizeCommand;
import de.thatsich.core.guice.ICommandProvider;

public interface FeatureCommandProvider extends ICommandProvider {
	public InitFeatureExtractorListCommand createInitFeatureExtractorListCommand();
	public InitFeatureVectorSetListCommand createInitFeatureVectorListCommand(Path folderPath);
	
	public GetLastFrameSizeCommand createGetLastFrameSizeCommand();
	public GetLastFeatureVectorIndexCommand createGetLastFeatureVectorIndexCommand();
	public GetLastFeatureExtractorIndexCommand createGetLastFeatureExtractorIndexCommand();
	
	public SetLastFrameSizeCommand createSetLastFrameSizeCommand(int lastFrameSize);
	public SetLastFeatureVectorIndexCommand createSetLastFeatureVectorIndexCommand(int lastFeatureVectorIndex);
	public SetLastFeatureExtractorIndexCommand createSetLastFeatureExtractorIndexCommand(int lastFeatureExtractorIndex);
	
	public ExtractFeatureVectorSetCommand createExtractFeatureVectorCommand(Path folderPath, ErrorEntry errorEntry, IFeatureExtractor extractor, int frameSize);
	public DeleteFeatureVectorSetCommand createRemoveFeatureVectorSetCommand(FeatureVectorSet featureVectorSet);
}
