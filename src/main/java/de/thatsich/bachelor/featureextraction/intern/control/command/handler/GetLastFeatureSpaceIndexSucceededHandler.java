package de.thatsich.bachelor.featureextraction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.api.model.IFeatureVectorSets;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for getting the LastFeatureVectorIndex
 *
 * @author Minh
 */
public class GetLastFeatureSpaceIndexSucceededHandler extends ACommandHandler<Integer>
{
	@Inject	private IFeatureVectorSets featureVectors;

	@Override
	public void handle(Integer value)
	{
		this.featureVectors.index().set(value);
		this.log.info("Set SelectedIndex.");
	}
}
