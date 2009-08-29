package uk.co.brotherlogic.busit.sheffield;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import uk.co.brotherlogic.busit.BusStop;
import uk.co.brotherlogic.busit.Timetable;

/**
 * The timetable for Sheffield
 * 
 * @author sat
 * 
 */
public class SheffieldTimetable implements Timetable
{

	/** Where we pull the stop data from */
	private static final String BASE_DATA_URL = "http://brotherlogic.plus.com:8090/";

	@Override
	public final List<BusStop> getClosestStops(final double lat,
			final double lon, final int num) throws IOException
	{
		StopDataParser parser = new StopDataParser();
		try
		{
			URL toSearch = new URL(BASE_DATA_URL + "closest?lat=" + lat
					+ "&lon=" + lon + "&num=" + num);
			SAXParser xmlProc = SAXParserFactory.newInstance().newSAXParser();
			xmlProc.parse(toSearch.openStream(), parser);
		}
		catch (MalformedURLException e)
		{
			throw new IOException(e.getLocalizedMessage());
		}
		catch (ParserConfigurationException e)
		{
			throw new IOException(e.getLocalizedMessage());
		}
		catch (SAXException e)
		{
			throw new IOException(e.getLocalizedMessage());
		}

		return parser.getStops();
	}

	@Override
	public final BusStop getStop(final String stopID) throws IOException
	{
		return new SheffieldBusStop(stopID);
	}

}
