package de.thatsich.openfx.preprocessed.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessed.api.model.IPreProcesseds;

import java.util.List;


/**
 * Handler for what should happen if the Command was successfull
 * for initializing the preprocessing get
 *
 * @author Minh
 */
public class InitPreProcessedsSucceededHandler extends ACommandHandler<List<IFeature>>
{
	@Inject
	private IPreProcesseds pps;

	@Override
	public void handle(List<IFeature> value)
	{
		this.pps.list().addAll(value);
		this.log.info("Added " + value + " with Size " + value.size() + " to DataBase.");
	}
}
