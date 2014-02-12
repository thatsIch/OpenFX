package de.thatsich.bachelor.classification.intern.command.classifier;

import org.opencv.core.MatOfFloat;
import org.opencv.utils.Converters;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import de.thatsich.bachelor.classification.intern.command.classifier.core.BinaryClassifierConfiguration;
import de.thatsich.bachelor.classification.intern.command.preprocessing.AANNPreProcessor;
import de.thatsich.bachelor.classification.intern.command.preprocessing.core.IPreProcessing;
import de.thatsich.bachelor.classification.intern.command.provider.IPreProcessingProvider;


public class AANNSVMBinaryClassifier extends SVMBinaryClassifier
{
	@Inject
	private IPreProcessingProvider	preProvider;

	@Override
	public IBinaryClassification train( MatOfFloat positiveTrainData, MatOfFloat negativeTrainData, BinaryClassifierConfiguration config )
	{
		final AANNPreProcessor aann = this.preProvider.createAANNPreProcessor();
		
		// convert train data for aann
		for ( int featureVectorIndex = 0; featureVectorIndex < positiveTrainData.rows(); featureVectorIndex++ )
		{
			for ( int elem = 0; elem < positiveTrainData.cols(); elem++) {
				// TODO	
			}
		}
		
		// train AANN TODO 
		final IPreProcessing preprocessing = aann.train( null, null );
		Converters.Mat_to_vector_float( positiveTrainData, null );
		
		// convert train data into preprocessed one
		final double[] preprocessedPositiveTrainData = preprocessing.preprocess( null );
//		positiveTrainData
		
		
		// train SVM with preprocessed trained data

		return super.train( positiveTrainData, negativeTrainData, config );
	}
}
