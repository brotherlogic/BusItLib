package uk.co.brotherlogic.busit.sheffield;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import uk.co.brotherlogic.busit.BusStop;

public class SheffieldDataProcessor
{
   public static void main(String[] args) throws Exception
   {
      File f = new File("etc/NaPTAN.xml");
      SheffieldDataProcessor proc = new SheffieldDataProcessor();
      List<BusStop> stops = proc.process(new FileInputStream(f));
      System.out.println(stops.size());
   }

   public List<BusStop> process(InputStream dataFile) throws IOException

   {

      List<BusStop> busstops = new LinkedList<BusStop>();
      try
      {
         // Build the XPath expression to read the data
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setNamespaceAware(true);
         DocumentBuilder builder = factory.newDocumentBuilder();
         Document doc = builder.parse(dataFile);

         XPathFactory xfactory = XPathFactory.newInstance();
         XPath xpath = xfactory.newXPath();
         XPathExpression expr = xpath.compile("//Area[AreaID=370]/Stops/*");

         Object result = expr.evaluate(doc, XPathConstants.NODESET);
         NodeList nodes = (NodeList) result;
         for (int i = 0; i < nodes.getLength(); i++)
         {
            Node node = nodes.item(i);

            String stopNumber = "", stopName = "", lat = "", lon = "";
            NodeList cnodes = node.getChildNodes();
            for (int j = 0; j < cnodes.getLength(); j++)
            {
               Node cnode = cnodes.item(j);
               if (cnode != null && cnode.getLocalName() != null)
                  if (cnode.getLocalName().equals("ATCOCode"))
                     stopNumber = cnode.getTextContent();
                  else if (cnode.getLocalName().equals("CommonName"))
                     stopName = cnode.getTextContent();
                  else if (cnode.getLocalName().equals("Lon"))
                     lon = cnode.getTextContent();
                  else if (cnode.getLocalName().equals("Lat"))
                     lat = cnode.getTextContent();
            }
            busstops.add(new SheffieldBusStop(stopNumber, stopName, Double.parseDouble(lon), Double
                  .parseDouble(lat)));
         }
      }
      catch (XPathExpressionException e)
      {
         throw new IOException(e);
      }
      catch (ParserConfigurationException e)
      {
         throw new IOException(e);
      }
      catch (SAXException e)
      {
         throw new IOException(e);
      }
      return busstops;
   }
}
