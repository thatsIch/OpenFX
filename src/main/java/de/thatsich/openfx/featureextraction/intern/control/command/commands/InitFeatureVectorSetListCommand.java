package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;
import de.thatsich.openfx.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.openfx.featureextraction.intern.services.CSVService;
import de.thatsich.core.javafx.ACommand;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InitFeatureVectorSetListCommand extends ACommand<List<FeatureVectorSet>>
{

	// Fields
	private final Path featureVectorFolderPath;

	// Injects
	@Inject
	private CSVService csv;

	@Inject
	protected InitFeatureVectorSetListCommand(@Assisted Path featureVectorFolderPath)
	{
		this.featureVectorFolderPath = featureVectorFolderPath;
	}

	@Override
	protected List<FeatureVectorSet> call() throws Exception
	{
		final List<FeatureVectorSet> featureVectorSetList = new ArrayList<>();

		if (Files.notExists(this.featureVectorFolderPath) || !Files.isDirectory(this.featureVectorFolderPath))
		{
			Files.createDirectories(this.featureVectorFolderPath);
		}

		final String GLOB_PATTERN = "*.{csv}";

		// traverse whole directory and search for csv files
		// try to open them
		// and read each csv file
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.featureVectorFolderPath, GLOB_PATTERN))
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
				final List<List<Float>> floatValues = csv.read(child);
				final String className = fileNameSplit[0];
				final String extractorName = fileNameSplit[1];
				final int frameSize = Integer.parseInt(fileNameSplit[2]);
				final String id = fileNameSplit[3];

				// read each line
				// and store them as a single FeatureVector
				// in the end write all information into the FeatureVectorSet
				final List<FeatureVector> featureVectorList = FXCollections.observableArrayList();
				for (List<Float> vector : floatValues)
				{
					final Float label = vector.get(vector.size() - 1);
					vector.remove(vector.size() - 1);

					final boolean boolLabel = (label > 0);
					featureVectorList.add(new FeatureVector(vector, boolLabel));
				}

				floatValues.clear();

				featureVectorSetList.add(new FeatureVectorSet(child, className, extractorName, frameSize, id, featureVectorList));
				log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		log.info("All FeatureVectors added.");

		return featureVectorSetList;
	}

}
