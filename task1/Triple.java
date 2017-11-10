// class that define a triple of elements and provides methods to get each one
public class Triple<F,S,T>
{
  private final F first;
  private final S second;
  private final T third;
  public Triple(F firstReq, S secondReq, T thirdReq)
  {
    first = firstReq;
    second = secondReq;
    third = thirdReq;
  }

  public F getFirst()
  {
    return first;
  }

  public S getSecond()
  {
    return second;
  }

  public T getThird()
  {
    return third;
  }
}
