package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatureState;
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
	protected InitFeaturesCommand(IFeatureState state, FeatureFileStorageService storage)
	{
		this.path = state.path().get();
		this.storage = storage;
	}

	@Override
	protected List<IFeature> call() throws Exception
	{
		this.createDirectoriesIfNotExists(this.path);

		final List<IFeature> features = new LinkedList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path))
		{
			for (Path featureFolder : stream)
			{
				final IFeature feature = this.storage.retrieve(featureFolder);
				features.add(feature);
				this.log.info("Added Feature " + featureFolder);
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		this.log.info("All FeatureVectors added.");

		return features;
	}


	private void createDirectoriesIfNotExists(Path path) throws IOException
	{
		if (Files.notExists(path) || !Files.isDirectory(path))
		{
			Files.createDirectories(path);
		}
	}
}
