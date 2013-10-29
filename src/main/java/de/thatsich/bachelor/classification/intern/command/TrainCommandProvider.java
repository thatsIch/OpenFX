package de.thatsich.bachelor.classification.intern.command;

import java.nio.file.Path;
import java.util.List;

import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassification;
import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassifier;
import de.thatsich.bachelor.classification.intern.command.commands.GetLastBinaryClassificationIndexCommand;
import de.thatsich.bachelor.classification.intern.command.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classification.intern.command.commands.InitBinaryClassificationListCommand;
import de.thatsich.bachelor.classification.intern.command.commands.InitBinaryClassifierListCommand;
import de.thatsich.bachelor.classification.intern.command.commands.RemoveBinaryClassificationCommand;
import de.thatsich.bachelor.classification.intern.command.commands.SetLastBinaryClassificationIndexCommand;
import de.thatsich.bachelor.classification.intern.command.commands.SetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classification.intern.command.commands.TrainBinaryClassifierCommand;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;

public interface TrainCommandProvider {
	public InitBinaryClassifierListCommand createInitBinaryClassifierListCommand();
	public InitBinaryClassificationListCommand createInitBinaryClassificationListCommand(Path trainedBinaryClassifierFolderPath);
	
	public TrainBinaryClassifierCommand createTrainBinaryClassifierCommand(Path binaryClassifierFolderPath, IBinaryClassifier classifier, FeatureVectorSet selected, List<FeatureVectorSet> all);
	public RemoveBinaryClassificationCommand createRemoveBinaryClassificationCommand(IBinaryClassification binaryClassification);
	
	public GetLastBinaryClassifierIndexCommand createGetLastBinaryClassifierIndexCommand();
	public GetLastBinaryClassificationIndexCommand createGetLastBinaryClassificationIndexCommand();
	
	public SetLastBinaryClassifierIndexCommand createSetLastBinaryClassifierIndexCommand(int lastBinaryClassifierIndex);
	public SetLastBinaryClassificationIndexCommand createSetLastBinaryClassificationIndexCommand(int lastBinaryClassificationIndex);
	
}
