package uk.co.brotherlogic.busit.sheffield;

import junit.framework.TestCase;
import uk.co.brotherlogic.busit.BusStop;

public class SheffieldBusStopTest extends TestCase
{
   public final void testLocationSearch()
   {
      BusStop stop = SheffieldBusUtils.getClosestStop(-1.5140, 53.3927);

      // This should be my local stop
      assert (stop.getStopID().equals("37021904"));
   }
}
