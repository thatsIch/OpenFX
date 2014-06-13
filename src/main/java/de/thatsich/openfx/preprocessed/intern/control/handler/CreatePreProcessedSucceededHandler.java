package de.thatsich.openfx.preprocessed.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessed.api.model.IPreProcesseds;


/**
 * Handler for what should happen if the Command was successfull
 * for training PreProcessor
 *
 * @author Minh
 */
public class CreatePreProcessedSucceededHandler extends ACommandHandler<IFeature>
{
	@Inject
	private IPreProcesseds pps;

	@Override
	public void handle(IFeature value)
	{
		this.pps.list().add(value);
		this.log.info("Added " + value + " to " + this.pps + ".");

		this.pps.selected().set(value);
		this.log.info("Selected " + value + ".");
	}
}
