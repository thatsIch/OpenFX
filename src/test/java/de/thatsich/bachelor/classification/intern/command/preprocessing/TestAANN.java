package de.thatsich.bachelor.classification.intern.command.preprocessing;

import static org.junit.Assert.fail;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(JukitoRunner.class)
public class TestAANN
{

	@Inject AANNPreProcessor aann;
	
	@Test
	public void test()
	{
		final double XOR_INPUT[][] = {
			{ 0.0, 0.0, 0.33 },
			{ 0.0, 0.33, 0.0 },
			{ 0.0, 0.33, 0.33 },
			{ 0.33, 0.0, 0.0 },
			{ 0.33, 0.0, 0.33 },
			{ 0.33, 0.33, 0.0 },
			{ 0.33, 0.33, 0.33 },
			{ 0.33, 0.33, 0.33 } };

		final double XOR_IDEAL[][] = {
			{ 0.0, 0.0, 0.33 },
			{ 0.0, 0.33, 0.0 },
			{ 0.0, 0.33, 0.33 },
			{ 0.33, 0.0, 0.0 },
			{ 0.33, 0.0, 0.33 },
			{ 0.33, 0.33, 0.0 },
			{ 0.33, 0.33, 0.33 },
			{ 0.33, 0.33, 0.33 } };

		this.aann.train( XOR_INPUT, XOR_IDEAL );
//		aann.test( XOR_INPUT, XOR_IDEAL );

		fail( "Not yet implemented" );
	}

}
