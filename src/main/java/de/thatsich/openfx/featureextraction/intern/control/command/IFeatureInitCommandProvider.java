package de.thatsich.openfx.featureextraction.intern.control.command;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.GetLastFeatureVectorIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.GetLastFrameSizeCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.InitFeatureExtractorListCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.InitFeaturesCommand;

import java.nio.file.Path;

public interface IFeatureInitCommandProvider extends ICommandProvider
{
	InitFeatureExtractorListCommand createInitFeatureExtractorListCommand();

	InitFeaturesCommand createInitFeatureVectorListCommand(Path folderPath);

	GetLastFrameSizeCommand createGetLastFrameSizeCommand();

	GetLastFeatureVectorIndexCommand createGetLastFeatureVectorIndexCommand();

	GetLastFeatureExtractorIndexCommand createGetLastFeatureExtractorIndexCommand();
}
