import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.util.Scanner;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

import java.io.FileNotFoundException;

class Parser
{

  // this method takes a String as input that is considered to be a json file
  // valid in respect to schema.json and returns a List of VehicleOffer s
  // that contain the information in the json file
  public static List<VehicleOffer> parseToList(String jsonFile)
  {
    JSONParser parser = new JSONParser();
    List<VehicleOffer> vehicleOffers = new ArrayList<VehicleOffer>();

    try
    {
      String content = new Scanner(new File(jsonFile)).useDelimiter("\\Z").next();
      Object obj = parser.parse(content);
      JSONObject jsonObj = (JSONObject)obj;
      JSONObject searchNode = (JSONObject)jsonObj.get("Search");
      JSONArray vehicleList = (JSONArray)searchNode.get("VehicleList");
      for (Object item : vehicleList)
      {
        JSONObject vehicleOffer = (JSONObject)item;
        Object priceObj = vehicleOffer.get("price");
        Object ratingObj = vehicleOffer.get("rating");
        float price, rating;
        // get the float value of whatever it is parsed for price and rating
        if (priceObj instanceof Double)
        {
          price = ((Double)priceObj).floatValue();
        }
        else
        {
          price = ((Long)priceObj).floatValue();
        }
        if (ratingObj instanceof Double)
        {
          rating = ((Double)ratingObj).floatValue();
        }
        else
        {
          rating = ((Long)ratingObj).floatValue();
        }
        vehicleOffers.add(new VehicleOffer(
          (String)vehicleOffer.get("sipp"),
          (String)vehicleOffer.get("name"),
          price,
          (String)vehicleOffer.get("supplier"),
          rating
        ));
      }
    } // try
    catch(FileNotFoundException fnfe)
    {
      System.out.println(fnfe);
    }
    catch(ParseException pe)
    {
      System.out.println(pe);
    }
    return vehicleOffers;
  } // parseToList
} // Parser class
