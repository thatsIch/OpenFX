package de.thatsich.bachelor.featureextraction.intern.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureState;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for getting LastFrameSize
 *
 * @author Minh
 */
public class GetLastFrameSizeSucceededHandler extends ACommandHandler<Integer>
{

	@Inject
	private IFeatureState featureState;

	@Override
	public void handle(Integer value)
	{
		this.featureState.setFrameSize(value);
		this.log.info("Set LastErrorLoopCount in Model.");
	}
}
