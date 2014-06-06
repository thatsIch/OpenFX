package de.thatsich.openfx.preprocessing.intern.control.command.preprocessing;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.APreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.PreProcessingConfig;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.encog.neural.networks.BasicNetwork;

/**
 * @author thatsIch
 * @since 29.05.2014.
 */
public class IdentityPreProcessing extends APreProcessing
{
	private ReadOnlyObjectProperty<BasicNetwork> networkProperty;

	@Inject
	public IdentityPreProcessing(@Assisted BasicNetwork network, @Assisted PreProcessingConfig config)
	{
		super(config);
		this.networkProperty = new ReadOnlyObjectWrapper<>(network);
	}

	@Override
	public double[] preprocess(final double[] featureVector)
	{
		return featureVector;
	}

	@Override
	public ReadOnlyObjectProperty<BasicNetwork> networkProperty()
	{
		return this.networkProperty;
	}
}
