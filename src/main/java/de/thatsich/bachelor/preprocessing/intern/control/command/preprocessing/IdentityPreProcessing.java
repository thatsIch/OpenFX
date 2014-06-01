package de.thatsich.bachelor.preprocessing.intern.control.command.preprocessing;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.preprocessing.intern.control.command.preprocessing.core.APreProcessing;
import de.thatsich.bachelor.preprocessing.intern.control.command.preprocessor.core.PreProcessorConfiguration;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import java.io.File;

/**
 * @author thatsIch
 * @since 29.05.2014.
 */
public class IdentityPreProcessing extends APreProcessing
{
	private ObjectProperty<BasicNetwork> networkProperty;

	@Inject
	public IdentityPreProcessing(@Assisted BasicNetwork network, @Assisted PreProcessorConfiguration config)
	{
		super(config);
		this.networkProperty = new SimpleObjectProperty<>(network);
	}

	@Override
	public double[] preprocess(final double[] featureVector)
	{
		return featureVector;
	}

	@Override
	public void load(final String fileName)
	{
		this.networkProperty.set((BasicNetwork) EncogDirectoryPersistence.loadObject(new File(fileName)));
	}

	@Override
	public void save(final String fileName)
	{
		EncogDirectoryPersistence.saveObject(new File(fileName), this.networkProperty.get());
	}
}
