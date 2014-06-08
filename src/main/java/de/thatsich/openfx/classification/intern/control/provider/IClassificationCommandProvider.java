package de.thatsich.openfx.classification.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassification;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.command.commands.RemoveBinaryClassificationCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.SetLastBinaryClassificationIndexCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.TrainBinaryClassifierCommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;

import java.nio.file.Path;
import java.util.List;

public interface IClassificationCommandProvider extends ICommandProvider
{
	public TrainBinaryClassifierCommand createTrainBinaryClassifierCommand(Path binaryClassifierFolderPath, IBinaryClassifier classifier, IFeature selected, List<IFeature> all);

	public RemoveBinaryClassificationCommand createRemoveBinaryClassificationCommand(IBinaryClassification binaryClassification);

	public SetLastBinaryClassifierIndexCommand createSetLastBinaryClassifierIndexCommand(int lastBinaryClassifierIndex);

	public SetLastBinaryClassificationIndexCommand createSetLastBinaryClassificationIndexCommand(int lastBinaryClassificationIndex);
}
