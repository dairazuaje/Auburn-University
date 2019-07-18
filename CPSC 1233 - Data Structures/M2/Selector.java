import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines a library of selection methods on Collections.
 *
 * @author  Dair Azuaje (dza0042@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version TODAY
 *
 */
public final class Selector {

/**
 * Can't instantiate this class.
 *
 * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
 *
 */
   private Selector() { }


   /**
    * Returns the minimum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the minimum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T min(Collection<T> coll, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException("Collection cannot be null");
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Collection is empty");
      }
      
      Iterator<T> iterator = coll.iterator();
      
      T min = iterator.next();
      
      while (iterator.hasNext()) {
         T currentItem = iterator.next();
         if (comp.compare(currentItem, min) < 0) {
            min = currentItem;
         }
      }
      return min;
   }


   /**
    * Selects the maximum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the maximum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T max(Collection<T> coll, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException("Collection cannot be null");
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Collection is empty");
      }
      
      Iterator<T> iterator = coll.iterator();
      T max = iterator.next();
      
      while (iterator.hasNext()) {
         T currentItem = iterator.next();
         if (comp.compare(currentItem, max) > 0) {
            max = currentItem;
         }
      }
      return max;
   }


   /**
    * Selects the kth minimum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth minimum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth minimum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T kmin(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException("Collection cannot be null");
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Collection is empty");
      }
      
      List<T> list = new ArrayList<T>(coll);
      java.util.Collections.sort(list, comp);
      
      
      for (int i = 0; i < list.size(); i++) {
         if (!((i + 1) >= list.size())) {
            if (comp.compare(list.get(i), list.get(i + 1)) == 0) {
               list.remove(list.get(i));
               i--;
            }
         }
      }
      
      if (list.size() < k || k <= 0) {
         throw new NoSuchElementException("Size must be larger than k");
      }
      
      T kValue = list.get(k - 1);
      int kValueIndex = k - 1;
      int numsLesser = 0;
      
      for (int j = 0; j < kValueIndex; j++) {
         if (comp.compare(list.get(j), kValue) < 0) {
            numsLesser++;
         }
      }
      
      if (numsLesser == k - 1) {
         return kValue;
      }
      
      throw new NoSuchElementException("Element was not found in collection");
   }


   /**
    * Selects the kth maximum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth maximum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth maximum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T kmax(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException("Collection cannot be null");
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Collection is empty");
      }
      
      List<T> list = new ArrayList<T>(coll);
      java.util.Collections.sort(list, comp);
      
      //Need to update because if there are duplicates list does not come out right
      for (int i = 0; i < list.size(); i++) {
         if (!((i + 1) >= list.size())) {
            if (comp.compare(list.get(i), list.get(i + 1)) == 0) {
               list.remove(list.get(i));
               i--;
            }
         }
      }
      
      for (T element: list) {
         System.out.print(element + " ,");
      }
      
      if (list.size() < k || k <= 0) {
         throw new NoSuchElementException("Size must be larger than k");
      }
      
      T kValue = list.get(list.size() - k);
      int kValueIndex = list.size() - k;
      int numsGreater = 0;
      
      for (int j = kValueIndex + 1; j < list.size(); j++) {
         if (comp.compare(list.get(j), kValue) > 0) {
            numsGreater++;
         }
      }
      
      //System.out.println("Nums greater = " + numsGreater);
      if (numsGreater == k - 1) {
         return kValue;
      }
      //return null;
      throw new NoSuchElementException("Element was not found in collection");
   }


   /**
    * Returns a new Collection containing all the values in the Collection coll
    * that are greater than or equal to low and less than or equal to high, as
    * defined by the Comparator comp. The returned collection must contain only
    * these values and no others. The values low and high themselves do not have
    * to be in coll. Any duplicate values that are in coll must also be in the
    * returned Collection. If no values in coll fall into the specified range or
    * if coll is empty, this method throws a NoSuchElementException. If either
    * coll or comp is null, this method throws an IllegalArgumentException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the range values are selected
    * @param low     the lower bound of the range
    * @param high    the upper bound of the range
    * @param comp    the Comparator that defines the total order on T
    * @return        a Collection of values between low and high
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> Collection<T> range(Collection<T> coll, T low, T high, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException("Collection cannot be null");
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Collection is empty");
      }
      
      Iterator<T> iterator = coll.iterator();
      Collection<T> inRange = new ArrayList<T>();
      
      while (iterator.hasNext()) {
         T current = iterator.next();
         
         if ((comp.compare(current, low) >= 0) && (comp.compare(current, high) <= 0)) {
            inRange.add(current);
         }
      }
      
      if (inRange.isEmpty()) {
         throw new NoSuchElementException("No values found in range");
      }
      
      return inRange;
   }


   /**
    * Returns the smallest value in the Collection coll that is greater than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the ceiling value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the ceiling value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T ceiling(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException("Collection cannot be null");
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Collection is empty");
      }
      
      Iterator<T> iterator = coll.iterator();
      T minPossible = null;
      
      while (iterator.hasNext()) {
         T currentItem = iterator.next();
         
         if ((comp.compare(currentItem, key)) >= 0) {
            minPossible = currentItem;
            
            while (iterator.hasNext()) {
               T currentInnerLoop = iterator.next();
               if ((comp.compare(currentInnerLoop, minPossible) <= 0) && (comp.compare(currentInnerLoop, key) >= 0)) {
                  minPossible = currentInnerLoop;
               }
            }
         }
      }
      
      if (minPossible == null) {
         throw new NoSuchElementException("No element found in collection");
      }
      return minPossible;
   }


   /**
    * Returns the largest value in the Collection coll that is less than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the floor value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the floor value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T floor(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null) {
         throw new IllegalArgumentException("Collection cannot be null");
      }
      
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Collection is empty");
      }
      
      Iterator<T> iterator = coll.iterator();
      T maxPossible = null;
      
      while (iterator.hasNext()) {
         T currentItem = iterator.next();
         
         if ((comp.compare(currentItem, key)) <= 0) {
            maxPossible = currentItem;
            
            while (iterator.hasNext()) {
               T currentInnerLoop = iterator.next();
               if ((comp.compare(currentInnerLoop, maxPossible) >= 0) && (comp.compare(currentInnerLoop, key) <= 0)) {
                  maxPossible = currentInnerLoop;
               }
            }
         }
      }
      if (maxPossible == null) {
         throw new NoSuchElementException("No element found in collection");
      }
      return maxPossible;
   }

}
