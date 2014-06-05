package de.thatsich.openfx.featureextraction.intern.control.command;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.DeleteFeatureCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.ExtractFeatureCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.SetLastFeatureExtractorIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.SetLastFeatureIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.SetLastTileSizeCommand;

public interface IFeatureCommandProvider extends ICommandProvider
{
	SetLastTileSizeCommand createSetLastFrameSizeCommand(int lastFrameSize);

	SetLastFeatureIndexCommand createSetLastFeatureIndexCommand(int lastFeatureVectorIndex);

	SetLastFeatureExtractorIndexCommand createSetLastFeatureExtractorIndexCommand(int lastFeatureExtractorIndex);

	ExtractFeatureCommand createExtractFeatureVectorCommand(IError error, IFeatureExtractor extractor, int frameSize, @Assisted("smooth") boolean smooth, @Assisted("threshold") boolean threshold, @Assisted("denoising") boolean denoising);

	DeleteFeatureCommand createRemoveFeatureVectorSetCommand(IFeature feature);
}
