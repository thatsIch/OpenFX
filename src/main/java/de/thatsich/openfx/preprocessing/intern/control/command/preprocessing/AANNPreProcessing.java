package de.thatsich.openfx.preprocessing.intern.control.command.preprocessing;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.APreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.PreProcessorConfiguration;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import java.io.File;


/**
 * Result of a AANNPreProcessor
 *
 * @author thatsIch
 */
public class AANNPreProcessing extends APreProcessing
{
	private ObjectProperty<BasicNetwork> networkProperty;

	@Inject
	public AANNPreProcessing(@Assisted BasicNetwork network, @Assisted PreProcessorConfiguration config)
	{
		super(config);
		this.networkProperty = new SimpleObjectProperty<>(network);
	}

	@Override
	public double[] preprocess(double[] featureVector)
	{
		final MLData inputData = new BasicMLData(featureVector);
		final MLData outputData = this.networkProperty.get().compute(inputData);

		return outputData.getData();
	}

	@Override
	public void load(String fileName)
	{
		this.networkProperty.set((BasicNetwork) EncogDirectoryPersistence.loadObject(new File(fileName)));
	}

	@Override
	public void save(String fileName)
	{
		EncogDirectoryPersistence.saveObject(new File(fileName), this.networkProperty.get());
	}
}
