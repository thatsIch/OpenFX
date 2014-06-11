package de.thatsich.openfx.featureextraction.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.DeleteFeatureCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.CreateExtractedFeatureCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.SetLastFeatureExtractorIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.SetLastFeatureIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.SetLastTileSizeCommand;

public interface IFeatureCommandProvider extends ICommandProvider
{
	SetLastTileSizeCommand createSetLastFrameSizeCommand(int lastFrameSize);

	SetLastFeatureIndexCommand createSetLastFeatureIndexCommand(int lastFeatureVectorIndex);

	SetLastFeatureExtractorIndexCommand createSetLastFeatureExtractorIndexCommand(int lastFeatureExtractorIndex);

	CreateExtractedFeatureCommand createExtractFeatureCommand(IError error, IFeatureExtractor extractor, int frameSize);

	DeleteFeatureCommand createDeleteFeatureCommand(IFeature feature);
}
