import java.util.Iterator;

/**
 * ListFactory.java.
 * Implements the factory method pattern (https://en.wikipedia.org/wiki/Factory_method_pattern)
 * for lists in this assignment.
 *
 * @author YOUR NAME (YOU@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version TODAY
 */
public class ListFactory {

   /**
    * Return an instance of a class that implements RandomizedList.
    */
   public static <T> RandomizedList<T> makeRandomizedList() {
      // Replace the following return statement with one that returns
      // an instance of the class you wrote to implement the
      // RandomizedList interface.
      return new RandomList<T>();
   }

   /**
    * Return an instance of a class that implements DoubleEndedList.
    */
   public static <T> DoubleEndedList<T> makeDoubleEndedList() {
      // Replace the following return statement with one that returns
      // an instance of the class you wrote to implement the
      // DoubleEndedList interface.
      return new NodeList<T>();
   }
   @SuppressWarnings("unchecked")
   public static void main(String[] args) {
      RandomizedList list = makeRandomizedList();
      
      
      list.add("A");
      list.add("B");
      list.add("C");
      
      Iterator itr = list.iterator();
      while (itr.hasNext()) {
         System.out.println(itr.next());
      }
   }
}
