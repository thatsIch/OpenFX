package de.thatsich.bachelor.featureextraction.intern.command;

import java.nio.file.Path;

import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.intern.command.commands.DeleteFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.ExtractFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.SetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.SetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.SetLastFrameSizeCommand;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.IFeatureExtractor;
import de.thatsich.core.guice.ICommandProvider;

public interface IFeatureCommandProvider extends ICommandProvider {
	public SetLastFrameSizeCommand createSetLastFrameSizeCommand(int lastFrameSize);
	public SetLastFeatureVectorIndexCommand createSetLastFeatureVectorIndexCommand(int lastFeatureVectorIndex);
	public SetLastFeatureExtractorIndexCommand createSetLastFeatureExtractorIndexCommand(int lastFeatureExtractorIndex);
	
	public ExtractFeatureVectorSetCommand createExtractFeatureVectorCommand(Path folderPath, ErrorEntry errorEntry, IFeatureExtractor extractor, int frameSize, @Assisted("smooth") boolean smooth, @Assisted("threshold") boolean threshold, @Assisted("denoising") boolean denoising);
	public DeleteFeatureVectorSetCommand createRemoveFeatureVectorSetCommand(FeatureVectorSet featureVectorSet);
}
