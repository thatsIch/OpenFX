package de.thatsich.openfx.featureextraction.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.GetLastFeatureIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.GetLastTileSizeCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.InitFeatureExtractorsCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.InitFeaturesCommand;

public interface IFeatureInitCommandProvider extends ICommandProvider
{
	GetLastTileSizeCommand createGetLastFrameSizeCommand();

	InitFeatureExtractorsCommand createInitFeatureExtractorListCommand();

	GetLastFeatureExtractorIndexCommand createGetLastFeatureExtractorIndexCommand();

	InitFeaturesCommand createInitFeaturesCommand();

	GetLastFeatureIndexCommand createGetLastFeatureIndexCommand();
}
