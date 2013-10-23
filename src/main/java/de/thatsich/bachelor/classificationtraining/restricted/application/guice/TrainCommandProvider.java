package de.thatsich.bachelor.classificationtraining.restricted.application.guice;

import java.nio.file.Path;
import java.util.List;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.GetLastBinaryClassificationIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.InitBinaryClassifierListCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.InitBinaryClassificationListCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.TrainBinaryClassifierCommand;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;

public interface TrainCommandProvider {
	public InitBinaryClassifierListCommand createInitBinaryClassifierListCommand();
	public GetLastBinaryClassifierIndexCommand createGetLastBinaryClassifierIndexCommand();
	public SetLastBinaryClassifierIndexCommand createSetLastBinaryClassifierIndexCommand(int lastBinaryClassifierIndex);
	public TrainBinaryClassifierCommand createTrainBinaryClassifierCommand(Path binaryClassifierFolderPath, IBinaryClassifier classifier, FeatureVectorSet selected, List<FeatureVectorSet> all);
	public InitBinaryClassificationListCommand createInitBinaryClassificationListCommand(Path trainedBinaryClassifierFolderPath);
	public GetLastBinaryClassificationIndexCommand createGetLastBinaryClassificationIndexCommand();
}
