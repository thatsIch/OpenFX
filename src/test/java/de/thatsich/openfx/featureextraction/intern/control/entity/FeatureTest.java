package de.thatsich.openfx.featureextraction.intern.control.entity;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import org.jukito.JukitoRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author thatsIch
 * @since 06.06.2014.
 */
@RunWith(JukitoRunner.class)
public class FeatureTest
{
	private final FeatureConfig thisConfig;
	private final FeatureConfig thatConfig;
	private final List<IFeatureVector> emptyVector;
	private final List<IFeatureVector> vectorWith1;
	private final List<IFeatureVector> vectorWith2;

	private IFeature emptyFeature;
	private IFeature thisFeature;
	private IFeature sameFeature;
	private IFeature thatFeature;

	public FeatureTest()
	{
		this.thisConfig = new FeatureConfig("class", "extractor", "", 1);
		this.thatConfig = new FeatureConfig("class", "extractor", "", 2);
		this.emptyVector = new LinkedList<>();
		this.vectorWith1 = new LinkedList<>();
		this.vectorWith2 = new LinkedList<>();

		List<Double> floats1 = Arrays.asList(1D);
		List<Double> floats2 = Arrays.asList(2D);

		this.vectorWith1.add(new FeatureVector(floats1, false));
		this.vectorWith2.add(new FeatureVector(floats2, false));
	}

	@Before
	public void reset() throws Exception
	{
		this.emptyFeature = new Feature(this.thisConfig, this.emptyVector);
		this.thisFeature = new Feature(this.thisConfig, this.vectorWith1);
		this.sameFeature = new Feature(this.thisConfig, this.vectorWith1);
		this.thatFeature = new Feature(this.thatConfig, this.vectorWith2);
	}

	@Test
	public void testMergeWithEmptyVectors_ShouldSuccess()
	{
		final IFeature merge = this.emptyFeature.merge(this.emptyFeature);
		final int size = merge.vectors().size();

		Assert.assertEquals(0, size);
	}

	@Test
	public void testMergeWithUnemptyVectors_ShouldSuccess()
	{
		final IFeature merge = this.thisFeature.merge(this.sameFeature);
		final int size = merge.vectors().size();

		Assert.assertEquals(2, size);
	}

	@Test
	public void testMergeWithUnequalVectors_ShouldSucceed() throws Exception
	{
		final IFeature merge = this.thisFeature.merge(this.thatFeature);
		final int size = merge.vectors().size();

		Assert.assertEquals(1, size);
	}

	@Test
	public void testEqualsWithEqualObjects_ShouldSuccess()
	{
		Assert.assertEquals(this.thisFeature, this.sameFeature);
	}

	@Test
	public void testEqualsWithInequalObjct_ShouldFail()
	{
		Assert.assertNotEquals(this.thisFeature, 0);
	}

	@Test
	public void testEqualsWithInequalFeature_ShouldFail()
	{
		Assert.assertNotEquals(this.thisFeature, this.thatFeature);
	}

	@Test
	public void testEqualsWithSameObject_ShouldSuccess() throws Exception
	{
		Assert.assertEquals(this.thisFeature, this.thisFeature);
	}
}
