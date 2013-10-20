package de.thatsich.bachelor.classificationtraining.restricted.services;

import java.util.List;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.InitBinaryClassifierListCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classificationtraining.restricted.controller.commands.TrainBinaryClassifierCommand;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;

public interface TrainCommandService {
	public InitBinaryClassifierListCommand createInitBinaryClassifierListCommand();
	public GetLastBinaryClassifierIndexCommand createGetLastBinaryClassifierIndexCommand();
	public SetLastBinaryClassifierIndexCommand createSetLastBinaryClassifierIndexCommand(int lastBinaryClassifierIndex);
	public TrainBinaryClassifierCommand createTrainBinaryClassifierCommand(IBinaryClassifier classifier, FeatureVector selected, List<FeatureVector> all);
}
