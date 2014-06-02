package de.thatsich.bachelor.featureextraction.intern.control.command;

import de.thatsich.bachelor.featureextraction.intern.control.command.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.GetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.GetLastFrameSizeCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.InitFeatureExtractorListCommand;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.InitFeatureVectorSetListCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

public interface IFeatureInitCommandProvider extends ICommandProvider
{
	InitFeatureExtractorListCommand createInitFeatureExtractorListCommand();

	InitFeatureVectorSetListCommand createInitFeatureVectorListCommand(Path folderPath);

	GetLastFrameSizeCommand createGetLastFrameSizeCommand();

	GetLastFeatureVectorIndexCommand createGetLastFeatureVectorIndexCommand();

	GetLastFeatureExtractorIndexCommand createGetLastFeatureExtractorIndexCommand();
}
