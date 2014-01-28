package de.thatsich.bachelor.classification.intern.command.preprocessing;

import static org.junit.Assert.fail;

import org.junit.Test;


public class TestAANN
{

	@Test
	public void test()
	{
		final AANNPreProcessor aann = new AANNPreProcessor( 3, 5, 2 );

		final double XOR_INPUT[][] = {
			{ 0.0, 0.0, 0.33 },
			{ 0.0, 0.33, 0.0 },
			{ 0.0, 0.33, 0.33 },
			{ 0.33, 0.0, 0.0 },
			{ 0.33, 0.0, 0.33 },
			{ 0.33, 0.33, 0.0 },
			{ 0.33, 0.33, 0.33 } };

		final double XOR_IDEAL[][] = {
			{ 0.0, 0.0, 0.33 },
			{ 0.0, 0.33, 0.0 },
			{ 0.0, 0.33, 0.33 },
			{ 0.33, 0.0, 0.0 },
			{ 0.33, 0.0, 0.33 },
			{ 0.33, 0.33, 0.0 },
			{ 0.33, 0.33, 0.33 } };

		aann.train( XOR_INPUT, XOR_IDEAL );
		aann.test( XOR_INPUT, XOR_IDEAL );

		fail( "Not yet implemented" );
	}

}
