package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureFileStorageService;
import de.thatsich.openfx.featureextraction.intern.control.entity.Feature;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureConfig;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;
import javafx.collections.FXCollections;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import java.util.List;


public class ExtractFeatureCommand extends ACommand<IFeature>
{
	// Properties
	private final IError error;
	private final IFeatureExtractor featureExtractor;
	private final int frameSize;
	private final boolean smooth;
	private final boolean threshold;
	private final boolean denoising;
	private final FeatureFileStorageService storage;

	@Inject
	public ExtractFeatureCommand(@Assisted IError error, @Assisted IFeatureExtractor extractor, @Assisted int frameSize, @Assisted("smooth") boolean smooth, @Assisted("threshold") boolean threshold, @Assisted("denoising") boolean denoising, FeatureFileStorageService storage)
	{
		this.error = error;
		this.featureExtractor = extractor;
		this.frameSize = frameSize;
		this.smooth = smooth;
		this.threshold = threshold;
		this.denoising = denoising;
		this.storage = storage;
	}

	@Override
	protected IFeature call() throws Exception
	{
		final String className = this.error.clazzProperty().get();
		final String extractorName = this.featureExtractor.getName();
		final Mat modified = this.error.modifiedProperty().get().clone();
		this.log.info("Prepared all necessary information + " + className + ", " + extractorName);

		// CvSmooth, CvThreshold
		if (this.smooth)
		{
			Imgproc.adaptiveBilateralFilter(modified, modified, new Size(31, 31), 62);
		}

		if (this.threshold)
		{
			Imgproc.threshold(modified, modified, 0, 255, Imgproc.THRESH_OTSU);
			//			Imgproc.adaptiveThreshold(modified, modified, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 15, -5);
		}

		if (this.denoising)
		{
			Photo.fastNlMeansDenoising(modified, modified);
		}

		final List<IFeatureVector> featureVectorList = FXCollections.observableArrayList();
		final Mat[][] originalErrorSplit = Images.split(modified, this.frameSize, this.frameSize);
		final Mat[][] errorSplit = Images.split(this.error.errorProperty().get(), this.frameSize, this.frameSize);
		this.log.info("Prepared split images.");

		for (int col = 0; col < originalErrorSplit.length; col++)
		{
			for (int row = 0; row < originalErrorSplit[col].length; row++)
			{
				try
				{
					final MatOfFloat featureVector = this.featureExtractor.extractFeature(originalErrorSplit[col][row]);
					List<Float> featureVectorAsList = featureVector.toList();

					final boolean isPositive = Core.sumElems(errorSplit[col][row]).val[0] != 0;
					featureVectorList.add(new FeatureVector(featureVectorAsList, isPositive));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		final FeatureConfig config = new FeatureConfig(className, extractorName, this.frameSize);
		final IFeature feature = new Feature(config, featureVectorList);

		this.storage.create(feature);
		this.log.info("Extracted FeatureVectors: " + featureVectorList.size());

		return feature;
	}
}
