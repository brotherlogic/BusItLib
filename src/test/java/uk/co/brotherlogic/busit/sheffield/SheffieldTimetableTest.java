package uk.co.brotherlogic.busit.sheffield;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tester class for the sheffield timetable
 * 
 * @author sat
 * 
 */
public class SheffieldTimetableTest extends TestCase
{
	/** The local timetable to use for all the tests */
	private final SheffieldTimetable tTable;

	public SheffieldTimetableTest(final String testName)
	{
		super(testName);
		tTable = new SheffieldTimetable();
	}

	public final void testExists() throws IOException
	{
		// This is the stop id of the rangley road stop
		assertNotNull(tTable.getStop("37021894"));
	}

	public final void testForClosest() throws IOException
	{
		final double heavygateLat = 53.3949;
		final double heavygateLon = -1.502997;

		// Check that we've got some times
		System.err.println(tTable
				.getClosestStops(heavygateLat, heavygateLon, 1).get(0).getID());
		assertTrue(tTable.getClosestStops(heavygateLat, heavygateLon, 1).get(0)
				.getID().equalsIgnoreCase("Rangeley Road [37021894]"));
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite(SheffieldTimetableTest.class);
	}
}
