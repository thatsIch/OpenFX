package de.thatsich.bachelor.featureextraction.intern.control.command;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.errorgeneration.intern.control.error.ErrorEntry;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.DeleteFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.ExtractFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.SetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.SetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.SetLastFrameSizeCommand;
import de.thatsich.bachelor.featureextraction.api.control.IFeatureExtractor;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

public interface IFeatureCommandProvider extends ICommandProvider
{
	public SetLastFrameSizeCommand createSetLastFrameSizeCommand(int lastFrameSize);

	public SetLastFeatureVectorIndexCommand createSetLastFeatureVectorIndexCommand(int lastFeatureVectorIndex);

	public SetLastFeatureExtractorIndexCommand createSetLastFeatureExtractorIndexCommand(int lastFeatureExtractorIndex);

	public ExtractFeatureVectorSetCommand createExtractFeatureVectorCommand(Path folderPath, ErrorEntry errorEntry, IFeatureExtractor extractor, int frameSize, @Assisted("smooth") boolean smooth, @Assisted("threshold") boolean threshold, @Assisted("denoising") boolean denoising);

	public DeleteFeatureVectorSetCommand createRemoveFeatureVectorSetCommand(FeatureVectorSet featureVectorSet);
}
