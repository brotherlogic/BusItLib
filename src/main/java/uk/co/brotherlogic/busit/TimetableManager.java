package uk.co.brotherlogic.busit;

import uk.co.brotherlogic.busit.sheffield.SheffieldTimetable;

/**
 * The overall timetable manager
 * 
 * @author sat
 * 
 */
public final class TimetableManager
{
	/**
	 * Blocking constructor
	 */
	private TimetableManager()
	{

	}

	/**
	 * Gets the web location for a given timetable
	 * 
	 * @param location
	 *            The name of the location (e.g. SHEFFIELD)
	 * @return A legitimate timetable for that location or null if the location
	 *         doesn't exist
	 */
	public static Timetable getTimetable(final String location)
	{
		if (location.equals("SHEFFIELD"))
			return new SheffieldTimetable();
		else
			return null;
	}
}
