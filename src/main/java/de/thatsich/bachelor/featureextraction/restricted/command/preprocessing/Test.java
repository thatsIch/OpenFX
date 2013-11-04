package de.thatsich.bachelor.featureextraction.restricted.command.preprocessing;

public class Test {

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
