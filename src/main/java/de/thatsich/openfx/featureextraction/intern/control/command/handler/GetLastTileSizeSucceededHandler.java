package de.thatsich.openfx.featureextraction.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.model.IFeatureState;

/**
 * Handler for what should happen if the Command was successfull
 * for getting LastFrameSize
 *
 * @author Minh
 */
public class GetLastTileSizeSucceededHandler extends ACommandHandler<Integer>
{
	@Inject private IFeatureState state;

	@Override
	public void handle(Integer value)
	{
		if (value >= 0)
		{
			this.state.frameSize().set(value);
			this.log.info("Set LastErrorLoopCount in Model.");
		}
	}
}
