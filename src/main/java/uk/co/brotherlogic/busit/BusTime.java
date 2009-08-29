package uk.co.brotherlogic.busit;

import java.io.IOException;

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

	/** The arrival time of the bus in a JAVA timestamp */
	private final long stopArrivalTimestamp;

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
			final long time) throws IOException
	{
		busId = id;
		destination = dest;
		attributes = attr;
		stopArrivalTimestamp = time;
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

	@Override
	public final String toString()
	{
		return getDescriptor() + " @ " + stopArrivalTimestamp;
	}
}
