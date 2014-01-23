package de.thatsich.bachelor.featureextraction.restricted.command.preprocessing;

/**
 * Java implementation of a resilient propagation (RProp) neural
 * network.
 * 
 * Riedmiller, M. (1994). "RProp - Description and Implementation Details."
 * Technical Report W-76128, University of Karlsruhe.
 * 
 * The network is a 3-layer feedforward network. Synaptic weights are
 * updated after each epoch: if the partial derivative of error with
 * respect to weight changes sign from the previous epoch, the weight
 * change is decreased by a learning rate factor; if the partial derivative
 * retains its sign, the weight change is increased by a separate learning
 * rate factor.
 * 
 * NOTE: RProp is not converging beyond 65% accuracy on the training data
 * used. Backpropagation was able to acheive 90%+ accuracy on this same
 * data using both vanilla and epoch-based training. (Needless to say I
 * am not impressed with RProp.)
 * 
 * There is no warranty of any kind on this code, not even the implied
 * warranty of merchantability or fitness for a particular purpose.
 * This code is intended for personal and educational purposes only.
 * 
 * @author Liquid Self
 */

class RPropNetwork extends FeedforwardNetwork
{

	public RPropNetwork( int inputSize, int outputSize, int[] hiddenSize )
	{
		super( inputSize, outputSize, hiddenSize );
	}

	@Override
	public void train( Dataset trainingSet )
	{
	}
//
//	// public RPropNetwork(int inputSize, int outputSize, int... hiddenSize) {
//	// super(inputSize, outputSize, hiddenSize);
//	// }
//	/** increase learning rate */
//	private double					increaseLearningRate_	= 1.2;
//
//	/** decrease learning rate */
//	private double					decreaseLearningRate_	= 0.5;
//
//	/** update-values for input-to-hidden synapses */
//	private RPropUpdateValue[][]	inputToHiddenUpdateValues_;
//
//	/** update-values for hidden-to-output synapses */
//	private RPropUpdateValue[][]	hiddenToOutputUpdateValues_;
//
//	// --------------------------------------------------------------
//	// Public Constructors
//	// --------------------------------------------------------------
//
//	/**
//	 * Creates a new <code>RPropNetwork</code> instance.
//	 */
//	public RPropNetwork( int inputSize, int outputSize, int... hiddens )
//	{
//		super( inputSize, outputSize, hiddens );
//		final int hiddenSize = hiddens.length;
//
//		// initialize input-to-hidden update-value matrix
//		inputToHiddenUpdateValues_ = new RPropUpdateValue[ inputSize ][ hiddenSize ];
//		for ( int input = 0; input < inputSize; input++ )
//		{
//			inputToHiddenUpdateValues_[input] = new RPropUpdateValue[ hiddenSize ];
//			for ( int hidden = 0; hidden < hiddenSize; hidden++ )
//			{
//				inputToHiddenUpdateValues_[input][hidden] = new RPropUpdateValue();
//			}
//		}
//
//		// initialize hidden-to-output update-value matrix
//		hiddenToOutputUpdateValues_ = new RPropUpdateValue[ hiddenSize ][ outputSize ];
//		for ( int hidden = 0; hidden < hiddenSize; hidden++ )
//		{
//			hiddenToOutputUpdateValues_[hidden] = new RPropUpdateValue[ outputSize ];
//			for ( int output = 0; output < outputSize; output++ )
//			{
//				hiddenToOutputUpdateValues_[hidden][output] = new RPropUpdateValue();
//			}
//		}
//	}
//
//	// --------------------------------------------------------------
//	// Public Methods
//	// --------------------------------------------------------------
//
//	/**
//	 * Trains the network using resilient propagation.
//	 * 
//	 * @param trainingSet
//	 *            The training set
//	 */
//	public void train( Dataset trainingSet )
//	{
//		// uses epoch-based training, so error for each weight will be
//		// accumulated and weights will be changed after each pass through
//		// the training pairs
//
//		int inputSize = inputLayer_.getSize();
//		int hiddenSize = hiddenLayer_.getSize();
//		int outputSize = outputLayer_.getSize();
//
//		// trainingSet.setShuffle(true);
//		double meanSquaredError = 0.0;
//
//		// for a specified number of training epochs...
//		for ( int epochs = 0; epochs < numberOfEpochs_; epochs++ )
//		{
//			sumSquaredError_ = 0.0;
//			meanSquaredError = 0.0;
//
//			inputToHiddenSynapses_.resetErrors();
//			hiddenToOutputSynapses_.resetErrors();
//
//			// and for each input-output vector pair in the training set...
//			int numberOfTrainingPairs = trainingSet.getSize();
//			for ( int pair = 0; pair < numberOfTrainingPairs; pair++ )
//			{
//
//				// propagate input vector from input layer to output layer
//				// and compute error at output layer
//				feedForward( trainingSet.getElement( pair ) );
//
//				// for each hidden-to-output synapse, compute partial
//				// derivative of error with respect to weight
//
//				double[] outputVector = trainingSet.getElement( pair ).getOutputVector();
//				for ( int output = 0; output < outputSize; output++ )
//				{
//					double outputActivation = outputLayer_.getNeuron( output ).getActivation();
//					for ( int hidden = 0; hidden < hiddenSize; hidden++ )
//					{
//						hiddenToOutputSynapses_.getSynapse( hidden, output ).addError( ( outputVector[output] - outputActivation ) * outputActivation * ( 1.0 - outputActivation ) * hiddenLayer_.getNeuron( hidden ).getActivation() );
//					}
//				}
//
//				// for each input-to-hidden synapse, compute partial
//				// derivative of error with respect to weight
//
//				for ( int hidden = 0; hidden < hiddenSize; hidden++ )
//				{
//
//					// accumulate output layer error for this
//					// hidden neuron
//
//					double errorFromOutputLayer = 0.0;
//					double hiddenActivation = hiddenLayer_.getNeuron( hidden ).getActivation();
//					for ( int output = 0; output < outputSize; output++ )
//					{
//						double outputActivation = outputLayer_.getNeuron( output ).getActivation();
//						errorFromOutputLayer += ( outputVector[output] - outputActivation ) * outputActivation * ( 1.0 - outputActivation ) * hiddenToOutputSynapses_.getSynapse( hidden, output ).getWeight();
//					}
//
//					// now update the error for this input-to-hidden synapse
//
//					for ( int input = 0; input < inputSize; input++ )
//					{
//						inputToHiddenSynapses_.getSynapse( input, hidden ).addError( hiddenActivation * ( 1.0 - hiddenActivation ) * errorFromOutputLayer * inputLayer_.getNeuron( input ).getActivation() );
//					}
//				}
//			}
//
//			// update the weight matrices
//			updateWeights( hiddenToOutputSynapses_, hiddenToOutputUpdateValues_ );
//			updateWeights( inputToHiddenSynapses_, inputToHiddenUpdateValues_ );
//
//			// output statistics
//			meanSquaredError = sumSquaredError_ / ( outputSize * numberOfTrainingPairs * 2 );
//			System.out.println( "MSE: " + meanSquaredError );
//		}
//	}
//
//	// --------------------------------------------------------------
//	// Private Methods
//	// --------------------------------------------------------------
//
//	/**
//	 * Helper method to update synaptic weights.
//	 * 
//	 * @param synapses
//	 *            The synaptic weight matrix to update
//	 * @param updateValues
//	 *            The matrix of update-values corresponding to
//	 *            the synaptic weights
//	 */
//	private void updateWeights( SynapticMatrix synapses, RPropUpdateValue[][] updateValues )
//	{
//		int numberOfRows = synapses.getNumberOfRows();
//		int numberOfColumns = synapses.getNumberOfColumns();
//
//		for ( int column = 0; column < numberOfColumns; column++ )
//		{
//			for ( int row = 0; row < numberOfRows; row++ )
//			{
//				Synapse synapse = synapses.getSynapse( row, column );
//				RPropUpdateValue updateValue = updateValues[row][column];
//
//				// compute product of accumulated error for the weight
//				// for this epoch (t) and the last epoch (t-1)
//				double product = synapse.getError() * synapse.getPreviousError();
//
//				if ( product > 0 )
//				{ // no sign change
//					updateValue.update( increaseLearningRate_ );
//
//					// modify the weight
//
//					double error = synapse.getError();
//					if ( error > 0 )
//					{
//						synapse.addWeight( -1.0 * updateValue.getValue() );
//					}
//					else if ( error < 0 )
//					{
//						synapse.addWeight( updateValue.getValue() );
//					}
//					else
//					{
//						// do nothing
//					}
//					synapse.setPreviousError( error );
//				}
//				else if ( product < 0 )
//				{ // sign change
//					updateValue.update( decreaseLearningRate_ );
//
//					// don't update update-value after next epoch
//					synapse.setPreviousError( 0.0 );
//				}
//				else
//				{
//					double error = synapse.getError();
//					if ( error > 0 )
//					{
//						synapse.addWeight( -1.0 * updateValue.getValue() );
//					}
//					else if ( error < 0 )
//					{
//						synapse.addWeight( updateValue.getValue() );
//					}
//					else
//					{
//						// do nothing
//					}
//					synapse.setPreviousError( error );
//				}
//			}
//		}
//	}
//
//	// --------------------------------------------------------------
//	// Main Method
//	// --------------------------------------------------------------
//
//	/**
//	 * Main method to create, train, and test the network.
//	 * 
//	 * @param args
//	 *            a <code>String[]</code> value
//	 */
//	public static void main( String[] args )
//	{
//		// define size of input and output vectors
//		int inputSize = 8;
//		int outputSize = 1;
//
//		// using defaults for all other parameters
//
//		// define training set
//		int numberOfTrainingPairs = 300;
//		Dataset trainingSet = new Dataset( numberOfTrainingPairs, inputSize, outputSize );
//		trainingSet.readFile( "../datasets/training.txt" );
//
//		// define testing set
//		int numberOfTestingPairs = 300;
//		Dataset testingSet = new Dataset( numberOfTestingPairs, inputSize, outputSize );
//		testingSet.readFile( "../datasets/testing.txt" );
//
//		// instantiate the network
//		RPropNetwork r = new RPropNetwork( inputSize, 10 * inputSize, outputSize );
//		r.setNumberOfEpochs( 50 );
//
//		// train the network
//		r.train( trainingSet );
//
//		// test the network
//		r.test( testingSet );
//	}
}
