package de.thatsich.openfx.featureextraction.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.api.model.IFeatureState;
import de.thatsich.openfx.featureextraction.intern.control.entity.Feature;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureConfig;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public class FeatureFileStorageService extends AFileStorageService<IFeature>
{
	@Inject
	protected FeatureFileStorageService(final IFeatureState state)
	{
		super(state.path().get());
	}

	@Override
	public IFeature create(final IFeature feature) throws IOException
	{
		final FeatureConfig config = feature.getConfig();
		final String fileName = config.toString();
		final Path filePath = super.storagePath.resolve(fileName);

		this.createInvalidDirectory(filePath);

		final Path vectorPath = filePath.resolve("vector.csv");
		final Path labelPath = filePath.resolve("lable.csv");

		try (
			final BufferedWriter vectorWriter = Files.newBufferedWriter(vectorPath, StandardCharsets.US_ASCII);
			final BufferedWriter labelWriter = Files.newBufferedWriter(labelPath, StandardCharsets.US_ASCII)
		)
		{
			final StringJoiner vectorJoiner = new StringJoiner(System.lineSeparator());
			final StringJoiner labelJoiner = new StringJoiner(System.lineSeparator());

			feature.vectors().forEach(vector -> {
				final StringJoiner floatsJoiner = new StringJoiner(",");
				vector.vector().forEach(f -> floatsJoiner.add(String.valueOf(f)));

				vectorJoiner.merge(floatsJoiner);
				labelJoiner.add(String.valueOf(vector.isPositive().get()));
			});

			vectorWriter.write(vectorJoiner.toString());
			labelWriter.write(labelJoiner.toString());
		}

		this.log.info("Created Feature " + feature);

		return feature;
	}

	@Override
	public IFeature retrieve(final Path path) throws IOException
	{
		final String fileName = path.getFileName().toString();
		final FeatureConfig config = new FeatureConfig(fileName);

		final Path vectorPath = path.resolve("vector.csv");
		final Path labelPath = path.resolve("label.csv");

		final List<IFeatureVector> featureVectors = new LinkedList<>();

		try (
			final BufferedReader vectorReader = Files.newBufferedReader(vectorPath, StandardCharsets.US_ASCII);
			final BufferedReader labelReader = Files.newBufferedReader(labelPath, StandardCharsets.US_ASCII)
		)
		{
			String vectorRow;
			String labelRow;

			while ((vectorRow = vectorReader.readLine()) != null && (labelRow = labelReader.readLine()) != null)
			{
				final List<String> stringFloats = Arrays.asList(vectorRow.split(","));
				final List<Float> floats = stringFloats.stream().map(Float::parseFloat).collect(Collectors.toCollection(LinkedList::new));
				final boolean isPositive = Boolean.parseBoolean(labelRow);

				featureVectors.add(new FeatureVector(floats, isPositive));
			}
		}

		final Feature feature = new Feature(config, featureVectors);
		this.log.info("Retrieved Feature " + feature);

		return feature;
	}

	@Override
	public IFeature update(final IFeature feature) throws IOException
	{

		this.log.info("Updated Feature " + feature);

		return feature;
	}

	@Override
	public void delete(final IFeature feature) throws IOException
	{
		final FeatureConfig config = feature.getConfig();
		final String pathName = config.toString();
		final Path filePath = super.storagePath.resolve(pathName);

		this.deleteRecursively(filePath);
		this.log.info("Deleted " + filePath);
	}
}
