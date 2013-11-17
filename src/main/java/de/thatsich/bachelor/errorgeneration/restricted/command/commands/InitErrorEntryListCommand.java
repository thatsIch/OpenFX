package de.thatsich.bachelor.errorgeneration.restricted.command.commands;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.restricted.service.ErrorFactoryService;
import de.thatsich.core.javafx.ACommand;


public class InitErrorEntryListCommand extends ACommand<List<ErrorEntry>>
{

	private final Path	errorInputFolderPath;

	@Inject
	private ErrorFactoryService factory;
	
	@Inject
	protected InitErrorEntryListCommand( @Assisted Path errorInputFolderPath )
	{
		this.errorInputFolderPath = errorInputFolderPath;
	}

	@Override
	protected List<ErrorEntry> call() throws Exception
	{
		final List<ErrorEntry> errorEntryList = new ArrayList<ErrorEntry>();

		if ( Files.notExists( this.errorInputFolderPath ) || !Files.isDirectory( this.errorInputFolderPath ) ) Files.createDirectories( this.errorInputFolderPath );

		final String GLOB_PATTERN = "*.{png,jpeg,jpg,jpe}";

		try (
			DirectoryStream<Path> stream = Files.newDirectoryStream( this.errorInputFolderPath, GLOB_PATTERN ) )
		{
			for ( Path childPath : stream )
			{
				errorEntryList.add( this.factory.getErrorEntryFromPath( childPath ) );
				this.log.info( "Added " + childPath + " with Attribute " + Files.probeContentType( childPath ) );
			}
		}
		catch ( IOException | DirectoryIteratorException e )
		{
			e.printStackTrace();
		}
		this.log.info( "All OpenCV Supported Images added: " + errorEntryList.size() + "." );

		return errorEntryList;
	}
}
