package uk.co.brotherlogic.busit;

import java.io.File;

import uk.co.brotherlogic.busit.sheffield.SheffieldTimetable;

/**
 * The overall timetable manager
 * 
 * @author sat
 * 
 */
public final class TimetableManager
{
	/** The location of cache file */
	private static File cacheDirectory;

	/**
	 * Blocking constructor
	 */
	private TimetableManager()
	{

	}

	/**
	 * Static method to get the cache directory
	 * 
	 * @return File of the cache directory
	 */
	public static File getCacheDirectory()
	{
		return cacheDirectory;
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

	/**
	 * Sets the cache directory
	 * 
	 * @param cDir
	 *            The file directory for the cache
	 */
	public static void setCacheDirectory(final File cDir)
	{
		cacheDirectory = cDir;
	}
}
