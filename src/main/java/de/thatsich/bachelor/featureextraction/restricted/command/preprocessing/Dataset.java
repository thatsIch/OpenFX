package de.thatsich.bachelor.featureextraction.restricted.command.preprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * <p>Java implementation of a class for reading in and accessing
 * numerical data to be used by neural networks.</p>
 *
 * <p>The <code>Dataset</code> class reads in data from a flat file.
 * The flat file is seen as a set of data "elements." An element is 
 * equivalent to a line of data in the file that contains an input
 * vector of size m and, optionally, an output vector of size n, is 
 * space-delimited and is terminated with the newline character ('\n'):</p>
 *
 * <p>input_1 input_2 ... input_m output_1 output_2 ... output_n</p>
 *
 * <p>There is no warranty of any kind on this code, not even the implied
 * warranty of merchantability or fitness for a particular purpose.
 * This code is intended for personal and educational purposes only.</p>
 *
 * @author Liquid Self
 */

public class Dataset
{
	/** number of elements */
	private int numberOfElements_;

	/** the size of the input vectors */
	private int inputSize_;

	/** the size of the output vectors */
	private int outputSize_;

	/** the data */
	private DatasetElement[] elements_;

	// --------------------------------------------------------------
	// Public Constructors
	// --------------------------------------------------------------

	/**
	 * Creates a new <code>Dataset</code> instance.
	 *
	 * @param numberOfElements The number of elements in the dataset
	 * @param inputSize The size of the input vectors
	 * @param outputSize The size of the output vectors
	 */
	public Dataset(int numberOfElements, int inputSize, int outputSize)
	{
		numberOfElements_ = numberOfElements;
		inputSize_ = inputSize;
		outputSize_ = outputSize;

		elements_ = new DatasetElement[numberOfElements_];
		for (int i = 0; i < numberOfElements_; i++){
			elements_[i] = new DatasetElement(inputSize, outputSize);
		}
	}

	// --------------------------------------------------------------
	// Public Methods
	// --------------------------------------------------------------

	/**
	 * Gets the size of the dataset.
	 *
	 * @return The number of elements of data
	 */
	public int getSize()
	{
		return numberOfElements_;
	}

	/**
	 * Gets the element of data at the specified index.
	 *
	 * @param whichElement The index
	 *
	 * @return The element of data
	 */
	public DatasetElement getElement(int whichElement)
	{
		return elements_[whichElement];
	}

	/**
	 * Reads in data from a flat file.
	 *
	 * @param fileName The name of the file (full or relative path)
	 */
	public void readFile(String fileName)
	{    	
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

			// read each line of data from the file
			// each line contains data of the form:
			//
			// input_1 input_2 ... input_m output_1 output_2 ... output_n

			for (int line = 0; line < numberOfElements_; line++){
				String data = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(data);

				// read in the input vector
				for (int i = 0; i < inputSize_; i++){
					elements_[line].getInputVector()[i] =
							(new Double(tokenizer.nextToken())).doubleValue();
				}

				// read in the output vector (if any)
				for (int i = 0; i < outputSize_; i++){
					elements_[line].getOutputVector()[i] =
							(new Double(tokenizer.nextToken())).doubleValue();
				}
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Shuffles the order of the lines of data.
	 */
	public void shuffle()
	{
	}
}
