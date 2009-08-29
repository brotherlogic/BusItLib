package uk.co.brotherlogic.busit.sheffield;

import junit.framework.TestCase;

/**
 * Sheffield timetable utils test
 * 
 * @author sat
 * 
 */
public class SheffieldTimeTableUtilsTest extends TestCase
{
	public SheffieldTimeTableUtilsTest(final String name)
	{
		super(name);
	}

	/**
	 * Testing that we can get some services
	 */
	public final void testGetServices()
	{
		// Check that we're getting some services back from here
		assertTrue(SheffieldBusUtils.getBusServices().size() > 0);
	}
}
