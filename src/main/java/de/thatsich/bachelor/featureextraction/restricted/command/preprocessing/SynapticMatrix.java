package de.thatsich.bachelor.featureextraction.restricted.command.preprocessing;

/**
 * <p>Java implementation of a synaptic matrix.</p>
 *
 * <p>There is no warranty of any kind on this code, not even the implied
 * warranty of merchantability or fitness for a particular purpose.
 * This code is intended for personal and educational purposes only.</p>
 *
 * @author Liquid Self
 */

class SynapticMatrix
{
    /** the number of rows */
    private int numberOfRows_ = 0;

    /** the number of columns */
    private int numberOfColumns_ = 0;

    /** the synapses */
    private Synapse[][] synapses_;

    // --------------------------------------------------------------
    // Public Constructors
    // --------------------------------------------------------------
    
    /**
     * Creates a new <code>SynapticMatrix</code> instance.
     */
    public SynapticMatrix(int numberOfRows, int numberOfColumns)
    {
        numberOfRows_ = numberOfRows;
        numberOfColumns_ = numberOfColumns;
        synapses_ = new Synapse[numberOfRows_][numberOfColumns_];
        for (int row = 0; row < numberOfRows_; row++){
            synapses_[row] = new Synapse[numberOfColumns_];
            for (int column = 0; column < numberOfColumns_; column++){
                synapses_[row][column] = new Synapse();
            }
        }
    }

    // --------------------------------------------------------------
    // Public Methods
    // --------------------------------------------------------------
    
    /**
     * Initializes the weight values to a specified value.
     *
     * @param value The value
     */
    public void initializeWeights(double value)
    {
        for (int row = 0; row < numberOfRows_; row++){
            for (int column = 0; column < numberOfColumns_; column++){
                Synapse synapse = synapses_[row][column];
                synapse.setWeight(value);
            }
        }
    }

    /**
     * Initializes the weight values to random y values,
     * such that if x is a random value in [0, 1],
     * then y = (factor * x ) + scalar.
     *
     * @param factor The factor
     * @param scalar The scalar
     */
    public void initializeWeightsRandom(double factor, double scalar)
    {
        for (int row = 0; row < numberOfRows_; row++){
            for (int column = 0; column < numberOfColumns_; column++){
                Synapse synapse = synapses_[row][column];
                synapse.setWeight(factor * Math.random() + scalar);
            }
        }
    } 

    /**
     * Gets the synapse specified by a row and column in the matrix.
     *
     * @param row The row
     * @param column The column
     *
     * @return The synapse
     */ 
    public Synapse getSynapse(int row, int column)
    {
        return synapses_[row][column];
    }

    /**
     * Gets the number of rows.
     *
     * @return The number of rows
     */
    public int getNumberOfRows()
    {
        return numberOfRows_;
    }

    /**
     * Gets the number of columns.
     *
     * @return The number of columns
     */
    public int getNumberOfColumns()
    {
        return numberOfColumns_;
    }

    /**
     * Prints the synaptic weights to System.out.
     */
    public void printWeights()
    {
        for (int row = 0; row < numberOfRows_; row++){
            for (int column = 0; column < numberOfColumns_; column++){
                System.out.print(synapses_[row][column].getWeight() + " ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Resets the error values for each synapse to 0.
     */
    public void resetErrors()
    {
        for (int row = 0; row < numberOfRows_; row++){
            for (int column = 0; column < numberOfColumns_; column++){
                Synapse synapse = synapses_[row][column];
                synapse.setError(0.0);
            }
        }
    }

    /**
     * Saves the error values for each synapse.
     */
    public void saveErrors()
    {
        for (int row = 0; row < numberOfRows_; row++){
            for (int column = 0; column < numberOfColumns_; column++){
                Synapse synapse = synapses_[row][column];
                synapse.saveError();
            }
        }
    }    
}
