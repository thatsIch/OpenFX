package de.thatsich.openfx.prediction.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.openfx.prediction.intern.control.entity.BinaryPrediction;
import de.thatsich.openfx.prediction.intern.model.PredictionState;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class BinaryPredictionFileStorageService extends AFileStorageService<BinaryPrediction>
{
	@Inject
	protected BinaryPredictionFileStorageService(PredictionState state)
	{
		super(state.path().get());
	}

	@Override
	public BinaryPrediction create(BinaryPrediction prediction)
	{
		// Preprare each information
		final StringJoiner joiner = new StringJoiner("_");
		joiner.add(prediction.classifierName().get());
		joiner.add(prediction.extractorName().get());
		joiner.add(String.valueOf(prediction.frameSize().get()));
		joiner.add(prediction.errorClassName().get());
		joiner.add(prediction.id().get());
		final String fileName = joiner.toString();

		final String file = this.storagePath.resolve(fileName).toAbsolutePath().toString();
		final Mat firstLayer = prediction.modified().get();
		final Mat secondLayer = prediction.errorIndication().get();
		final Mat thirdLayer = prediction.errorPrediction().get();

		// Merge every layer into a new Mat with 8UC3
		final List<Mat> listMat = Arrays.asList(firstLayer, secondLayer, thirdLayer);
		final Mat mergedMat = new Mat(firstLayer.size(), CvType.CV_8UC3);
		Core.merge(listMat, mergedMat);

		// Write to FS
		Highgui.imwrite(file, mergedMat);

		return prediction;
	}

	@Override
	public BinaryPrediction retrieve(Path filePath)
	{
		final Mat layeredImage = Highgui.imread(filePath.toAbsolutePath().toString());

		// split channels to extract GL and Error Mat
		final List<Mat> layeredImageChannelMats = new LinkedList<>();
		Core.split(layeredImage, layeredImageChannelMats);

		final Mat firstLayer = layeredImageChannelMats.get(0);
		final Mat secondLayer = layeredImageChannelMats.get(1);
		final Mat thirdLayer = layeredImageChannelMats.get(2);

		// extract file information
		final String fileName = filePath.getFileName().toString();
		final String[] fileNameSplit = fileName.split("_");

		// prepare subinfo
		final String classificationName = fileNameSplit[0];
		final String extractorName = fileNameSplit[1];
		final int frameSize = Integer.parseInt(fileNameSplit[2]);
		final String errorName = fileNameSplit[3];
		final String id = fileNameSplit[4];

		return new BinaryPrediction(firstLayer, secondLayer, thirdLayer, classificationName, extractorName, frameSize, errorName, id);
	}

	@Override
	public BinaryPrediction update(final BinaryPrediction elem) throws IOException
	{
		return elem;
	}

	@Override
	public void delete(final BinaryPrediction prediction) throws IOException
	{
		final StringJoiner joiner = new StringJoiner("_");
		joiner.add(prediction.classifierName().get());
		joiner.add(prediction.extractorName().get());
		joiner.add(String.valueOf(prediction.frameSize().get()));
		joiner.add(prediction.errorClassName().get());
		joiner.add(prediction.id().get());
		final String fileName = joiner.toString();

		final Path toBeDeletedFilePath = this.storagePath.resolve(fileName);
		if (Files.exists(toBeDeletedFilePath))
		{
			Files.delete(toBeDeletedFilePath);
			this.log.info(toBeDeletedFilePath + " deleted.");
		}
		else
		{
			this.log.warning("Could not delete " + toBeDeletedFilePath + " because it was not found.");
		}
	}
}
