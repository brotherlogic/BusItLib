package uk.co.brotherlogic.busit.sheffield;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.brotherlogic.busit.BusStop;
import uk.co.brotherlogic.busit.BusTime;

/**
 * The Sheffield Bus Stop
 * 
 * @author sat
 * 
 */
public class SheffieldBusStop implements BusStop
{
	/** The stop ID */
	private final String stopId;

	/** The name of the stop */
	private String name;

	/** Where we pull the updates from */
	private static final String UPDATE_URL = "http://tsy.acislive.com/pip/stop_simulator_table.asp?NaPTAN=REPLACE&bMap=1&offset=0&refresh=30&pscode=95&dest=&duegate=90&PAC=REPLACE";

	/** Where we can pull the name from */
	private static final String NAME_URL = "http://tsy.acislive.com/pip/stop_simulator.asp?naptan=REPLACE&dest=";

	/** Offset of the BUS ID */
	private static final int BUS_ID_OFFSET = 0;

	/** Offset of the destination */
	private static final int DEST_OFFSET = 1;

	/** Offset of the time */
	private static final int TIME_OFFSET = 2;

	/** Offset of the floor status */
	private static final int FLOOR_OFFSET = 3;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            The id of the stop
	 * @param lat
	 *            The latitude location
	 * @param lon
	 *            The longitude location
	 * @param stopName
	 *            The Name of the stop
	 */
	public SheffieldBusStop(final String id)
	{
		this.stopId = id;
	}

	@Override
	public final void close()
	{
		// Do nothing
	}

	@Override
	public final List<BusTime> getArrivalTimes() throws IOException
	{
		List<BusTime> times = new LinkedList<BusTime>();

		if (name == null)
			try
			{
				URL tURL = new URL(NAME_URL.replace("REPLACE", stopId));
				Pattern p = Pattern.compile("<title>.*?\\d+(.*?)</title>");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(tURL.openStream()));
				for (String line = reader.readLine(); line != null
						&& name == null; line = reader.readLine())
				{
					Matcher m = p.matcher(line);
					while (m.find())
						name = m.group(1).trim();
				}
				reader.close();
			}
			catch (MalformedURLException e)
			{
				throw new IOException(e.getLocalizedMessage());
			}

		try
		{
			URL tURL = new URL(UPDATE_URL.replace("REPLACE", stopId));
			Pattern p = Pattern.compile("<td.*?>(.*?)</td>");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					tURL.openStream()));

			int rowPointer = 0;
			String busID = "";
			String dest = "";
			String time = "";

			// Don't really use this
			String floor = "";

			for (String line = reader.readLine(); line != null; line = reader
					.readLine())
			{
				Matcher m = p.matcher(line.replace("&nbsp;", " "));
				while (m.find())
				{
					switch (rowPointer)
					{
					case BUS_ID_OFFSET:
						busID = m.group(1).trim();
						break;
					case DEST_OFFSET:
						dest = m.group(1).trim();
						break;
					case TIME_OFFSET:
						time = m.group(1).trim();
						break;
					case FLOOR_OFFSET:
						floor = m.group(1).trim();
						break;
					default:
						System.err.println("Unknow row value: " + rowPointer);
					}
					rowPointer++;

					if (rowPointer > FLOOR_OFFSET)
					{
						rowPointer = 0;
						times.add(new BusTime(busID, dest, floor,
								parseTime(time)));
					}
				}
			}
		}
		catch (IOException e)
		{
			System.err.println("ERROR HERE : " + e.getLocalizedMessage());
			e.printStackTrace();
		}

		return times;
	}

	@Override
	public final String getID()
	{
		if (name == null)
			try
			{
				getArrivalTimes();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		return name + " [" + stopId + "]";
	}

	public final String getStopId()
	{
		return stopId;
	}

	/**
	 * Parses the time string
	 * 
	 * @param timeStr
	 *            The String time in XX:YY or N min format
	 * @throws ParseException
	 *             if we can't parse the time
	 */
	private long parseTime(final String timeStr) throws IOException
	{
		Calendar time = Calendar.getInstance();

		if (timeStr.contains(":"))
		{
			String[] elems = timeStr.split(":");
			int hours = Integer.parseInt(elems[0]);
			int minutes = Integer.parseInt(elems[1]);

			time.set(Calendar.HOUR_OF_DAY, hours);
			time.set(Calendar.MINUTE, minutes);

			return time.getTimeInMillis();
		}
		else if (timeStr.contains("min"))
		{
			String[] elems = timeStr.split("\\s+");
			int mins = Integer.parseInt(elems[0]);

			time.add(Calendar.MINUTE, mins);
		}
		else if (!timeStr.contains("Due"))
			throw new IOException("Cannot parse: " + timeStr);

		return time.getTimeInMillis();
	}

	@Override
	public final String toString()
	{
		return "STOP_ID:" + stopId;
	}
}
