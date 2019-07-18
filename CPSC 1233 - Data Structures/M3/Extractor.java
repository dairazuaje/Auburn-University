import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Extractor.java. Implements feature extraction for collinear points in
 * two dimensional data.
 *
 * @author  Dair Azuaje (dza@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version TODAY
 *
 */
public class Extractor {
   
   /** raw data: all (x,y) points from source data. */
   private Point[] points;
   
   /** lines identified from raw data. */
   private SortedSet<Line> lines;
  
   /**
    * Builds an extractor based on the points in the file named by filename. 
    */
   public Extractor(String filename) {
      
      try {
         //Sets up Scanner to read from file passed in
         Scanner in = new Scanner(new File(filename));
         
         //Reads file length from file
         int length = in.nextInt();
         
         //Creates array based on size from file
         points = new Point[length];
         int index = 0;
         
         //Reads x and y values from file and creates a Point to add to Points array
         while (in.hasNextLine()) {
            int x = in.nextInt();
            int y = in.nextInt();
            points[index] = new Point(x,y);
            index++;
         }
         in.close();
      }
      catch (Exception e) {
         System.out.println("File not found");
      }
   }
  
   /**
    * Builds an extractor based on the points in the Collection named by pcoll. 
    *
    * THIS METHOD IS PROVIDED FOR YOU AND MUST NOT BE CHANGED.
    */
   public Extractor(Collection<Point> pcoll) {
      points = pcoll.toArray(new Point[]{});
   }
  
   /**
    * Returns a sorted set of all line segments of exactly four collinear
    * points. Uses a brute-force combinatorial strategy. Returns an empty set
    * if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesBrute() {
      Line ptline = new Line();
      lines = new TreeSet<Line>();
      for (int i = 0; i < points.length; i ++) {
         for (int j = 0; j < points.length; j++) { 
            for (int k = 0; k < points.length; k++) { 
               for (int l = 0; l < points.length; l++) { 
                  ptline.add(points[i]);
                  ptline.add(points[j]);
                  ptline.add(points[k]);
                  ptline.add(points[l]);
                 
                  if (ptline.length() == 4) { 
                     lines.add(ptline);
                  }
                  ptline = new Line();
               }
            }
         }       
      }
      return lines;
   }
  
   /**
    * Returns a sorted set of all line segments of at least four collinear
    * points. The line segments are maximal; that is, no sub-segments are
    * identified separately. A sort-and-scan strategy is used. Returns an empty
    * set if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesFast() {
	   Point[] copy = Arrays.copyOf(points, points.length);
      lines = new TreeSet<Line>();
      Line ptline = new Line(); 
      
      for (int i = 0; i < points.length; i++) { 
         Arrays.sort(copy, points[i].slopeOrder);
         for (int j = 0; j < copy.length; j++) {
            ptline.add(copy[0]);
            if (ptline.add(copy[j])) {
            	ptline.add(copy[j]);
            }
            else {
            	if (ptline.length() >= 4) {
            	  	lines.add(ptline);
            	}
            	ptline = new Line();
            	ptline.add(copy[j]);
            }
         }
      }
	   return lines;
   }
}
