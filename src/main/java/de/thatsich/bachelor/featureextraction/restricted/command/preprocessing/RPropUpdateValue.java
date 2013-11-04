package de.thatsich.bachelor.featureextraction.restricted.command.preprocessing;

/**
 * <p>Java implementation of an update value used for resilient propagation
 * networks.</p>
 *
 * <p>There is no warranty of any kind on this code, not even the implied
 * warranty of merchantability or fitness for a particular purpose.
 * This code is intended for personal and educational purposes only.</p>
 *
 * @author Liquid Self
 */

class RPropUpdateValue
{
    /** the update-value */
    private double theValue_ = 0.1;

    /** largest value allowed */
    private static final double upperBound_ = 50.0;

    /** smallest value allowed */
    private static final double lowerBound_ = .000001;

    // --------------------------------------------------------------
    // Public Constructors
    // --------------------------------------------------------------

    /**
     * Default constructor - Creates a new <code>RPropUpdateValue</code>
     * instance.
     */
    RPropUpdateValue() {}

    /**
     * Gets the update value.
     *
     * @return The update value
     */
    public double getValue()
    {
        return theValue_;
    }

    /**
     * Updates the update value.
     *
     * @param The factor to scale the update value by
     */
    public void update(double factor)
    {
        theValue_ *= factor;
        if (theValue_ > upperBound_){
            theValue_ = upperBound_;
        }
        else if (theValue_ < lowerBound_){
            theValue_ = lowerBound_;
        }
    }
}