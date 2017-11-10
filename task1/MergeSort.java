// THIS PROGRAM IS INSPIRED FROM ONE FOUND ON THE INTERNET
import java.util.*;

public class MergeSort {

    private ArrayList<VehicleOffer> strList;

    public MergeSort(ArrayList<VehicleOffer> input)
    {
      strList = input;
    }

    public void sort()
    {
      strList = mergeSort(strList);
    }

    public ArrayList<VehicleOffer> mergeSort(ArrayList<VehicleOffer> arr)
    {
      ArrayList<VehicleOffer> l = new ArrayList<VehicleOffer>();
      ArrayList<VehicleOffer> r = new ArrayList<VehicleOffer>();
      int m;

      if (arr.size() == 1)
      {
        return arr;
      }
      else
      {
        m = arr.size()/2;
        for (int i=0; i<m; i++)
        {
          l.add(arr.get(i));
        }

        for (int i=m; i<arr.size(); i++)
        {
          r.add(arr.get(i));
        }

        l  = mergeSort(l);
        r = mergeSort(r);
        merge(l, r, arr);
      }
      return arr;
    }

    private void merge(ArrayList<VehicleOffer> l, ArrayList<VehicleOffer> r,
       ArrayList<VehicleOffer> arr)
    {
      int li = 0;
      int ri = 0;
      int wholeIndex = 0;

      while (li < l.size() && ri < r.size())
      {
        if (l.get(li).getScore() + l.get(li).getRating() >=
          r.get(ri).getScore() + r.get(ri).getRating())
        {
          arr.set(wholeIndex, l.get(li));
          li++;
        }
        else
        {
          arr.set(wholeIndex, r.get(ri));
          ri++;
        }
        wholeIndex++;
      }

      ArrayList<VehicleOffer> rest;
      int restIndex;
      if (li >= l.size())
      {
        rest = r;
        restIndex = ri;
      }
      else
      {
        rest = l;
        restIndex = li;
      }

      for (int i=restIndex; i<rest.size(); i++)
      {
        arr.set(wholeIndex, rest.get(i));
        wholeIndex++;
      }
    }
    public ArrayList<VehicleOffer> getSorted()
    {
      this.sort();
      return strList;
    }
  }
