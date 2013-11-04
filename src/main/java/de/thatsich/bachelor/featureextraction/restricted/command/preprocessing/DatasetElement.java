package de.thatsich.bachelor.featureextraction.restricted.command.preprocessing;

/**
 * <p>Java implementation of a dataset "element." An element is comprised
 * of an input vector of size m and, optionally, an output vector of size
 * n.</p>
 *
 * <p>There is no warranty of any kind on this code, not even the implied
 * warranty of merchantability or fitness for a particular purpose.
 * This code is intended for personal and educational purposes only.</p>
 *
 * @author Liquid Self
 */

class DatasetElement
{
	/** the size of the input vector */
	private final int inputSize_;

	/** the size of the output vector */
	private final int outputSize_;

	/** the input vector */
	private final double[] inputVector_;

	/** the output vector */
	private double[] outputVector_;

	// --------------------------------------------------------------
	// Public Constructors
	// --------------------------------------------------------------

	/**
	 * Creates a new <code>DatasetElement</code> instance.
	 *
	 * @param inputSize The size of the input vector
	 * @param outputSize The size of the output vector
	 */
	DatasetElement(int inputSize, int outputSize)
	{
		this.inputSize_ = inputSize;
		this.outputSize_ = outputSize;

		this.inputVector_ = new double[this.inputSize_];
		if (outputSize > 0){
			this.outputVector_ = new double[this.outputSize_]; 
		}
	}

	// --------------------------------------------------------------
	// Public Methods
	// --------------------------------------------------------------

	/**
	 * Gets the input vector.
	 *
	 * @return The input vector
	 */
	public double[] getInputVector()
	{
		return this.inputVector_;
	}

	/**
	 * Gets the output vector.
	 *
	 * @return The output vector
	 */
	public double[] getOutputVector()
	{
		return this.outputVector_;
	}
}
