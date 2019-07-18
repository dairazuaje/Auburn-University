import java.util.Arrays;

public class TestKmin {

   public static void main(String[] args) {
      // int[] arr = {1,2,3,4,5};
      // int[] arr = {1,3,3,4,5,7,7,8,9};
      // int[] arr = {2,8,7,3,4};
      // int[] arr = {-3, -7, -3, -3, -1, -9, -1, -1, -1, -5};
      int[] arr = {5,9,1,7,3};
       
      int result = mink(arr,3);
      System.out.println(result);
   }
   
   public static int mink(int[] a, int k) {
      
      //Checks to see if arguments are valid
      if ((a == null) || (a.length == 0) || (k > a.length)) {
         throw new IllegalArgumentException("a must not be null or have a length of 0.");
      }
      
      //Checks if arr.length = 1
      if (a.length == 1) {
         return a[a.length - 1];
      }
      //Copy a[] and sort in ascending order
      int[] copy = Arrays.copyOf(a, a.length);
      Arrays.sort(copy);
      int uniqueCount = 0;
      int[] uniqueArrTemp = new int[a.length];
      
      //Determine quantity of unique numbers and add those to array
      for (int i = 0; i < copy.length; i++) {
         if (!((i + 1) >= copy.length)) {
            if (copy[i] != copy[i + 1]) {
               uniqueArrTemp[uniqueCount] = copy[i];
               uniqueCount++;
            }
         }
         else { 
            uniqueArrTemp[uniqueCount] = copy[i];
            uniqueCount++;
         }
      }
      if (uniqueCount < k) {
         throw new IllegalArgumentException("Invalid Argument");
      }
      
      if (uniqueCount == 0) {
         return copy[uniqueCount];
      }
      //If uniqueCount == copy.length, meaning all are unique, go into loop that assess copy array
      if (uniqueCount == copy.length) {
         int valueInCopy = copy[k - 1]; //Value being tested for kmax in copy array since all were unique
         int valueIndex = k - 1; //Index of value being tested at kmax in copy array since all were unique
         int numsLesser = 0; //Count of numbers greater than kmax
         for (int i = valueIndex; i >= 0; i--) {
            if (valueInCopy > copy[i]) {
               numsLesser++;
            }  
         }
         if (numsLesser == (k - 1)) {
            return copy[valueIndex];
         }
      
         throw new IllegalArgumentException("None found");
      }
      
      //Create a new array the size of the unique numbers and use a for loop to add them in from temporary unique array. 
      int[] uniqueNumbers = new int[uniqueCount];
      
      for (int j = 0; uniqueArrTemp[j] != 0; j++) {
         uniqueNumbers[j] = uniqueArrTemp[j]; //This works
      }
      
      //Assess array to make sure number is kmin
      int value = uniqueNumbers[k - 1]; //Value being tested for kmin in uniqueNumbers array
      int valueIndex = k - 1; //Index of value being tested at kmin in UniqueNumbers array
      int numsLesser = 0; //Count of numbers greater than kmin
      for (int l = valueIndex; l >= 0; l--) {
         if (value > uniqueNumbers[l]) {
            numsLesser++;
         }
      }
      
      if (numsLesser == (k - 1)) {
         return uniqueNumbers[valueIndex];
      }
      
      throw new IllegalArgumentException("None found");
   }
}