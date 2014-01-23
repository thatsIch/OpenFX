package de.thatsich.bachelor.featureextraction.restricted.command.preprocessing;

/**
 * <p>
 * Java implementation of a synapse.
 * </p>
 * 
 * <p>
 * There is no warranty of any kind on this code, not even the implied warranty
 * of merchantability or fitness for a particular purpose. This code is intended
 * for personal and educational purposes only.
 * </p>
 * 
 * @author Liquid Self
 */
class Synapse
{
	/** the synaptic weight */
	private double	weight_;

	/** the error of the synaptic weight */
	private double	error_;

	/** the previous value of the synaptic weight */
	private double	previousWeight_;

	/** the previous error of the synaptic weight */
	private double	previousError_;

	// --------------------------------------------------------------
	// Public Constructors
	// --------------------------------------------------------------

	/**
	 * Default constructor - creates a new <code>Synapse</code> instance.
	 */
	public Synapse()
	{
		weight_ = 0.0;
		error_ = 0.0;
		previousWeight_ = 0.0;
		previousError_ = 0.0;
	}

	// --------------------------------------------------------------
	// Public Methods
	// --------------------------------------------------------------

	/**
	 * Gets the weight.
	 * 
	 * @return The weight
	 */
	public double getWeight()
	{
		return weight_;
	}

	/**
	 * Sets the weight to the specified value.
	 * 
	 * @param weight
	 *            The weight value
	 */
	public void setWeight( double weight )
	{
		previousWeight_ = weight_;
		weight_ = weight;
	}

	/**
	 * Adds a specified amount to the weight.
	 * 
	 * @param weightToAdd
	 *            The amount to add
	 */
	public void addWeight( double weightToAdd )
	{
		previousWeight_ = weight_;
		weight_ += weightToAdd;
	}

	/**
	 * Gets the previous weight change.
	 * 
	 * @return The previous weight change
	 */
	public double getPreviousWeightChange()
	{
		return ( weight_ - previousWeight_ );
	}

	/**
	 * Undoes the previous weight change.
	 */
	public void undoPreviousWeightChange()
	{
		weight_ = previousWeight_;
	}

	/**
	 * Gets the error.
	 * 
	 * @return The error
	 */
	public double getError()
	{
		return error_;
	}

	/**
	 * Sets the error.
	 * 
	 * @param error
	 *            The error value
	 */
	public void setError( double error )
	{
		error_ = error;
	}

	/**
	 * Adds a specified amount to the error.
	 * 
	 * @param errorToAdd
	 *            The amount to add
	 */
	public void addError( double errorToAdd )
	{
		error_ += errorToAdd;
	}

	/**
	 * Gets the previous weight value.
	 * 
	 * @return The previous weight value
	 */
	public double getPreviousWeight()
	{
		return previousWeight_;
	}

	/**
	 * Sets the previous error value.
	 * 
	 * @param previousError
	 *            The previous error value
	 */
	public void setPreviousError( double previousError )
	{
		previousError_ = previousError;
	}

	/**
	 * Gets the previous error value.
	 * 
	 * @return The previous error value
	 */
	public double getPreviousError()
	{
		return previousError_;
	}

	/**
	 * Saves the current error value (e.g., to prepare for the next iteration).
	 */
	public void saveError()
	{
		previousError_ = error_;
	}
}
