package de.thatsich.openfx.featureextraction.intern.control.command;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.GetLastFeatureIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.GetLastTileSizeCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.InitFeatureExtractorsCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.InitFeaturesCommand;

import java.nio.file.Path;

public interface IFeatureInitCommandProvider extends ICommandProvider
{
	InitFeatureExtractorsCommand createInitFeatureExtractorListCommand();

	InitFeaturesCommand createInitFeaturesCommand(Path folderPath);

	GetLastTileSizeCommand createGetLastFrameSizeCommand();

	GetLastFeatureIndexCommand createGetLastFeatureVectorIndexCommand();

	GetLastFeatureExtractorIndexCommand createGetLastFeatureExtractorIndexCommand();
}
