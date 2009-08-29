package uk.co.brotherlogic.busit.sheffield;

import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import uk.co.brotherlogic.busit.BusStop;

/**
 * This is a parser for the stop data xml file
 * 
 * @author sat
 * 
 */
public class StopDataParser extends DefaultHandler
{
	/** This is the parsed list of stops */
	private final List<BusStop> stops = new LinkedList<BusStop>();

	/**
	 * Gets the stops
	 * 
	 * @return a list of all the bus stops
	 */
	public final List<BusStop> getStops()
	{
		return stops;
	}

	@Override
	public final void startElement(final String uri, final String localName,
			final String name, final Attributes attributes) throws SAXException
	{
		super.startElement(uri, localName, name, attributes);

		if (name.equalsIgnoreCase("stop") || localName.equalsIgnoreCase("stop"))
		{
			String id = attributes.getValue("id");
			stops.add(new SheffieldBusStop(id));
		}
	}
}
