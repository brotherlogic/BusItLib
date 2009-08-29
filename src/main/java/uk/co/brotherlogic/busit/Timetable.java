package uk.co.brotherlogic.busit;

import java.io.IOException;
import java.util.List;

/**
 * The base class for a timetable
 * 
 * @author sat
 * 
 */
public interface Timetable
{
	/**
	 * Adds a new stop into the database
	 * 
	 * @param stopID
	 *            The number of the stop
	 * @param lat
	 *            The latitude location
	 * @param lon
	 *            The longitude location
	 * @return The new stop once it's been added to the database
	 * @throws IOException
	 *             if something goes wrong
	 */
	BusStop addStop(String stopID, double lat, double lon) throws IOException;

	/**
	 * Method to get the closest stops to a particular location
	 * 
	 * @param lat
	 *            The Latitude
	 * @param lon
	 *            The Longitude
	 * @param numb
	 *            The number of stops to return
	 * @return The closes bus stop (for this timetable) to the provided location
	 */
	List<BusStop> getClosestStops(double lat, double lon, int numb)
			throws IOException;

	/**
	 * Gets the stop with a particular ID
	 * 
	 * @param stopID
	 *            The Stop ID
	 * @return The stop with this ID or null if there is no such stop
	 */
	BusStop getStop(String stopID) throws IOException;
}
