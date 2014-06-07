package de.thatsich.openfx.preprocessing.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.RemovePreProcessingCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.SetLastPreProcessingIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.SetLastPreProcessorIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.TrainPreProcessorCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;

import java.util.List;


public interface IPreProcessingCommandProvider extends ICommandProvider
{
	TrainPreProcessorCommand createTrainPreProcessorCommand(IPreProcessor selectedPreProcessor, IFeature selectedFeatureVectorSet, List<IFeature> featureVectorSetList);

	RemovePreProcessingCommand createRemovePreProcessingCommand(IPreProcessing pp);

	SetLastPreProcessorIndexCommand createSetLastPreProcessorIndexCommand(int index);

	SetLastPreProcessingIndexCommand createSetLastPreProcessingIndexCommand(int index);
}
