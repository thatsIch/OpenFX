package de.thatsich.openfx.classification.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.command.commands.CreateTrainedBinaryClassifierCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.DeleteBinaryClassificationCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.SetLastBinaryClassificationIndexCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;

import java.nio.file.Path;

public interface IClassificationCommandProvider extends ICommandProvider
{
	CreateTrainedBinaryClassifierCommand createTrainBinaryClassifierCommand(Path binaryClassifierFolderPath, IBinaryClassifier classifier, IFeature selected);

	DeleteBinaryClassificationCommand createRemoveBinaryClassificationCommand(ITrainedBinaryClassifier binaryClassification);

	SetLastBinaryClassifierIndexCommand createSetLastBinaryClassifierIndexCommand(int lastBinaryClassifierIndex);

	SetLastBinaryClassificationIndexCommand createSetLastBinaryClassificationIndexCommand(int lastBinaryClassificationIndex);
}
