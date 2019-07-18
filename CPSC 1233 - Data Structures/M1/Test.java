import java.util.Arrays;

public class Test {

   public static void main(String[] args) {
      // int[] arr = {1,2,3,4,5};
      // int[] arr = {1,3,3,4,5,7,7,8,9};
      // int[] arr = {2,8,7,3,4};
      int[] arr = {-3, -7, -3, -3, -1, -9, -1, -1, -1, -5};
       
      int result = maxk(arr,1);
      System.out.println(result);
   }
   
   public static int maxk(int[] a, int k) {
      
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
         int valueInCopy = copy[copy.length - k]; //Value being tested for kmax in copy array since all were unique
         int valueIndex = copy.length - k; //Index of value being tested at kmax in copy array since all were unique
         int numsGreater = 0; //Count of numbers greater than kmax
         for (int i = (copy.length - 1); i > valueIndex; i--) {
            if (valueInCopy < copy[i]) {
               numsGreater++;
            }  
         }
         if (numsGreater == (k - 1)) {
            return copy[(copy.length - 1) - numsGreater];
         }
      
         throw new IllegalArgumentException("None found");
      }
      
      //Create a new array the size of the unique numbers and use a for loop to add them in from temporary unique array. 
      int[] uniqueNumbers = new int[uniqueCount];
      
      for (int j = 0; uniqueArrTemp[j] != 0; j++) {
         uniqueNumbers[j] = uniqueArrTemp[j]; //This works
      }
      
      //Assess array to make sure number is kmax
      int value = uniqueNumbers[uniqueNumbers.length - k]; //Value being tested for kmax in uniqueNumbers array
      int valueIndex = uniqueNumbers.length - k; //Index of value being tested at kmax in UniqueNumbers array
      int numsGreater = 0; //Count of numbers greater than kmax
      for (int l = (uniqueNumbers.length - 1); l > valueIndex; l--) {
         if (value < uniqueNumbers[l]) {
            numsGreater++;
         }
      }
      
      if (numsGreater == (k - 1)) {
         return uniqueNumbers[(uniqueNumbers.length - 1) - numsGreater];
      }
      
      throw new IllegalArgumentException("None found");
   }
}