package de.thatsich.openfx.prediction.intern.control.command.service;

import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import de.thatsich.core.IFileStorageService;
import de.thatsich.openfx.prediction.api.control.BinaryPrediction;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BinaryPredictionFileStorageService implements IFileStorageService<BinaryPrediction>
{
	@Override
	public void save(BinaryPrediction prediction)
	{
		// Preprare each information
		final String file = prediction.getFilePathProperty().get().toAbsolutePath().toString();
		final Mat firstLayer = prediction.getWithErrorProperty().get();
		final Mat secondLayer = prediction.getErrorIndicationProperty().get();
		final Mat thirdLayer = prediction.getErrorPredictionProperty().get();

		// Merge every layer into a new Mat with 8UC3
		final List<Mat> listMat = Arrays.asList(firstLayer, secondLayer, thirdLayer);
		final Mat mergedMat = new Mat(firstLayer.size(), CvType.CV_8UC3);
		Core.merge(listMat, mergedMat);

		// Write to FS
		Highgui.imwrite(file, mergedMat);
	}

	@Override
	public BinaryPrediction load(Path filePath) throws WrongNumberArgsException
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
		if (fileNameSplit.length != 5)
		{
			throw new WrongNumberArgsException("Expected 5 encoded information but found " + fileNameSplit.length);
		}

		// prepare subinfo
		final String classificationName = fileNameSplit[0];
		final String extractorName = fileNameSplit[1];
		final int frameSize = Integer.parseInt(fileNameSplit[2]);
		final String errorName = fileNameSplit[3];
		final String id = fileNameSplit[4];

		return new BinaryPrediction(filePath, firstLayer, secondLayer, thirdLayer, classificationName, extractorName, frameSize, errorName, id);
	}
}