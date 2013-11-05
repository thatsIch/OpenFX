package de.thatsich.bachelor.featureextraction.restricted.command.preprocessing;


/**
 * <p>Java implementation of a neuron.</p>
 *
 * <p>There is no warranty of any kind on this code, not even the implied
 * warranty of merchantability or fitness for a particular purpose.
 * This code is intended for personal and educational purposes only.</p>
 *
 * @author Liquid Self
 */

public class Neuron
{
	// --------------------------------------------------------------
	// Public Constants
	// --------------------------------------------------------------

	enum ActivationFunction {
		SIGMOID,
		STEP,
		BIPOLAR_STEP
	}

	// --------------------------------------------------------------
	// Private Members
	// --------------------------------------------------------------

	/** the activation value */
	private double activation_;

	/** the gain - adjusts the steepness of a sigmoid function */
	private double gain_;

	/** the bias - adjusts the magnitude of the neural input */
	private double bias_;

	/** designates which activation function we are using */
	private ActivationFunction activationFunction_;

	// --------------------------------------------------------------
	// Constructors
	// --------------------------------------------------------------

	/**
	 * Default constructor - creates a new <code>Neuron</code> instance.
	 */
	Neuron()
	{
		this.activation_ = 0.0;
		this.gain_ = 1.0;
		this.bias_ = 0.0;
		this.activationFunction_ = ActivationFunction.SIGMOID;
	}

	/**
	 * Creates a new <code>Neuron</code> instance.
	 *
	 * @param activation The activation value
	 * @param gain The gain
	 * @param bias The bias
	 * @param activationFunction The activation function to use
	 */
	Neuron(double activation, double gain, double bias, ActivationFunction activationFunction)
	{
		this.activation_ = activation;
		this.gain_ = gain;
		this.bias_ = bias;
		this.activationFunction_ = activationFunction;
	}

	// Getter
	public double getActivation() { return this.activation_; }
	public double getBias() { return this.bias_; }
	public double getGain() { return this.gain_; }

	// Setter
	public void setActivation(double activation) { this.activation_ = activation; }
	public void setBias(double bias) { this.bias_ = bias; }
	public void setGain(double gain) { this.gain_ = gain; }

	/**
	 * Sets the activation function type.
	 *
	 * @param activationFunction Flag specifying the activation function
	 *     (valid values are listed in the Public Constants section of this
	 *     class)
	 */
	public void setActivationFunction(ActivationFunction activationFunction)
	{
		this.activationFunction_ = activationFunction;
	}

	/**
	 * Computes the activation.
	 *
	 * @param input The input to the neuron
	 * 
	 * @return The computed activation
	 */
	public double computeActivation(double input)
	{
		switch (this.activationFunction_) {
			case SIGMOID:
				this.activation_ = 1.0/(1.0 + Math.exp(-1.0 * (input + this.bias_) * this.gain_));
				break;
				
			case STEP:
				this.activation_ = (input + this.bias_ > 0) ? 1.0 : 0.0;
				break;
				
			case BIPOLAR_STEP:
				this.activation_ = (input + this.bias_ > 0) ? 1.0 : -1.0;
				break;
				
			default:
				this.activation_ = input;
				break;
		}

		return this.activation_;
	}
}
