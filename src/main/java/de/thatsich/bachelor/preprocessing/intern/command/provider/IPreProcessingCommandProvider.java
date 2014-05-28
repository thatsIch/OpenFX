package de.thatsich.bachelor.preprocessing.intern.command.provider;

import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.commands.RemovePreProcessingCommand;
import de.thatsich.bachelor.preprocessing.intern.command.commands.SetLastPreProcessingIndexCommand;
import de.thatsich.bachelor.preprocessing.intern.command.commands.SetLastPreProcessorIndexCommand;
import de.thatsich.bachelor.preprocessing.intern.command.commands.TrainPreProcessorCommand;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.IPreProcessor;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;
import java.util.List;


public interface IPreProcessingCommandProvider extends ICommandProvider
{
	TrainPreProcessorCommand createTrainPreProcessorCommand(Path preProcessingFolderPath, IPreProcessor selectedPreProcessor, FeatureVectorSet selectedFeatureVectorSet, List<FeatureVectorSet> featureVectorSetList);

	RemovePreProcessingCommand createRemovePreProcessingCommand(IPreProcessing pp);

	SetLastPreProcessorIndexCommand createSetLastPreProcessorIndexCommand(int index);

	SetLastPreProcessingIndexCommand createSetLastPreProcessingIndexCommand(int index);
}
