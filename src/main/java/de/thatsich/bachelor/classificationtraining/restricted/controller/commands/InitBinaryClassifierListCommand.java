package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.api.entities.RandomForest;
import de.thatsich.bachelor.classificationtraining.api.entities.SVM;
import de.thatsich.core.javafx.Command;

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
