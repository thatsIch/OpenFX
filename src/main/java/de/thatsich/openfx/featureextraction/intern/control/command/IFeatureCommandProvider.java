package de.thatsich.openfx.featureextraction.intern.control.command;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;
import de.thatsich.openfx.featureextraction.api.control.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.DeleteFeatureVectorSetCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.ExtractFeatureVectorSetCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.SetLastFeatureExtractorIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.SetLastFeatureVectorIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.SetLastFrameSizeCommand;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVectorSet;

import java.nio.file.Path;

public interface IFeatureCommandProvider extends ICommandProvider
{
	SetLastFrameSizeCommand createSetLastFrameSizeCommand(int lastFrameSize);

	SetLastFeatureVectorIndexCommand createSetLastFeatureVectorIndexCommand(int lastFeatureVectorIndex);

	SetLastFeatureExtractorIndexCommand createSetLastFeatureExtractorIndexCommand(int lastFeatureExtractorIndex);

	ExtractFeatureVectorSetCommand createExtractFeatureVectorCommand(Path folderPath, ErrorEntry errorEntry, IFeatureExtractor extractor, int frameSize, @Assisted("smooth") boolean smooth, @Assisted("threshold") boolean threshold, @Assisted("denoising") boolean denoising);

	DeleteFeatureVectorSetCommand createRemoveFeatureVectorSetCommand(FeatureVectorSet featureVectorSet);
}
