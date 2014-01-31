package de.thatsich.bachelor.classification.intern.command.preprocessing;

import java.util.Arrays;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.intern.command.preprocessing.core.IPreProcessing;


@RunWith( JukitoRunner.class )
public class TestAANN
{
	@Inject
	AANNPreProcessor	aann;

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
			{ 0.33, 0.33, 0.33 } };

		IPreProcessing pre = this.aann.train( XOR_INPUT, XOR_INPUT );

		System.out.println( Arrays.toString( pre.preprocess( XOR_INPUT[0] ) ) );
		
		// [0.15667940529897997, 0.18082645153532378, 0.18082645153532378]

	}

}
