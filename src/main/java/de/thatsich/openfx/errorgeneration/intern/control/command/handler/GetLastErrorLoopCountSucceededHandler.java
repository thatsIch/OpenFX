package de.thatsich.openfx.errorgeneration.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.errorgeneration.api.model.IErrorState;

/**
 * Handler for what should happen if the Command was successfull
 * for getting LastErrorLoopCount
 *
 * @author Minh
 */
public class GetLastErrorLoopCountSucceededHandler extends ACommandHandler<Integer>
{
	@Inject private IErrorState errorState;

	@Override
	public void handle(Integer value)
	{
		this.errorState.loopCount().set(value);
		this.log.info("Set LastErrorLoopCount in Model.");
	}
}

