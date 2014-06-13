package de.thatsich.openfx.preprocessed.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessed.intern.control.command.commands.CreatePreProcessedFeatureCommand;
import de.thatsich.openfx.preprocessed.intern.control.command.commands.RemovePreProcessedFeatureCommand;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;


public interface IPreProcessedCommandProvider extends ICommandProvider
{
	CreatePreProcessedFeatureCommand createCreatePreProcessedFeatureCommand(ITrainedPreProcessor preProcessor, IFeature feature);

	RemovePreProcessedFeatureCommand createRemovePreProcessedFeatureCommand(IFeature preProcessedFeature);
}
