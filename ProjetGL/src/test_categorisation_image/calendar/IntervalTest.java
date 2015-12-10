package test_categorisation_image.calendar;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import categorisation_image.calendar.Interval;

public class IntervalTest {

	@SuppressWarnings("deprecation")
	@Test
	public void testInterval() {
		Date d1 = new Date(2015,12,10,16,55,10);
		Date d2 = new Date(2015,12,11,16,55,10);
		try
		{
			Interval i1 = new Interval(d1, d2);
			Interval i2 = new Interval(d1, d1);
			assertEquals(d1, i1.getStart());
			assertEquals(d2, i1.getEnd());
			assertTrue(i1.getStart().before(i1.getEnd()));
			assertEquals(i2.getStart(), i2.getEnd());
			new Interval(null, d2);
			fail("null argument");
		}
		catch (Exception e) {}
		try
		{
			new Interval(d1, null);
			fail("null argument");
		}
		catch (Exception e) {}
		try
		{
			new Interval(null, null);
			fail("null argument");
		}
		catch (Exception e) {}
		try
		{
			new Interval(d2, d1);
			fail("start > end");
		}
		catch (Exception e) {}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetStart() {
		Date d1 = new Date(2015,12,10,16,55,10);
		Date d2 = new Date(2015,12,11,16,55,10);
		Interval i1 = new Interval(d1, d2);
		assertEquals(d1, i1.getStart());
		assertNotNull(i1.getStart());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetEnd() {
		Date d1 = new Date(2015,12,10,16,55,10);
		Date d2 = new Date(2015,12,11,16,55,10);
		Interval i1 = new Interval(d1, d2);
		assertEquals(d2, i1.getEnd());
		assertNotNull(i1.getEnd());
	}
}
