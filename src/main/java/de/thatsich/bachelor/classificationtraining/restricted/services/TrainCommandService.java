package de.thatsich.bachelor.classificationtraining.restricted.services;

import java.nio.file.Path;
import java.util.List;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.GetLastTrainedBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.InitBinaryClassifierListCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.InitTrainedBinaryClassifierListCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.TrainBinaryClassifierCommand;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;

public interface TrainCommandService {
	public InitBinaryClassifierListCommand createInitBinaryClassifierListCommand();
	public GetLastBinaryClassifierIndexCommand createGetLastBinaryClassifierIndexCommand();
	public SetLastBinaryClassifierIndexCommand createSetLastBinaryClassifierIndexCommand(int lastBinaryClassifierIndex);
	public TrainBinaryClassifierCommand createTrainBinaryClassifierCommand(Path binaryClassifierFolderPath, IBinaryClassifier classifier, FeatureVectorSet selected, List<FeatureVectorSet> all);
	public InitTrainedBinaryClassifierListCommand createInitTrainedBinaryClassifierListCommand(Path trainedBinaryClassifierFolderPath);
	public GetLastTrainedBinaryClassifierIndexCommand createGetLastTrainedBinaryClassifierIndexCommand();
}
