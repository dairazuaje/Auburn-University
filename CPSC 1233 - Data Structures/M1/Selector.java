import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;


/**
 * Provides an implementation of the WordLadderGame interface. The lexicon
 * is stored as a HashSet of Strings.
 *
 * @author Your Name (you@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2018-04-06
 */
public class Doublets implements WordLadderGame {

    // The word list used to validate words.
    // Must be instantiated and populated in the constructor.
   private HashSet<String> lexicon;
   private final List<String> EMPTY_LADDER = new ArrayList<>();

    /**
     * Instantiates a new instance of Doublets with the lexicon populated with
     * the strings in the provided InputStream. The InputStream can be formatted
     * in different ways as long as the first string on each line is a word to be
     * stored in the lexicon.
     */
   public Doublets(InputStream in) {
      try {
         lexicon = new HashSet<String>();
         Scanner s = new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next();
                /////////////////////////////////////////////////////////////
                // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
                /////////////////////////////////////////////////////////////
            lexicon.add(str.toLowerCase());
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }

    //////////////////////////////////////////////////////////////
    // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
    //////////////////////////////////////////////////////////////

    /**
     * Returns the Hamming distance between two strings, str1 and str2. The
     * Hamming distance between two strings of equal length is defined as the
     * number of positions at which the corresponding symbols are different. The
     * Hamming distance is undefined if the strings have different length, and
     * this method returns -1 in that case. See the following link for
     * reference: https://en.wikipedia.org/wiki/Hamming_distance
     *
     * @param  str1 the first string
     * @param  str2 the second string
     * @return      the Hamming distance between str1 and str2 if they are the
     *                  same length, -1 otherwise
     */
   @Override
   public int getHammingDistance(String str1, String str2) {
   	// TODO Auto-generated method stub
      if (str1.length() != str2.length()) {
    	  return -1;
      }
     int hammingDistance = 0;
     for (int i = 0; i < str1.length(); i++) {
        if (!(str1.charAt(i) == str2.charAt(i))) {
           hammingDistance++;
        }
     }
      
      return hammingDistance;
      
   }

	/**
     * Returns all the words that have a Hamming distance of one relative to the
     * given word.
     *
     * @param  word the given word
     * @return      the neighbors of the given word
     */
   @Override
   public List<String> getNeighbors(String word) {
   	// TODO Auto-generated method stub
	  if (word == null) {
		  return EMPTY_LADDER;
	  }
      List<String> neighbors = new ArrayList<String>();
      for (String element: lexicon) {
         if (getHammingDistance(word, element) == 1) {
            neighbors.add(element);
         }
      }
      return neighbors;
   }

	/**
     * Returns the total number of words in the current lexicon.
     *
     * @return number of words in the lexicon
     */
   @Override
   public int getWordCount() {
   	// TODO Auto-generated method stub
      return lexicon.size();
   
   }

	/**
     * Checks to see if the given string is a word.
     *
     * @param  str the string to check
     * @return     true if str is a word, false otherwise
     */
   @Override
   public boolean isWord(String str) {
   	// TODO Auto-generated method stub
      if (lexicon.contains(str)) {
         return true;
      }
      return false;
   }

	/**
     * Checks to see if the given sequence of strings is a valid word ladder.
     *
     * @param  sequence the given sequence of strings
     * @return          true if the given sequence is a valid word ladder,
     *                       false otherwise
     */
   @Override
   public boolean isWordLadder(List<String> sequence) {
   	// TODO Auto-generated method stub
      if (sequence == null || sequence.isEmpty()) {
         return false;		
      }

      for (int i = 0; i < sequence.size() - 1; i++) {
         if (getHammingDistance(sequence.get(i), sequence.get(i + 1)) != 1 || !isWord(sequence.get(i)) || !isWord(sequence.get(i + 1))) {
            return false;
         }
      }
      return true;
   }
	
	/**
	    * Returns a minimum-length word ladder from start to end. If multiple
	    * minimum-length word ladders exist, no guarantee is made regarding which
	    * one is returned. If no word ladder exists, this method returns an empty
	    * list.
	    *
	    * Breadth-first search must be used in all implementing classes.
	    *
	    * @param  start  the starting word
	    * @param  end    the ending word
	    * @return        a minimum length word ladder from start to end
	    */
   @Override
   public List<String> getMinLadder(String start, String end) {
	  // Word ladder that will be returned if exists
	  List<String> minLadder = new ArrayList<String>();
//	  String start1 = start.toUpperCase(); // match case
//      String end1 = end.toUpperCase(); // match case
      // If start and end word are the same, add to ladder and return ladder
	// If words are not equal length, no possible ladder so return the empty ladder
      if (start.length() != end.length()) {
         return EMPTY_LADDER;
      }
      
   // If neither word is in the lexicon return empty ladder
      if (!isWord(start) || !isWord(end)) {
         return EMPTY_LADDER;
      }
      
      String cases = "";
	  if (start.toUpperCase() != start) {
		  cases = "lowercase";
	  } else {
		  cases = "uppercase";
	  }
      if (start.equalsIgnoreCase(end)) {
    	if (cases == "lowercase") {
    		minLadder.add(start.toLowerCase());
         return minLadder;
    	} else {
    		minLadder.add(start.toUpperCase());
         return minLadder;
    	}
    	
      }
      
      // Execute search if we get to this point
      minLadder = bfsMemory(start,end,cases);
      return minLadder;
  }      
	
   private List<String> bfsMemory(String start, String end, String cases) {
      List<String> ladder = new ArrayList<String>();
      Deque<Node> queue = new ArrayDeque<>();
      HashSet<String> visited = new HashSet<>();
      
      // Adds start word to visited HashSet
      visited.add(start.toUpperCase());
      
      // Adds start word to queue and sets previous to null
      queue.addLast(new Node(start, null));
      
      while (!queue.isEmpty()) {
         
    	 // Pulls word from queue, makes it a node and gets word from node
         Node n = queue.removeFirst();
         String word = n.word;
         
         // Gets all neighbors of start word
         List<String> neighbors = getNeighbors(word);
         
         for (String neighbor : neighbors) {
        	// If neighbor has not been searched, add to visited and queue
            if (!visited.contains(neighbor)) {
               visited.add(neighbor);
               queue.addLast(new Node(neighbor, n));
            }
            
            // If neighbor equals end word, remove last queue entry and create new Node list. This is our backwards wordLadder
            if (neighbor.equals(end)) {
             
               Node wordLadder = queue.removeLast();
               if (cases == "lowercase") {
            	   while (wordLadder != null) {
                       ladder.add(0, wordLadder.word.toLowerCase());
                       wordLadder = wordLadder.previous;
                    }
               } else {
            	   while (wordLadder != null) {
                       ladder.add(0, wordLadder.word.toUpperCase());
                       wordLadder = wordLadder.previous;
                    }
               }
               // Traverse list backwards until null is hit, with each traversal add word at node to from of ladder
               
               return ladder;
            }
         }
      }      
      return EMPTY_LADDER;

   }
	
   public class Node {
      private Node previous;
      private String word;
   	
   	
      public Node(String word, Node prev) {
         this.word = word;
         previous = prev;
      }
   
   }
}

