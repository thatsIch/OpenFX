package de.thatsich.bachelor.preprocessing.intern.command.commands;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javafx.collections.FXCollections;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;

import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.PreProcessorConfiguration;
import de.thatsich.bachelor.preprocessing.intern.command.provider.IPreProcessingProvider;
import de.thatsich.core.javafx.ACommand;


public class InitPreProcessingListCommand extends ACommand<List<IPreProcessing>>
{
	// Fields
	private final Path						path;
	private final IPreProcessingProvider	provider;

	@Inject
	protected InitPreProcessingListCommand( @Assisted Path path, IPreProcessingProvider provider )
	{
		this.path = path;
		this.provider = provider;
	}

	@Override
	protected List<IPreProcessing> call() throws Exception
	{
		final List<IPreProcessing> list = FXCollections.observableArrayList();

		if ( Files.notExists( this.path ) || !Files.isDirectory( this.path ) )
		{
			Files.createDirectories( this.path );
		}

		final String GLOB_PATTERN = "*.{pp}";

		// traverse whole directory and search for pp files
		// try to open them
		// and parse the correct preprocessor
		try (
			DirectoryStream<Path> stream = Files.newDirectoryStream( this.path, GLOB_PATTERN ) )
		{
			for ( Path child : stream )
			{
				try
				{
					// split the file name
					// and check for #member
					// and extract info
					final String fileName = child.getFileName().toString();
					final String[] fileNameSplit = fileName.split( "_" );
					if ( fileNameSplit.length != 4 ) throw new WrongNumberArgsException( "Expected 3 encoded information but found " + fileNameSplit.length );
					this.log.info( "Split FileName." );

					final String preProcessingName = fileNameSplit[0];
					final String inputSizeString = fileNameSplit[1];
					final String outputSizeString = fileNameSplit[2];
					final String id = fileNameSplit[3];
					this.log.info( "Prepared SubInformation." );

					final PreProcessorConfiguration config = new PreProcessorConfiguration( child, preProcessingName, Integer.parseInt( inputSizeString ), Integer.parseInt( outputSizeString ), id );
					final IPreProcessing preProcessing;
					switch ( preProcessingName )
					{
						case "AANNPreProcessor" :
							preProcessing = this.provider.createAANNPreProcessing( config );
							break;

						default :
							throw new IllegalStateException( "Unknown PreProcessing" );
					}
					this.log.info( "Resolved PreProcessing." );

					preProcessing.load( child.toAbsolutePath().toString() );
					this.log.info( "Loaded PP into " + preProcessing );

					list.add( preProcessing );
					this.log.info( "Added " + child );
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				} // END INNER TRY
			} // END FOR
		} // END OUTER TRY
		catch ( IOException | DirectoryIteratorException e )
		{
			e.printStackTrace();
		}
		this.log.info( "All PreProcessings added." );

		return list;
	}
}
