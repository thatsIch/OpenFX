package de.thatsich.bachelor.preprocessing.intern.control.command.provider;

import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.bachelor.preprocessing.api.control.IPreProcessing;
import de.thatsich.bachelor.preprocessing.intern.control.command.commands.RemovePreProcessingCommand;
import de.thatsich.bachelor.preprocessing.intern.control.command.commands.SetLastPreProcessingIndexCommand;
import de.thatsich.bachelor.preprocessing.intern.control.command.commands.SetLastPreProcessorIndexCommand;
import de.thatsich.bachelor.preprocessing.intern.control.command.commands.TrainPreProcessorCommand;
import de.thatsich.bachelor.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
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
