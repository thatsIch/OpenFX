package de.thatsich.openfx.preprocessed.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessed.api.model.IPreProcesseds;

import java.util.List;


/**
 * Handler for what should happen if the Command was successfull
 * for deleting the preprocessing
 *
 * @author Minh
 */
public class DeletePreProcessedSucceededHandler extends ACommandHandler<IFeature>
{
	@Inject
	private IPreProcesseds pps;

	@Override
	public void handle(IFeature value)
	{
		final List<IFeature> list = this.pps.list();
		list.remove(value);
		this.log.info("Removed " + value);

		if (list.size() > 0)
		{
			final IFeature first = list.get(0);
			this.pps.selected().set(first);
			this.log.info("Reset Selection to first: " + first);
		}
		else
		{
			this.pps.selected().set(null);
			this.log.info("Reset selection to null");
		}
	}
}
