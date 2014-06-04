package de.thatsich.openfx.featureextraction.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.api.model.IFeatureState;
import de.thatsich.openfx.featureextraction.intern.control.entity.Feature;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public class FeatureStorageService extends AFileStorageService<IFeature>
{
	@Inject
	protected FeatureStorageService(final IFeatureState state)
	{
		super(state.path().get());
	}

	@Override
	public void save(final IFeature elem) throws IOException
	{
		final BufferedWriter writer = Files.newBufferedWriter(elem.path(), StandardCharsets.US_ASCII, StandardOpenOption.APPEND);
		final List<IFeatureVector> featureVectors = elem.vectors();

		for (int fvIndex = 0; fvIndex < featureVectors.size(); fvIndex++)
		{
			final IFeatureVector fv = featureVectors.get(fvIndex);
			final List<Float> floats = fv.vector();
			for (int floatIndex = 0; floatIndex < floats.size(); floatIndex++)
			{
				writer.write(String.valueOf(floats.get(floatIndex)));

				if (floatIndex < floats.size() - 1)
				{
					writer.write(",");
				}
			}

			if (fvIndex < featureVectors.size() - 1)
			{
				writer.newLine();
			}
		}
		writer.close();
	}

	@Override
	public IFeature load(final Path path) throws IOException
	{
		System.out.println("LOAD");
		// extract file information
		final String fileName = path.getFileName().toString();
		final String[] fileNameSplit = fileName.split("_");

		// prepare subinfo
		final String className = fileNameSplit[0];
		final String extractorName = fileNameSplit[1];
		final int frameSize = Integer.parseInt(fileNameSplit[2]);

		final List<IFeatureVector> featureVectors = new LinkedList<>();
		final BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.US_ASCII);

		String stringRow;
		while ((stringRow = reader.readLine()) != null)
		{
			final List<String> stringFloats = Arrays.asList(stringRow.split(","));
			final List<Float> floats = stringFloats.stream().map(Float::parseFloat).collect(Collectors.toCollection(LinkedList::new));
			final boolean isPositive = floats.remove(floats.size() - 1) == 1;
			featureVectors.add(new FeatureVector(floats, isPositive));
		}
		reader.close();

		return new Feature(path, className, extractorName, frameSize, featureVectors);

	}

	@Override
	public void update(final IFeature elem) throws IOException
	{

	}

	@Override
	public void delete(final IFeature elem) throws IOException
	{

	}
}
