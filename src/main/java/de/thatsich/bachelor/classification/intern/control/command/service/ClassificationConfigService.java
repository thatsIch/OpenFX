package de.thatsich.bachelor.classification.intern.control.command.service;

import de.thatsich.core.AConfigurationService;
import javafx.util.Pair;


/**
 * @author Tran Minh Do
 */
public class ClassificationConfigService extends AConfigurationService
{
	private static final Pair<String, Integer> CLASSIFIER_INDEX = new Pair<>("classifer_index", 0);
	private static final Pair<String, Integer> CLASSIFICATION_INDEX = new Pair<>("classification_index", 0);

	public int getLastBinaryClassifierIndexInt()
	{
		return super.get(CLASSIFIER_INDEX.getKey(), CLASSIFIER_INDEX.getValue());
	}

	public void setLastBinaryClassifierIndexInt(int index)
	{
		super.set(CLASSIFIER_INDEX.getKey(), index);
	}

	public int getLastBinaryClassificationIndexInt()
	{
		return super.get(CLASSIFICATION_INDEX.getKey(), CLASSIFICATION_INDEX.getValue());
	}

	public void setLastBinaryClassificationIndexInt(int lastTrainedBinaryClassifierIndex)
	{
		super.set(CLASSIFICATION_INDEX.getKey(), lastTrainedBinaryClassifierIndex);
	}
}
