package de.thatsich.bachelor.featureextraction.intern.control.command;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.errorgeneration.intern.control.error.core.ErrorEntry;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.api.control.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.DeleteFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.ExtractFeatureVectorSetCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.SetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.SetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.SetLastFrameSizeCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

public interface IFeatureCommandProvider extends ICommandProvider
{
	SetLastFrameSizeCommand createSetLastFrameSizeCommand(int lastFrameSize);

	SetLastFeatureVectorIndexCommand createSetLastFeatureVectorIndexCommand(int lastFeatureVectorIndex);

	SetLastFeatureExtractorIndexCommand createSetLastFeatureExtractorIndexCommand(int lastFeatureExtractorIndex);

	ExtractFeatureVectorSetCommand createExtractFeatureVectorCommand(Path folderPath, ErrorEntry errorEntry, IFeatureExtractor extractor, int frameSize, @Assisted("smooth") boolean smooth, @Assisted("threshold") boolean threshold, @Assisted("denoising") boolean denoising);

	DeleteFeatureVectorSetCommand createRemoveFeatureVectorSetCommand(FeatureVectorSet featureVectorSet);
}
