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
import java.nio.file.StandardOpenOption;
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
		final Path filePath = super.storagePath.resolve(config.toString());
		final BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.US_ASCII, StandardOpenOption.APPEND);
		final StringJoiner featureJoiner = new StringJoiner(System.lineSeparator());

		feature.vectors().forEach(vector -> {
			final StringJoiner vectorJoiner = new StringJoiner(",");
			vector.vector().forEach(f -> vectorJoiner.add(String.valueOf(f)));
			featureJoiner.merge(vectorJoiner);
		});
		writer.write(featureJoiner.toString());

		writer.close();
		this.log.info("Created Feature " + feature);

		return feature;
	}

	@Override
	public IFeature retrieve(final Path path) throws IOException
	{
		final String fileName = super.getFileNameWithoutExtension(path);
		final FeatureConfig config = new FeatureConfig(fileName);

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
		final Path filePath = super.storagePath.resolve(config.toString());

		Files.deleteIfExists(filePath);
		this.log.info("Deleted " + filePath);
	}
}