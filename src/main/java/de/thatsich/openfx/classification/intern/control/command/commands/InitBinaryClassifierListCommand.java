package de.thatsich.openfx.classification.intern.control.command.commands;

import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.RandomForestBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.SVMBinaryClassifier;

import java.util.ArrayList;
import java.util.List;


public class InitBinaryClassifierListCommand extends ACommand<List<IBinaryClassifier>>
{
	@Override
	protected List<IBinaryClassifier> call() throws Exception
	{
		final List<IBinaryClassifier> binaryClassifierList = new ArrayList<>();

		binaryClassifierList.add(this.get(SVMBinaryClassifier.class));
		binaryClassifierList.add(this.get(RandomForestBinaryClassifier.class));

		return binaryClassifierList;
	}

	private <T extends IBinaryClassifier> T get(Class<T> type)
	{
		return super.injector.getInstance(type);
	}

}
