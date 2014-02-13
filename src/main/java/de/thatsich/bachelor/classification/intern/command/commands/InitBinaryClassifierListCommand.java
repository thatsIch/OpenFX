package de.thatsich.bachelor.classification.intern.command.commands;

import java.util.ArrayList;
import java.util.List;

import de.thatsich.bachelor.classification.intern.command.classifier.RandomForestBinaryClassifier;
import de.thatsich.bachelor.classification.intern.command.classifier.SVMBinaryClassifier;
import de.thatsich.bachelor.classification.intern.command.classifier.core.IBinaryClassifier;
import de.thatsich.core.javafx.ACommand;


public class InitBinaryClassifierListCommand extends ACommand<List<IBinaryClassifier>>
{
	private <T extends IBinaryClassifier> T get( Class<T> type )
	{
		return super.injector.getInstance( type );
	}

	@Override
	protected List<IBinaryClassifier> call() throws Exception
	{
		final List<IBinaryClassifier> binaryClassifierList = new ArrayList<IBinaryClassifier>();

		binaryClassifierList.add( this.get( SVMBinaryClassifier.class ) );
		binaryClassifierList.add( this.get( RandomForestBinaryClassifier.class ) );

		return binaryClassifierList;
	}

}
