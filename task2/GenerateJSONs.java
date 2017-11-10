import java.util.*;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.IOException;

// these are for validation with respect to the JSON Schema
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jackson.JsonLoader;

public class GenerateJSONs
{
  public static void main(String[] args)
  {
    try
    {
      // valiate with respect to schema
      final JsonNode schemaJson = JsonLoader.fromResource("/schema.json");
      final JsonNode vehiclesJson = JsonLoader.fromResource("/" + args[0]);
      final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
      final JsonSchema schema = factory.getJsonSchema(schemaJson);
      String report = schema.validate(vehiclesJson).toString();
      // if successful, this will be equal to "success"
      String valMsg = report.substring(report.length()-8,report.length()-1);
      if (valMsg.equals("success"))
      {
        ArrayList<VehicleOffer> vehicleOffers = (ArrayList<VehicleOffer>) Parser.parseToList(args[0]);

        writeJson1(vehicleOffers);
        writeJson2(vehicleOffers);
        writeJson3(vehicleOffers);
        writeJson4(vehicleOffers);
      }
      else
      {
        System.out.println("The JSON file provided is not a valid one.");
      }
    }
    catch(IOException ioe)
    {
      System.out.println(ioe);
    }
    catch(ProcessingException pe)
    {
      System.out.println(pe);
    }
  }

  private static void writeJson1(ArrayList<VehicleOffer> vehicleOffers)
  {
    try
    {
      PrintWriter jsonFileWriter = new PrintWriter(new FileWriter("./json/nameAndPrice.json"));
      JSONObject vehicleObj = new JSONObject();
      JSONArray vehicleArray = new JSONArray();
      JSONObject result = new JSONObject();

      int count = 0;
      for (VehicleOffer vo : vehicleOffers)
      {
        count++;
        vehicleObj.put("number", new Integer(count));
        vehicleObj.put("name", new String(vo.getName()));
        vehicleObj.put("price", new Float(vo.getPrice()));

        vehicleArray.add(vehicleObj);
        vehicleObj = new JSONObject();
      }
      result.put("carsList", vehicleArray);
      jsonFileWriter.print(result.toString());
      jsonFileWriter.close();
    }
    catch(IOException ioe)
    {
      System.err.println(ioe);
    }
  }

  private static void writeJson2(ArrayList<VehicleOffer> vehicleOffers)
  {
    try
    {
      PrintWriter jsonFileWriter =
        new PrintWriter(new FileWriter("./json/nameAndSpecification.json"));

      JSONObject vehicleObj = new JSONObject();
      JSONObject result = new JSONObject();
      JSONArray vehicleArray = new JSONArray();

      int count = 0;
      for (VehicleOffer vo : vehicleOffers)
      {
        count++;
        vehicleObj.put("number", new Integer(count));
        vehicleObj.put("specification", vo.getSpecification());
        vehicleArray.add(vehicleObj);
        vehicleObj = new JSONObject();
      }
      result.put("carsList", vehicleArray);
      jsonFileWriter.print(result.toString());
      jsonFileWriter.close();
    }
    catch(IOException ioe)
    {
      System.err.println(ioe);
    }
  }

  private static void writeJson3(ArrayList<VehicleOffer> vehicleOffers)
  {
    try
    {
    PrintWriter jsonFileWriter =
      new PrintWriter(new FileWriter("./json/carTypeRatings.json"));

    JSONObject rating = new JSONObject();
    JSONArray ratingsArray = new JSONArray();
    JSONObject result = new JSONObject();

      HashMap<String,Triple<String,String,Float>> highestRatings =
        new HashMap<String,Triple<String,String,Float>>();
      // construct the map such that only the car types that are present in the json file
      // are included
      for (VehicleOffer vo : vehicleOffers)
      {
        highestRatings.put(vo.getType(), new Triple<String,String,Float>(
          vo.getName(), vo.getSupplier(), vo.getRating()));
      }
      // compute the highest rating for each car
      for (VehicleOffer vo : vehicleOffers)
      {
        if (vo.getRating() > highestRatings.get(vo.getType()).getThird())
        {
          highestRatings.put(vo.getType(), new Triple<String,String,Float>(
            vo.getName(), vo.getSupplier(), vo.getRating()));
        }
      }
      // print the results
      int count = 0;
      for (Map.Entry<String, Triple<String,String,Float>> entry : highestRatings.entrySet())
      {
        count++;
        rating.put("number", new Integer(count));
        rating.put("name", entry.getValue().getFirst());
        rating.put("carType", entry.getKey());
        rating.put("supplier", entry.getValue().getSecond());
        rating.put("rating", entry.getValue().getThird());

        ratingsArray.add(rating);
        rating = new JSONObject();
      }
      result.put("carTypes", ratingsArray);
      jsonFileWriter.print(result.toString());
      jsonFileWriter.close();
    } // try
    catch(IOException ioe)
    {
      System.err.println(ioe);
    }
  }

  private static void writeJson4(ArrayList<VehicleOffer> vehicleOffers)
  {
    try
    {
      PrintWriter jsonFileWriter =
        new PrintWriter(new FileWriter("./json/vehicleRatings.json"));

      JSONObject vehicleObj = new JSONObject();
      JSONArray vehicleArray = new JSONArray();
      JSONObject result = new JSONObject();

      MergeSort ms = new MergeSort(vehicleOffers);
      vehicleOffers = ms.getSorted();

      int count = 0;
      for (VehicleOffer vo : vehicleOffers)
      {
        count++;
        vehicleObj.put("number", new Integer(count));
        vehicleObj.put("name", vo.getName());
        vehicleObj.put("score", vo.getScore());
        vehicleObj.put("rating", vo.getRating());
        vehicleObj.put("overall", new Float((float)vo.getScore() + vo.getRating()));

        vehicleArray.add(vehicleObj);
        vehicleObj = new JSONObject();
      }
      result.put("carsList", vehicleArray);
      jsonFileWriter.print(result.toString());
      jsonFileWriter.close();
    }
    catch(IOException ioe)
    {
      System.err.println(ioe);
    }
  }
}
