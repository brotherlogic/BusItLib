package uk.co.brotherlogic.busit.sheffield;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generic Utils for the Sheffield Busses
 * 
 * @author sat
 * 
 */
public final class SheffieldBusUtils
{
	/** The Servies URL */
	private static final String SERVICES_URL = "http://www.travelsouthyorkshiretimetableupdates.com/includes/functions.php?q=B&call=chkservice";

	/**
	 * Blocking constructor
	 */
	private SheffieldBusUtils()
	{

	}

	public static List<String> getBusServices()
	{
		List<String> services = new LinkedList<String>();

		try
		{
			URL url = new URL(SERVICES_URL);

			Pattern matcher = Pattern.compile("<option.*?>(.*?)<");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			for (String line = reader.readLine(); line != null; line = reader
					.readLine())

			{
				Matcher pMatcher = matcher.matcher(line);
				while (pMatcher.find())
					services.add(pMatcher.group(1));
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return services;
	}

}
