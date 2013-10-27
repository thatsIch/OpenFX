package de.thatsich.bachelor.classificationtraining.restricted.controller.commands;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.restricted.application.guice.BinaryClassifierProvider;
import de.thatsich.bachelor.classificationtraining.restricted.model.logic.RandomForestBinaryClassifier;
import de.thatsich.bachelor.classificationtraining.restricted.model.logic.SVMBinaryClassifier;
import de.thatsich.core.javafx.ACommand;

public class InitBinaryClassifierListCommand extends ACommand<List<IBinaryClassifier>> {

	@Inject BinaryClassifierProvider provider;

	@Override
	protected List<IBinaryClassifier> call() throws Exception {
		final List<IBinaryClassifier> binaryClassifierList = new ArrayList<IBinaryClassifier>();
		
		binaryClassifierList.add(provider.get(SVMBinaryClassifier.class));
		binaryClassifierList.add(provider.get(RandomForestBinaryClassifier.class));
		
		return binaryClassifierList;
	}

}
