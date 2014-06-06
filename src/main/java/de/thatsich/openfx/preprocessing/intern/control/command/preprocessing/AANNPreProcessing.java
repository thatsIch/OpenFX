package de.thatsich.openfx.preprocessing.intern.control.command.preprocessing;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.APreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.PreProcessingConfig;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;


/**
 * Result of a AANNPreProcessor
 *
 * @author thatsIch
 */
public class AANNPreProcessing extends APreProcessing
{
	private ReadOnlyObjectProperty<BasicNetwork> networkProperty;

	@Inject
	public AANNPreProcessing(@Assisted BasicNetwork network, @Assisted PreProcessingConfig config)
	{
		super(config);
		this.networkProperty = new ReadOnlyObjectWrapper<>(network);
	}

	@Override
	public double[] preprocess(double[] featureVector)
	{
		final MLData inputData = new BasicMLData(featureVector);
		final MLData outputData = this.networkProperty.get().compute(inputData);

		return outputData.getData();
	}

	@Override
	public ReadOnlyObjectProperty<BasicNetwork> networkProperty()
	{
		return this.networkProperty;
	}
}
