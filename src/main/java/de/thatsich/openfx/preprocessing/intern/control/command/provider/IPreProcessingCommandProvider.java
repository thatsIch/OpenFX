package de.thatsich.openfx.preprocessing.intern.control.command.provider;

import de.thatsich.openfx.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.RemovePreProcessingCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.SetLastPreProcessingIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.SetLastPreProcessorIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.TrainPreProcessorCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
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
