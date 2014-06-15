package de.thatsich.openfx.network.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.network.intern.control.command.commands.CreateTrainedNetworkCommand;
import de.thatsich.openfx.network.intern.control.command.commands.DeleteNetworkCommand;
import de.thatsich.openfx.network.intern.control.command.commands.SetLastNetworkIndexCommand;
import de.thatsich.openfx.network.intern.control.prediction.NetworkConfig;
import de.thatsich.openfx.network.intern.control.prediction.TrainedNetwork;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ClassSelection;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.CollectiveNetworkBinaryClassifiers;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.ICNBC;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.INBC;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.NetworkBinaryClassifiers;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;

import java.util.List;
import java.util.Set;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public interface INetworkCommandProvider extends ICommandProvider
{
	CreateTrainedNetworkCommand createTrainedNetworkCommand(List<IImage> images, List<IErrorGenerator> errorGenerators, List<IFeatureExtractor> featureExtractors, List<IPreProcessor> preProcessors, List<IBinaryClassifier> binaryClassifiers);

	NetworkBinaryClassifiers createNetworkBinaryClassifiers(String errorClass, List<ITrainedBinaryClassifier> bcs);

	TrainedNetwork createTrainedNetwork(NetworkConfig config, List<IFeatureExtractor> featureExtractors, ICNBC cnbc);

	CollectiveNetworkBinaryClassifiers createCollectiveNetworkBinaryClassifiers(List<INBC> nbcs, Set<String> uniqueErrorClasses);

	ClassSelection createClassSelection();

	DeleteNetworkCommand createDeleteNetworkCommand(ITrainedNetwork selected);

	SetLastNetworkIndexCommand createSetLastNetworkIndexCommand(int index);
}
