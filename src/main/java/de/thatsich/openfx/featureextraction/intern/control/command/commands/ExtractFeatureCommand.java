package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureStorageService;
import de.thatsich.openfx.featureextraction.intern.control.entity.Feature;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;
import javafx.collections.FXCollections;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.photo.Photo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class ExtractFeatureCommand extends ACommand<IFeature>
{
	// Properties
	private final Path path;
	private final IError error;
	private final IFeatureExtractor featureExtractor;
	private final int frameSize;
	private final boolean smooth;
	private final boolean threshold;
	private final boolean denoising;

	// Injects
	@Inject private FeatureStorageService storage;

	@Inject
	public ExtractFeatureCommand(@Assisted Path folderPath, @Assisted IError error, @Assisted IFeatureExtractor extractor, @Assisted int frameSize, @Assisted("smooth") boolean smooth, @Assisted("threshold") boolean threshold, @Assisted("denoising") boolean denoising)
	{
		this.path = folderPath;
		this.error = error;
		this.featureExtractor = extractor;
		this.frameSize = frameSize;
		this.smooth = smooth;
		this.threshold = threshold;
		this.denoising = denoising;
	}

	@Override
	protected IFeature call() throws Exception
	{
		final String className = this.error.getClazz();
		final String extractorName = this.featureExtractor.getName();
		final Mat originalWithErrorMat = this.error.getModified().clone();
		this.log.info("Prepared all necessary information + " + className + ", " + extractorName);

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

		final List<IFeatureVector> featureVectorList = FXCollections.observableArrayList();
		final List<List<Float>> csvResult = FXCollections.observableArrayList();
		final Mat[][] originalErrorSplit = Images.split(originalWithErrorMat, this.frameSize, this.frameSize);
		final Mat[][] errorSplit = Images.split(this.error.getError(), this.frameSize, this.frameSize);
		this.log.info("Prepared split images.");

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

		final Path filePath = this.path.resolve(className + "_" + extractorName + "_" + this.frameSize + ".csv");
		final IFeature feature = new Feature(filePath, className, extractorName, this.frameSize, featureVectorList);

		this.storage.save(feature);
		this.log.info("Extracted FeatureVectors: " + featureVectorList.size());

		return feature;
	}
}
