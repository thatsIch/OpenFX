package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;
import de.thatsich.openfx.featureextraction.api.control.FeatureVector;
import de.thatsich.openfx.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.openfx.featureextraction.api.control.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.intern.services.CSVService;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.core.opencv.Images;
import javafx.collections.FXCollections;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.photo.Photo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ExtractFeatureVectorSetCommand extends ACommand<FeatureVectorSet>
{
	// Properties
	private final Path featureInputFolderPath;
	private final ErrorEntry errorEntry;
	private final IFeatureExtractor featureExtractor;
	private final int frameSize;
	private final boolean smooth;
	private final boolean threshold;
	private final boolean denoising;

	// Injects
	@Inject
	private CSVService csvService;

	@Inject
	public ExtractFeatureVectorSetCommand(@Assisted Path folderPath, @Assisted ErrorEntry errorEntry, @Assisted IFeatureExtractor extractor, @Assisted int frameSize, @Assisted("smooth") boolean smooth, @Assisted("threshold") boolean threshold, @Assisted("denoising") boolean denoising)
	{
		this.featureInputFolderPath = folderPath;
		this.errorEntry = errorEntry;
		this.featureExtractor = extractor;
		this.frameSize = frameSize;
		this.smooth = smooth;
		this.threshold = threshold;
		this.denoising = denoising;
	}

	@Override
	protected FeatureVectorSet call() throws Exception
	{
		final String className = this.errorEntry.getErrorClassProperty().get();
		final String extractorName = this.featureExtractor.getName();
		final String id = UUID.randomUUID().toString();
		final Mat originalWithErrorMat = this.errorEntry.getOriginalWithErrorMat().clone();
		log.info("Prepared all necessary information.");

		// TODO Implement Smooth and Threshold somehow Denoising
		// CvSmooth, CvThreshold
		if (this.smooth == this.threshold == this.denoising)
		{
			this.log.info("TODO");
		}

		if (this.denoising)
		{
			Photo.fastNlMeansDenoising(originalWithErrorMat, originalWithErrorMat);
		}

		final List<FeatureVector> featureVectorList = FXCollections.observableArrayList();
		final List<List<Float>> csvResult = FXCollections.observableArrayList();
		final Mat[][] originalErrorSplit = Images.split(originalWithErrorMat, this.frameSize, this.frameSize);
		final Mat[][] errorSplit = Images.split(this.errorEntry.getErrorMat(), this.frameSize, this.frameSize);
		log.info("Prepared split images.");

		for (int col = 0; col < originalErrorSplit.length; col++)
		{
			for (int row = 0; row < originalErrorSplit[col].length; row++)
			{
				try
				{
					// extract feature vector
					// and reshape them into a one row feature vector if its 2D
					// mat and removes unecessary channels
					final MatOfFloat featureVector = this.featureExtractor.extractFeature(originalErrorSplit[col][row]);
					List<Float> featureVectorAsList = new ArrayList<>(featureVector.toList());

					// if contain an error classify it as positive match
					if (Core.sumElems(errorSplit[col][row]).val[0] != 0)
					{
						featureVectorList.add(new FeatureVector(featureVectorAsList, true));
						featureVectorAsList.add(1F);
					}

					// else its a negative match
					else
					{
						featureVectorList.add(new FeatureVector(featureVectorAsList, false));
						featureVectorAsList.add(0F);
					}

					csvResult.add(featureVectorAsList);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		final Path filePath = this.featureInputFolderPath.resolve(className + "_" + extractorName + "_" + this.frameSize + "_" + id + ".csv");
		this.csvService.write(filePath, csvResult);

		this.log.info("Extracted FeatureVectors: " + featureVectorList.size());
		return new FeatureVectorSet(filePath, className, extractorName, this.frameSize, id, featureVectorList);
	}
}
