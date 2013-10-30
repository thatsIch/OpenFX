package de.thatsich.bachelor.classification.intern.command.commands;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.intern.command.BinaryClassifierProvider;
import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassifier;
import de.thatsich.bachelor.classification.intern.command.classifier.RandomForestBinaryClassifier;
import de.thatsich.bachelor.classification.intern.command.classifier.SVMBinaryClassifier;
import de.thatsich.core.javafx.ACommand;

public class InitBinaryClassifierListCommand extends ACommand<List<IBinaryClassifier>> {

	@Inject private BinaryClassifierProvider provider;

	@Override
	protected List<IBinaryClassifier> call() throws Exception {
		final List<IBinaryClassifier> binaryClassifierList = new ArrayList<IBinaryClassifier>();
		
		binaryClassifierList.add(this.provider.get(SVMBinaryClassifier.class));
		binaryClassifierList.add(this.provider.get(RandomForestBinaryClassifier.class));
		
		return binaryClassifierList;
	}

}
