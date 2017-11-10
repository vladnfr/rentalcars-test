import java.util.*;

// class to represent a vehicle offer (each object in the json can be captured
// by an instance of this class)
// provides various methods that facilitate the tasks
public class VehicleOffer
{
  private String sipp;
  private String name;
  private float price;
  private String supplier;
  private float rating;

  private static final Map<String, String> vehicleTypes = new HashMap<String, String>();
  static
  {
    vehicleTypes.put("M", "Mini");
    vehicleTypes.put("E", "Economy");
    vehicleTypes.put("C", "Compact");
    vehicleTypes.put("I", "Intermediate");
    vehicleTypes.put("S", "Standard");
    vehicleTypes.put("F", "Full Size");
    vehicleTypes.put("P", "Premium");
    vehicleTypes.put("L", "Luxury");
    vehicleTypes.put("X", "Special");
  }

  public static Map<String, String> getVehicleTypesMap()
  {
    return vehicleTypes;
  }

  private static final Map<String,String> doors= new HashMap<String,String>();
  static
  {
    doors.put("B", "2 doors");
    doors.put("C", "4 doors");
    doors.put("D", "5 doors");
    doors.put("W", "Estate");
    doors.put("T", "Convertible");
    doors.put("F", "SUV");
    doors.put("P", "Pick up");
    doors.put("V", "Passenger Van");
    doors.put("X", "Special");
  }

  public VehicleOffer(String reqSipp, String reqName, float reqPrice,
                      String reqSupplier, float reqRating)
  {
    sipp = reqSipp;
    name = reqName;
    price = reqPrice;
    supplier = reqSupplier;
    rating = reqRating;
  }

  public String getSipp()
  {
    return sipp;
  }

  public String getName()
  {
    return name;
  }

  public float getPrice()
  {
    return price;
  }

  public String getSupplier()
  {
    return supplier;
  }

  public float getRating()
  {
    return rating;
  }

  public String getType()
  {
    return vehicleTypes.get(sipp.substring(0,1));
  }

  public String getDoors()
  {
    return doors.get(sipp.substring(1,2));
  }

  public String getTransmission()
  {
    boolean isAutomatic = sipp.substring(2,3).equals("A");
    return isAutomatic ? "Automatic" : "Manual";
  }

  public String getFuel()
  {
    return "Petrol";
  }

  public String getAc()
  {
    boolean hasAc = sipp.substring(3,4).equals("R");
    return hasAc ? "AC" : "no AC";
  }

  public String getSpecification()
  {
    return this.getName() + " - " + this.getSipp() + " - " + this.getType() +
           " - " + this.getDoors() + " - " + this.getTransmission() + " - " +
           this.getFuel() + " - " + this.getAc();
  }

  public short getScore()
  {
    // init to 1 or 5, based on whether it has manual or automatic gear
    short score = this.getSipp().substring(2,3).equals("A") ? (short)5:(short)1;
    if (this.getSipp().substring(3,4).equals("R"))
    {
      score += 2;
    }
    return score;
  }
}
