package de.thatsich.bachelor.preprocessing.intern.command.commands;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.thatsich.bachelor.classification.intern.command.classifier.IBinaryClassifier;
import de.thatsich.bachelor.classification.intern.command.classifier.RandomForestBinaryClassifier;
import de.thatsich.bachelor.classification.intern.command.classifier.SVMBinaryClassifier;
import de.thatsich.core.javafx.ACommand;

public class InitPreProcesserListCommand extends ACommand<List<IBinaryClassifier>> {

	@Inject private Injector injector;

    private <T extends IBinaryClassifier> T get(Class<T> type) {
        return injector.getInstance(type);
    }
    
	@Override
	protected List<IBinaryClassifier> call() throws Exception {
		final List<IBinaryClassifier> binaryClassifierList = new ArrayList<IBinaryClassifier>();
		
		binaryClassifierList.add(this.get(SVMBinaryClassifier.class));
		binaryClassifierList.add(this.get(RandomForestBinaryClassifier.class));
		
		return binaryClassifierList;
	}

	
}
