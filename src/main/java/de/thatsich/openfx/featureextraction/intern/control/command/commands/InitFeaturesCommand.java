package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureFileStorageService;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class InitFeaturesCommand extends ACommand<List<IFeature>>
{
	// Fields
	private final Path path;
	private final FeatureFileStorageService storage;

	@Inject
	protected InitFeaturesCommand(@Assisted Path path, FeatureFileStorageService storage)
	{
		this.path = path;
		this.storage = storage;
	}

	@Override
	protected List<IFeature> call() throws Exception
	{
		final List<IFeature> features = new LinkedList<>();

		if (Files.notExists(this.path) || !Files.isDirectory(this.path))
		{
			Files.createDirectories(this.path);
		}

		final String GLOB_PATTERN = "*.{csv}";

		// traverse whole directory and search for csv files
		// try to open them
		// and read each csv file
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path, GLOB_PATTERN))
		{
			for (Path child : stream)
			{
				final IFeature load = this.storage.retrieve(child);
				features.add(load);
				this.log.info("Added Feature " + child + " with Attribute " + Files.probeContentType(child));
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		this.log.info("All FeatureVectors added.");

		return features;
	}

}
