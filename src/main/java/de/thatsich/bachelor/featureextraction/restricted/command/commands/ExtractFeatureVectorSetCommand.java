package de.thatsich.bachelor.featureextraction.restricted.command.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.collections.FXCollections;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.IFeatureExtractor;
import de.thatsich.bachelor.featureextraction.restricted.services.CSVService;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.core.opencv.Images;


public class ExtractFeatureVectorSetCommand extends ACommand<FeatureVectorSet>
{
	// Properties
	private final Path				featureInputFolderPath;
	private final ErrorEntry		errorEntry;
	private final IFeatureExtractor	featureExtractor;
	private final int				frameSize;

	// Injects
	@Inject
	private CSVService				csvService;

	@Inject
	public ExtractFeatureVectorSetCommand( @Assisted Path folderPath, @Assisted ErrorEntry errorEntry, @Assisted IFeatureExtractor extractor, @Assisted int frameSize )
	{
		this.featureInputFolderPath = folderPath;
		this.errorEntry = errorEntry;
		this.featureExtractor = extractor;
		this.frameSize = frameSize;
	}

	@Override
	protected FeatureVectorSet call() throws Exception
	{
		final String className = this.errorEntry.getErrorClassProperty().get();
		final String extractorName = this.featureExtractor.getName();
		final String id = UUID.randomUUID().toString();
		log.info( "Prepared all necessary information." );

		final List<FeatureVector> featureVectorList = FXCollections.observableArrayList();
		final List<List<Float>> csvResult = FXCollections.observableArrayList();
		final Mat[][] originalErrorSplit = Images.split( this.errorEntry.getOriginalWithErrorMat(), this.frameSize, this.frameSize );
		final Mat[][] errorSplit = Images.split( this.errorEntry.getErrorMat(), this.frameSize, this.frameSize );
		log.info( "Prepared split images." );

		for ( int col = 0; col < originalErrorSplit.length; col++ )
		{
			for ( int row = 0; row < originalErrorSplit[col].length; row++ )
			{
				try
				{
					// extract feature vector
					// and reshape them into a one row feature vector if its 2D
					// mat and removes unecessary channels
					final MatOfFloat featureVector = this.featureExtractor.extractFeature( originalErrorSplit[col][row] );
					List<Float> featureVectorAsList = new ArrayList<Float>( featureVector.toList() );

					// if contain an error classify it as positive match
					if ( Core.sumElems( errorSplit[col][row] ).val[0] != 0 )
					{
						featureVectorList.add( new FeatureVector( featureVectorAsList, true ) );
						featureVectorAsList.add( 1F );
					}

					// else its a negative match
					else
					{
						featureVectorList.add( new FeatureVector( featureVectorAsList, false ) );
						featureVectorAsList.add( 0F );
					}

					csvResult.add( featureVectorAsList );
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
		}

		final StringBuffer buffer = new StringBuffer();
		buffer.append( className + "_" );
		buffer.append( extractorName + "_" );
		buffer.append( this.frameSize + "_" );
		buffer.append( id + ".csv" );

		final Path filePath = this.featureInputFolderPath.resolve( buffer.toString() );
		this.csvService.write( filePath, csvResult );

		this.log.info( "Extracted FeatureVectors: " + featureVectorList.size() );
		return new FeatureVectorSet( filePath, className, extractorName, this.frameSize, id, featureVectorList );
	}
}
