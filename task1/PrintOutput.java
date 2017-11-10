import java.util.*;

import java.io.IOException;

// these are for validation with respect to the JSON Schema
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jackson.JsonLoader;

public class PrintOutput
{
  // main method to print the tasks as asked in the requirements (1,2,3,4)
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

        printTask1(vehicleOffers);
        printTask2(vehicleOffers);
        printTask3(vehicleOffers);
        printTask4(vehicleOffers);
      }
      else
      {
        System.out.println("The JSON file provided is not a valid one.");
      }
    } // try
    catch(IOException ioe)
    {
      System.out.println(ioe);
    }
    catch(ProcessingException pe)
    {
      System.out.println(pe);
    }
  }

  private static void printTask1(ArrayList<VehicleOffer> vehicleOffers)
  {
    int count = 0;
    for (VehicleOffer vo : vehicleOffers)
    {
      count++;
      System.out.println(count + ". " + vo.getName() + " - " + vo.getPrice());
    }
    System.out.println();
  }

  private static void printTask2(ArrayList<VehicleOffer> vehicleOffers)
  {
    int count = 0;
    for (VehicleOffer vo : vehicleOffers)
    {
      count++;
      System.out.println(count + ". " + vo.getSpecification());
    }
    System.out.println();
  }

  private static void printTask3(ArrayList<VehicleOffer> vehicleOffers)
  {
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
      System.out.println(count + ". " + entry.getValue().getFirst() + " - " +
      entry.getKey() + " - " + entry.getValue().getSecond() +
      " - " + entry.getValue().getThird());
    }
    System.out.println();
  }
  private static void printTask4(ArrayList<VehicleOffer> vehicleOffers)
  {
    MergeSort ms = new MergeSort(vehicleOffers);
    vehicleOffers = ms.getSorted();
    int count = 0;
    for (VehicleOffer vo : vehicleOffers)
    {
      count++;
      System.out.println(count + ". " + vo.getName() + " - " + vo.getScore() +
        " - " + vo.getRating() + " - " + ((float)vo.getScore() +
          vo.getRating()));
    }
    System.out.println();
  }
}
