package de.thatsich.bachelor.featureextraction.restricted.command.preprocessing;

import de.thatsich.bachelor.featureextraction.restricted.command.preprocessing.Neuron.ActivationFunction;


class NeuralLayer
{
	/** the number of neurons in the layer */
	private int			size_	= 0;

	/** the neurons in the layer */
	private Neuron[]	neurons_;

	// --------------------------------------------------------------
	// Public Constructors
	// --------------------------------------------------------------

	/**
	 * Creates a new <code>NeuralLayer</code> instance.
	 * 
	 * @param size
	 *            The size of the neural layer
	 */
	public NeuralLayer( int size )
	{
		this.size_ = size;
		this.neurons_ = new Neuron[ size_ ];
		for ( int i = 0; i < this.size_; i++ )
		{
			this.neurons_[i] = new Neuron();
		}
	}

	/**
	 * Gets the size.
	 * 
	 * @return The size
	 */
	public int getSize()
	{
		return this.size_;
	}

	/**
	 * Gets the neuron at the specified index.
	 * 
	 * @param index
	 *            The index
	 * 
	 * @return The neuron at the specified index
	 */
	public Neuron getNeuron( int index )
	{
		return this.neurons_[index];
	}

	/**
	 * Sets the activation functions of all neurons in the layer to
	 * the specified type.
	 * 
	 * @param activationFunction
	 *            The type of activation function
	 * @see class Neuron for valid activation function types
	 */
	public void setActivationFunctions( ActivationFunction activationFunction )
	{
		for ( int i = 0; i < this.size_; i++ )
		{
			this.neurons_[i].setActivationFunction( activationFunction );
		}
	}
}
