package de.thatsich.bachelor.featureextraction.intern.command;

import java.nio.file.Path;

import de.thatsich.bachelor.featureextraction.intern.command.commands.GetLastFeatureExtractorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.GetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.GetLastFrameSizeCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.InitFeatureExtractorListCommand;
import de.thatsich.bachelor.featureextraction.intern.command.commands.InitFeatureVectorSetListCommand;
import de.thatsich.core.guice.ICommandProvider;

public interface IFeatureInitCommandProvider extends ICommandProvider {
	public InitFeatureExtractorListCommand createInitFeatureExtractorListCommand();
	public InitFeatureVectorSetListCommand createInitFeatureVectorListCommand(Path folderPath);
	
	public GetLastFrameSizeCommand createGetLastFrameSizeCommand();
	public GetLastFeatureVectorIndexCommand createGetLastFeatureVectorIndexCommand();
	public GetLastFeatureExtractorIndexCommand createGetLastFeatureExtractorIndexCommand();
}
