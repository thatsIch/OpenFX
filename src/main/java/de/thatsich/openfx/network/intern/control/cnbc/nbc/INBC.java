package de.thatsich.openfx.network.intern.control.cnbc.nbc;

/**
 * @author thatsIch
 * @since 03.06.2014.
 */
public interface INBC
{
	String getUnqiueImageClass();

	void evolve();

	void addFeature();

	void removeFeature();

	double getFuserOutput();

}
