package de.thatsich.bachelor.preprocessing.intern.command.preprocessing;

import java.io.File;

import javafx.beans.property.ObjectProperty;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.preprocessing.intern.command.preprocessing.core.APreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.PreProcessorConfiguration;


/**
 * Result of a AANNPreProcessor
 * 
 * @author thatsIch
 */
public class AANNPreProcessing extends APreProcessing
{
	private ObjectProperty<BasicNetwork>	networkProperty;

	@Inject
	public AANNPreProcessing( @Assisted BasicNetwork network, @Assisted PreProcessorConfiguration config )
	{
		super( config );
		this.networkProperty.set( network );
	}

	@Override
	public double[] preprocess( double[] featureVector )
	{
		final MLData inputData = new BasicMLData( featureVector );
		final MLData outputData = this.networkProperty.get().compute( inputData );

		return outputData.getData();
	}

	@Override
	public void load( String fileName )
	{
		this.networkProperty.set( ( BasicNetwork ) EncogDirectoryPersistence.loadObject( new File( fileName ) ) );
	}

	@Override
	public void save( String fileName )
	{
		EncogDirectoryPersistence.saveObject( new File( fileName ), this.networkProperty.get() );
	}
}
