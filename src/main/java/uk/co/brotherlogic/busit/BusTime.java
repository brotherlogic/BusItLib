package uk.co.brotherlogic.busit;

import java.io.IOException;
import java.util.Calendar;

/**
 * The arrival time for a given stop
 * 
 * @author sat
 * 
 */
public class BusTime
{
	/** The ID of the bus */
	private final String busId;

	/** The destination of the bus */
	private final String destination;

	/** Any extra attributes of this bus */
	private final String attributes;

	/** The arrival time of the bus in a UNIX timestamp */
	private long stopArrivalTimestamp;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            The ID of the stop
	 * @param dest
	 *            The destination
	 * @param attr
	 *            Extra attributes
	 * @param time
	 *            The time of arrival as a string
	 * @throws ParseException
	 *             If we can't parse the time string
	 */
	public BusTime(final String id, final String dest, final String attr,
			final String time) throws IOException
	{
		busId = id;
		destination = dest;
		attributes = attr;

		parseTime(time);
	}

	/**
	 * Get the descriptor for this time
	 * 
	 * @return A String descriptor
	 */
	public final String getDescriptor()
	{
		return busId + ": " + destination + " (" + attributes + ")";
	}

	/**
	 * Get the arrival time as a UNIX timestamp
	 * 
	 * @return long timestamp of the arrival time
	 */
	public final long getTime()
	{
		return stopArrivalTimestamp;
	}

	/**
	 * Parses the time string
	 * 
	 * @param timeStr
	 *            The String time in XX:YY or N min format
	 * @throws ParseException
	 *             if we can't parse the time
	 */
	private void parseTime(final String timeStr) throws IOException
	{
		Calendar time = Calendar.getInstance();

		if (timeStr.contains(":"))
		{
			String[] elems = timeStr.split(":");
			int hours = Integer.parseInt(elems[0]);
			int minutes = Integer.parseInt(elems[1]);

			time.set(Calendar.HOUR_OF_DAY, hours);
			time.set(Calendar.MINUTE, minutes);

			stopArrivalTimestamp = time.getTimeInMillis();
		}
		else if (timeStr.contains("min"))
		{
			String[] elems = timeStr.split("\\s+");
			int mins = Integer.parseInt(elems[0]);

			time.add(Calendar.MINUTE, mins);
		}
		else if (!timeStr.contains("Due"))
			throw new IOException("Cannot parse: " + timeStr);

		stopArrivalTimestamp = time.getTimeInMillis();
	}

	@Override
	public final String toString()
	{
		return getDescriptor() + " @ " + stopArrivalTimestamp;
	}
}
