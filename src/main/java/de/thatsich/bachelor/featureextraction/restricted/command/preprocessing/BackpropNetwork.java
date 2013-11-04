package de.thatsich.bachelor.featureextraction.restricted.command.preprocessing;

/**
 * <p>Java implementation of a backpropagation neural network.</p>
 *
 * <p>Rumelhart, D.E., Hinton, G.E., Williams, R.J. (1986) "Learning internal
 * representations by error propagation." In: Rumelhart, D.E., McClelland,
 * J.L., & the PDP Research Group (Eds.), Parallel distributed processing:
 * explorations in the microstructure of cognition, vol. 1: Foundations.
 * Cambridge: MIT Press, pp. 318-362.</p>
 *
 * <p>Epoch-based training, and weight decay variations:
 * Hinton, G.E. (1986). "Learning distributed representations of concepts."
 * In Program of the 8th annual conference of the cognitive science society
 * (pp. 1-12).</p>
 * 
 * <p>The network has 3 layers - input, hidden, output - and 2 synaptic
 * weight matrices.  An input vector is presented to the input layer,
 * and computation flows forward to the hidden layer, and then to the
 * output layer.  For each input vector, the error of the computed
 * output vector is backpropagated to the input layer, with synaptic
 * weights adjusted using gradient descent.  This implementation includes
 * a momentum term to prevent convergence on local minima.</p>
 * 
 * <p>There is no warranty of any kind on this code, not even the implied
 * warranty of merchantability or fitness for a particular purpose.
 * This code is intended for personal and educational purposes only.</p>
 *
 * @author Liquid Self
 */

public class BackpropNetwork extends FeedforwardNetwork
{
    /** learning rate */
    private double learningRate_ = 0.2;

    /** momentum */
    private double momentum_ = 0.7;

    /** weight decay - percentage of weight that is subtracted */
    private double weightDecay_ = 0.0;

    /**
     * flag designating use of epoch-based training:
     * if true, weights are updated after each epoch
     * (i.e., after each pass through the training set
     */
    private boolean isEpochBased_ = false;

    // --------------------------------------------------------------
    // Public Constructors
    // --------------------------------------------------------------

    /**
     * Creates a new <code>BackpropNetwork</code> instance.
     */
    public BackpropNetwork(int inputSize, int hiddenSize, int outputSize)
    {
        super(inputSize, hiddenSize, outputSize);
    }

    public void setLearningRate(double learningRate)
    {
        learningRate_ = learningRate;
    }

    public void setMomentum(double momentum)
    {
        momentum_ = momentum;
    }

    public void setEpochBased(boolean isEpochBased)
    {
        isEpochBased_ = isEpochBased;
    }

    // --------------------------------------------------------------
    // Public Methods
    // --------------------------------------------------------------

    /**
     * Trains the network using backpropagation with momentum.
     *
     * @param trainingSet The training set
     */
    public void train(Dataset trainingSet)
    {
        int inputSize = inputLayer_.getSize();
        int hiddenSize = hiddenLayer_.getSize();
        int outputSize = outputLayer_.getSize();

        //trainingSet.setShuffle(true);
        double meanSquaredError = 0.0;

        // for a specified number of training epochs...
        for (int epochs = 0; epochs < numberOfEpochs_; epochs++){
            sumSquaredError_ = 0.0;
            meanSquaredError = 0.0;

            inputToHiddenSynapses_.resetErrors();
            hiddenToOutputSynapses_.resetErrors();

            // and for each input-output vector pair in the training set...
            int numberOfTrainingPairs = trainingSet.getSize();
            for (int pair = 0; pair < numberOfTrainingPairs; pair++){

                // propagate input vector from input layer to output layer
                //   and compute error at output layer
                feedForward(trainingSet.getElement(pair));

                // for each hidden-to-output synapse, compute partial
                //   derivative of error with respect to weight

                double[] outputVector = 
                  trainingSet.getElement(pair).getOutputVector();
                for (int output = 0; output < outputSize; output++){

                    double outputActivation = 
                      outputLayer_.getNeuron(output).getActivation();

                    for (int hidden = 0; hidden < hiddenSize; hidden++){

                        // compute partial derivative of error 
                        double error =
                          (outputVector[output] - outputActivation) *
                          outputActivation *
                          (1.0 - outputActivation) *
                          hiddenLayer_.getNeuron(hidden).getActivation();

                        Synapse synapse = 
                          hiddenToOutputSynapses_.getSynapse(hidden, output);

                        if (isEpochBased_){ 
                              // accumulate error until end of this epoch
                              synapse.addError(error);
                        }
                        else{
                              // update weight immediately
                              synapse.setError(error);
                              updateWeight(synapse);
                        }
                    }
                }

                // for each input-to-hidden synapse, compute partial
                //   derivative of error with respect to weight

                for (int hidden = 0; hidden < hiddenSize; hidden++){

                    // accumulate output layer error for this
                    //   hidden neuron

                    double errorFromOutputLayer = 0.0;
                    double hiddenActivation = 
                      hiddenLayer_.getNeuron(hidden).getActivation();
                    for (int output = 0; output < outputSize; output++){
                        double outputActivation =
                          outputLayer_.getNeuron(output).getActivation(); 
                        errorFromOutputLayer += 
                          (outputVector[output] - outputActivation) *
                          outputActivation *
                          (1.0 - outputActivation) * 
                          hiddenToOutputSynapses_.getSynapse(hidden, output).
                            getWeight();
                    }

                    // now update the error for this input-to-hidden synapse

                    for (int input = 0; input < inputSize; input++){

                        // compute partial derivative of error
                        double error =
                          hiddenActivation *
                          (1.0 - hiddenActivation) *
                          errorFromOutputLayer *
                          inputLayer_.getNeuron(input).getActivation();

                        Synapse synapse =
                          inputToHiddenSynapses_.getSynapse(input, hidden);

                        if (isEpochBased_){
                            // accumulate error until end of this epoch
                            synapse.addError(error);
                        }
                        else{
                            // update weight immediately
                            synapse.setError(error);
                            updateWeight(synapse);
                        }
                    }
                }
            }

            if (isEpochBased_){
                // update the weight matrices
                updateWeights(hiddenToOutputSynapses_);
                updateWeights(inputToHiddenSynapses_);
            }

            // output statistics
            meanSquaredError = sumSquaredError_ /
              (outputSize * numberOfTrainingPairs * 2);
            System.out.println("MSE: " + meanSquaredError);
        }
    }

    // --------------------------------------------------------------
    // Private Methods
    // --------------------------------------------------------------

    /**
     * Helper method to update synaptic weights.
     *
     * @param synapses The synaptic weight matrix to update
     */
    private void updateWeights(SynapticMatrix synapses)
    {           
        int numberOfRows = synapses.getNumberOfRows();
        int numberOfColumns = synapses.getNumberOfColumns();

        for (int column = 0; column < numberOfColumns; column++){
            for (int row = 0; row < numberOfRows; row++){
                Synapse synapse = synapses.getSynapse(row, column);
                updateWeight(synapse);
            }
        }
    }

    /**
     * Helper method to update a synaptic weight.
     *
     * @param synapse The synapse to update
     */
    private void updateWeight(Synapse synapse)
    {
        double value = synapse.getWeight();
        value += ((learningRate_ * synapse.getError()) +
          (momentum_ * synapse.getPreviousWeightChange()));
        value *= (1.0 - weightDecay_);

        // decrease the value by weight decay factor
        synapse.setWeight(value);
    }

    // --------------------------------------------------------------
    // Main Method
    // --------------------------------------------------------------

    /**
     * Main method to create, train, and test the network.
     *
     * @param args a <code>String[]</code> value
     */
    public static void main(String[] args)
    {
        // define size of input and output vectors
        int inputSize = 8;
        int outputSize = 1;

        // using defaults for all other parameters

        // define training set
        int numberOfTrainingPairs = 300;
        Dataset trainingSet = new Dataset(numberOfTrainingPairs, inputSize,
          outputSize);
        trainingSet.readFile("../datasets/training.txt");

        // define testing set
        int numberOfTestingPairs = 300;
        Dataset testingSet = new Dataset(numberOfTestingPairs, inputSize,
          outputSize);
        testingSet.readFile("../datasets/testing.txt");

        // instantiate the network
        BackpropNetwork b1 =
          new BackpropNetwork(inputSize, 2*inputSize, outputSize);

        // set parameters
        b1.setNumberOfEpochs(3000);
        b1.setLearningRate(0.175);
        b1.setMomentum(0.65);
        b1.setEpochBased(false);

        // train the network
        b1.train(trainingSet);

        // test the network
        b1.test(testingSet);

        // now an epoch-based example

        // instantiate the network
        BackpropNetwork b2 =
          new BackpropNetwork(inputSize, 2*inputSize, outputSize);

        // set parameters
        b2.setNumberOfEpochs(3000);
        b2.setLearningRate(0.04);  // use a smaller learning rate
        b2.setMomentum(0.8);
        b2.setEpochBased(true);

        // train the network
        b2.train(trainingSet);

        // test the network
        b2.test(testingSet);
    }
}
