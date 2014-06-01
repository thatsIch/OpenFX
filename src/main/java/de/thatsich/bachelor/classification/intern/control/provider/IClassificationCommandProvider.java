package de.thatsich.bachelor.classification.intern.control.provider;

import de.thatsich.bachelor.classification.api.control.IBinaryClassification;
import de.thatsich.bachelor.classification.intern.control.classifier.core.IBinaryClassifier;
import de.thatsich.bachelor.classification.intern.control.command.commands.RemoveBinaryClassificationCommand;
import de.thatsich.bachelor.classification.intern.control.command.commands.SetLastBinaryClassificationIndexCommand;
import de.thatsich.bachelor.classification.intern.control.command.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classification.intern.control.command.commands.TrainBinaryClassifierCommand;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;
import java.util.List;

public interface IClassificationCommandProvider extends ICommandProvider
{
	public TrainBinaryClassifierCommand createTrainBinaryClassifierCommand(Path binaryClassifierFolderPath, IBinaryClassifier classifier, FeatureVectorSet selected, List<FeatureVectorSet> all);

	public RemoveBinaryClassificationCommand createRemoveBinaryClassificationCommand(IBinaryClassification binaryClassification);

	public SetLastBinaryClassifierIndexCommand createSetLastBinaryClassifierIndexCommand(int lastBinaryClassifierIndex);

	public SetLastBinaryClassificationIndexCommand createSetLastBinaryClassificationIndexCommand(int lastBinaryClassificationIndex);
}