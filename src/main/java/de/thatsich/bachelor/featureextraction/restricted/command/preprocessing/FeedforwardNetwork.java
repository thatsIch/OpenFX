package de.thatsich.bachelor.featureextraction.restricted.command.preprocessing;

import java.util.List;

/**
 * Java implementation of a feedforward neural network abstract base class.
 *
 * The network has 3 layers - input, hidden, output - and 2 synaptic
 * weight matrices.  An input vector is presented to the input layer,
 * and computation flows forward to the hidden layer, and then to the
 * output layer.  Child classes must implement train().
 *
 * There is no warranty of any kind on this code, not even the implied
 * warranty of merchantability or fitness for a particular purpose.
 * This code is intended for personal and educational purposes only.
 *
 * @author Liquid Self
 */
abstract class FeedforwardNetwork {
	// --- network parameters ---

	/** number of passes through training set */
	protected int numberOfEpochs_ = 100;

	/** allowable tolerance for output value (used in testing) */
	protected double tolerance_ = 0.1;

	// --- statistics ---

	protected double sumSquaredError_ = 0.0;

	// --- the network itself ---

	/** the input layer */
	protected NeuralLayer inputLayer_;

	/** the hidden layer */
	protected List<NeuralLayer> hiddenLayer_;

	/** the output layer */
	protected NeuralLayer outputLayer_;

	/** the synaptic weight matrix */
	protected List<SynapticMatrix> synapses_;

	// --------------------------------------------------------------
	// Public Constructors
	// --------------------------------------------------------------

	/**
	 * Creates a new <code>FeedforwardNetwork</code> instance.
	 */
	public FeedforwardNetwork(int inputSize, int outputSize, int... hiddenSize)
	{
		this.inputLayer_ = new NeuralLayer(inputSize);
		NeuralLayer previousLayer = this.inputLayer_;
		for (int size : hiddenSize) {
			final NeuralLayer newHiddenLayer = new NeuralLayer(size);
			final SynapticMatrix synapse = new SynapticMatrix(previousLayer.getSize(), newHiddenLayer.getSize());
			synapse.initializeWeightsRandom(1.0, -0.5);
			
			this.hiddenLayer_.add(newHiddenLayer);
			this.synapses_.add(synapse);
			previousLayer = newHiddenLayer;
		}
		
		this.outputLayer_ = new NeuralLayer(outputSize);
		final SynapticMatrix synapse = new SynapticMatrix(previousLayer.getSize(), this.outputLayer_.getSize());
		synapse.initializeWeightsRandom(1.0, -0.5);
	}

	// --------------------------------------------------------------
	// Public Methods
	// --------------------------------------------------------------

	/**
	 * Sets the number of epochs.
	 *
	 * @param numberOfEpochs The number of epochs
	 */
	public void setNumberOfEpochs(int numberOfEpochs)
	{
		numberOfEpochs_ = numberOfEpochs;
	}

	/**
	 * Sets the tolerance.
	 *
	 * @param tolerance The tolerance
	 */
	public void setTolerance(int tolerance)
	{
		tolerance_ = tolerance;
	}

	/**
	 * Prints the values of the synaptic weight matrices.
	 */
	public void printSynapticMatrices()
	{
		for (SynapticMatrix matrix : this.synapses_) {
			matrix.printWeights();
		}
	}

	/**
	 * Abstract function for training the network.
	 *
	 * @param trainingSet The training set
	 */
	public abstract void train(Dataset trainingSet);

	/**
	 * Tests the network.
	 *
	 * @param testingSet The testing set
	 */
	public void test(Dataset testingSet)
	{
		int numberCorrect = 0;
		int numberOfTestingPairs = testingSet.getSize();
		for (int pair = 0; pair < numberOfTestingPairs; pair++){

			// propagate input vector from input layer to output layer
			//   and determine if output is correct
			if (feedForward(testingSet.getElement(pair)) == true){
				numberCorrect++;
			}
		}

		// output accuracy
		System.out.println("Accuracy = "+
				((new Double(numberCorrect).doubleValue()) /
						(new Double(numberOfTestingPairs).doubleValue())) * 100.00 + "%");
	}

	// --------------------------------------------------------------
	// Protected Methods
	// --------------------------------------------------------------

	/**
	 * Given an input-output vector pair, present the input vector
	 * to the network, propagate forward to the hidden and output layers,
	 * compute activations at the output layer and evaluate the accuracy
	 * of computed output relative to the output vector.
	 *
	 * @param vectorPair The input-output vector pair
	 *
	 * @return True if all elements of the network's output vector is
	 *         are correct within a specified tolerance; false otherwise
	 */
	protected boolean feedForward(DatasetElement vectorPair)
	{
		boolean isOutputCorrect = true;
		double[] inputVector = vectorPair.getInputVector();
		double[] outputVector = vectorPair.getOutputVector();
		int inputSize = inputLayer_.getSize();
		int outputSize = outputLayer_.getSize();

		// apply the input vector to the input layer
		for (int input = 0; input < inputSize; input++){
			inputLayer_.getNeuron(input).setActivation(inputVector[input]);
		}

		// propagate input layer values forward and generate
		//   activation values at the hidden layer

		// for each neuron in the each hidden layer...
		for (int layerIndex = 0; layerIndex < this.hiddenLayer_.size(); layerIndex++) {
			final NeuralLayer hiddenLayer = this.hiddenLayer_.get(layerIndex);
			final SynapticMatrix synapseToCurrentLayer = this.synapses_.get(layerIndex);
			
			for (int hidden = 0; hidden < hiddenLayer.getSize(); hidden++){
				double weightedSum = 0.0;
				final Neuron hiddenNeuron = hiddenLayer.getNeuron(hidden);
				// add the weighted inputs from all input layer neurons
				for (int input = 0; input < inputSize; input++){
					weightedSum += inputLayer_.getNeuron(input).getActivation() *
							synapseToCurrentLayer.getSynapse(input, hidden).getWeight() +
							hiddenNeuron.getBias();
				}
				// and pass the sum through the threshold function
				hiddenNeuron.computeActivation(weightedSum);
			}
		}
		

		// propagate hidden layer values forward and generate
		//   activation values at the output layer

		// for each neuron in the output layer...
		for (int output = 0; output < outputSize; output++){
			double weightedSum = 0.0;
			Neuron outputNeuron = outputLayer_.getNeuron(output);
			// add the weighted inputs from all hidden layer neurons
			
			final NeuralLayer lastHiddenLayer = this.hiddenLayer_.get(this.hiddenLayer_.size() - 1);
			final SynapticMatrix lastSynapse = this.synapses_.get(this.synapses_.size() - 1);
			for (int hidden = 0; hidden < lastHiddenLayer.getSize(); hidden++){
				weightedSum +=
						lastHiddenLayer.getNeuron(hidden).getActivation() *
						lastSynapse.getSynapse(hidden, output).getWeight() +
						outputNeuron.getBias();
			}

			// and pass the sum through the threshold function
			outputLayer_.getNeuron(output).computeActivation(weightedSum);

			// compute statistics

			double outputError = Math.abs(outputVector[output] -
					outputLayer_.getNeuron(output).getActivation());
			if (outputError > tolerance_){
				isOutputCorrect = false;
			} 
			sumSquaredError_ += Math.pow(outputError, 2);
		}
		return isOutputCorrect;
	}
}
