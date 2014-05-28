package de.thatsich.bachelor.featureextraction.intern.command;

import de.thatsich.bachelor.featureextraction.intern.command.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.GetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.GetLastFrameSizeCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.InitFeatureExtractorListCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.InitFeatureVectorSetListCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

public interface IFeatureInitCommandProvider extends ICommandProvider
{
	public InitFeatureExtractorListCommand createInitFeatureExtractorListCommand();

	public InitFeatureVectorSetListCommand createInitFeatureVectorListCommand(Path folderPath);

	public GetLastFrameSizeCommand createGetLastFrameSizeCommand();

	public GetLastFeatureVectorIndexCommand createGetLastFeatureVectorIndexCommand();

	public GetLastFeatureExtractorIndexCommand createGetLastFeatureExtractorIndexCommand();
}
