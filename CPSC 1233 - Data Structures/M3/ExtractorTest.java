import java.util.Arrays;
import java.util.SortedSet;

public class ExtractorTest {
   public static void main(String[] args) {
      String file = "input.txt";
      Extractor cl = new Extractor(file);
      SortedSet<Line> bruteLines = cl.getLinesBrute();
      System.out.println("Brute: ");
      printLines(bruteLines);
   
      SortedSet<Line> fastLines = cl.getLinesFast();
      System.out.println("Fast: ");
      printLines(fastLines);
   }


   private static void printLines(SortedSet<Line> lines) {
      int count = 0;
      for (Line line : lines) {
         System.out.println(++count + ": " + line);
      }
      System.out.println();
   }
}