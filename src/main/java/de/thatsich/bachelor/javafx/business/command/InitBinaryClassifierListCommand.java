package de.thatsich.bachelor.javafx.business.command;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import de.thatsich.bachelor.opencv.classifier.RandomForest;
import de.thatsich.bachelor.opencv.classifier.SVM;
import de.thatsich.core.javafx.Command;
import de.thatsich.core.opencv.IBinaryClassifier;

public class InitBinaryClassifierListCommand extends Command<List<IBinaryClassifier>> {

	@Override
	protected Task<List<IBinaryClassifier>> createTask() {
		return new Task<List<IBinaryClassifier>>() {
			
			@Override
			protected List<IBinaryClassifier> call() throws Exception {
				final List<IBinaryClassifier> binaryClassifierList = new ArrayList<IBinaryClassifier>();
				
				binaryClassifierList.add(new SVM());
				binaryClassifierList.add(new RandomForest());
				
				return binaryClassifierList;
			}
		};
	}

}
