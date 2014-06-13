package de.thatsich.openfx.preprocessing.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.CreateTrainedPreProcessorCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.RemovePreProcessingCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.SetLastPreProcessingIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.SetLastPreProcessorIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;


public interface IPreProcessingCommandProvider extends ICommandProvider
{
	CreateTrainedPreProcessorCommand createTrainPreProcessorCommand(IPreProcessor preProcessor, IFeature feature);

	RemovePreProcessingCommand createRemovePreProcessingCommand(ITrainedPreProcessor pp);

	SetLastPreProcessorIndexCommand createSetLastPreProcessorIndexCommand(int index);

	SetLastPreProcessingIndexCommand createSetLastPreProcessingIndexCommand(int index);
}
