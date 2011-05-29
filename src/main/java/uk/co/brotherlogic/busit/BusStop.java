package uk.co.brotherlogic.busit;

import java.io.IOException;
import java.util.List;

/**
 * A single bus stop
 * 
 * @author sat
 * 
 */
public interface BusStop
{
   /**
    * Release any resources this stop may be holding
    */
   void close();

   /**
    * Get the arrival times for this specific stop
    * 
    * @return A List of BusTime for this stop
    * @throws IOException
    *            Thrown if something goes wrong when parsing the arrival times
    */
   List<BusTime> getArrivalTimes() throws IOException;

   double getDist(double lat, double lon);

   /**
    * Get the ID of this stop
    * 
    * @return a String ID for the stop
    */
   String getID();
}
