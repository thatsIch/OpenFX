package de.thatsich.bachelor.classification.intern.control.command.preprocessing;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import de.thatsich.bachelor.preprocessing.intern.control.command.preprocessor.AANNPreProcessor;


@RunWith( JukitoRunner.class )
public class TestAANN
{
	@Test
	@Inject
	public void testTraining( AANNPreProcessor aann )
	{
		final double xorInput3D[][] = {
			{ 0.0, 0.0, 0.33 },
			{ 0.0, 0.33, 0.0 },
			{ 0.0, 0.33, 0.33 },
			{ 0.33, 0.0, 0.0 },
			{ 0.33, 0.0, 0.33 },
			{ 0.33, 0.33, 0.0 },
			{ 0.33, 0.33, 0.33 } };

		aann.train( xorInput3D, xorInput3D, null );
	}
}
