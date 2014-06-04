package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.intern.control.entity.Feature;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;
import de.thatsich.openfx.featureextraction.intern.service.CSVService;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InitFeaturesCommand extends ACommand<List<IFeature>>
{

	// Fields
	private final Path path;

	// Injects
	@Inject
	private CSVService csv;

	@Inject
	protected InitFeaturesCommand(@Assisted Path path)
	{
		this.path = path;
	}

	@Override
	protected List<IFeature> call() throws Exception
	{
		final List<IFeature> features = new ArrayList<>();

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

				// split the file name 
				// and check if has 4 members
				// and extract them
				final String fileName = child.getFileName().toString();
				final String[] fileNameSplit = fileName.split("_");
				if (fileNameSplit.length != 4)
				{
					throw new WrongNumberArgsException("Expected 4 encoded information but found " + fileNameSplit.length);
				}
				final List<List<Float>> floatValues = this.csv.read(child);
				final String className = fileNameSplit[0];
				final String extractorName = fileNameSplit[1];
				final int frameSize = Integer.parseInt(fileNameSplit[2]);

				// read each line
				// and store them as a single FeatureVector
				// in the end write all information into the FeatureVectorSet
				final List<IFeatureVector> featureVectorList = FXCollections.observableArrayList();
				for (List<Float> vector : floatValues)
				{
					final Float label = vector.get(vector.size() - 1);
					vector.remove(vector.size() - 1);

					final boolean boolLabel = (label > 0);
					featureVectorList.add(new FeatureVector(vector, boolLabel));
				}

				floatValues.clear();

				features.add(new Feature(child, className, extractorName, frameSize, featureVectorList));
				this.log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
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
