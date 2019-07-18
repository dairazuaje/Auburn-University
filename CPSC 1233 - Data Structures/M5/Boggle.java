import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Boggle implements WordSearchGame {
   private boolean lexiconLoaded;
   private TreeSet<String> lexicon;
   private String[][] board;
   private boolean[][] visited;
   private int width;
   private int height;
   private final int MAX_NEIGHBORS = 8;

   
   
   public Boggle() {
      lexiconLoaded = false;
   }
   
   /**
    * Loads the lexicon into a data structure for later use. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName) {
      
      //If fileName is null throw IllegalArgumentException
      if (fileName == null) {
         throw new IllegalArgumentException("FileName was null");
      }
      try {
         Scanner in = new Scanner(new File(fileName));
         lexicon = new TreeSet<String>();
         while (in.hasNext()) {
            String word = in.next();
            lexicon.add(word.toUpperCase());
            in.nextLine();
         }
         in.close();
      } 
      catch (Exception e) {
         throw new IllegalArgumentException("File could not be opened");
      }
      
      //If file can be opened and read set to true
      lexiconLoaded = true;   
   }
   
   /**
    * Stores the incoming array of Strings in a data structure that will make
    * it convenient to find words.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    *     game board in row-major order. Thus, index 0 stores the contents of board
    *     position (0,0) and index length-1 stores the contents of board position
    *     (N-1,N-1). Note that the board must be square and that the strings inside
    *     may be longer than one character.
    * @throws IllegalArgumentException if letterArray is null, or is  not
    *     square.
    */
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException("LetterArray is null");
      }
      
      if (letterArray.length % Math.sqrt(letterArray.length) != 0) {
         throw new IllegalArgumentException("LetterArray is not a square");
      }
      
      int dimension = (int) Math.sqrt(letterArray.length);
      height = dimension;
      width = dimension;
      
      board = new String[height][width];
      
      
      int index = 0;
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < width; j++) {
            board[i][j] = letterArray[index];
            index++;
         }
      }   
   }
   
   /**
    * Creates a String representation of the board, suitable for printing to
    *   standard out. Note that this method can always be called since
    *   implementing classes should have a default board.
    */
   public String getBoard() {
      String result = "";
      
      int index = 0;
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < width; j++) {
            if (j == width - 1) {
            	result+= board[i][j] + "\n";
            } else {
            	result+= board[i][j];
            }
            index++;
         }
      }
	  return result;    
   }
   
   /**
    * Retrieves all valid words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllValidWords(int minimumWordLength) {
      //Throw IllegalArgumentException if minimumWordLegth is less than 1
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException("Minimum word lenth cannot be less than 1");
      }
      
      //Throw IllegalArgumentException if Lexicon has not been loaded
      if (lexiconLoaded == false) {
         throw new IllegalStateException("Lexicon was not loaded");
      }
      
      Iterator<String> itr = lexicon.iterator();
      SortedSet<String> words = new TreeSet<String>();
      while (itr.hasNext()) {
    	  String word = itr.next();
    	  if (word.length() >= minimumWordLength) {
    		  if (!isOnBoard(word).isEmpty()) {
    			  words.add(word);
    		  }
    	  }
      }
      return words;
   }
   
  /**
   * Computes the cummulative score for the scorable words in the given set.
   * To be scorable, a word must (1) have at least the minimum number of characters,
   * (2) be in the lexicon, and (3) be on the board. Each scorable word is
   * awarded one point for the minimum number of characters, and one point for 
   * each character beyond the minimum number.
   *
   * @param words The set of words that are to be scored.
   * @param minimumWordLength The minimum number of characters required per word
   * @return the cummulative score of all scorable words in the set
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */  
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
	   
	  if (lexiconLoaded == false) {
	         throw new IllegalArgumentException("Lexicon was not loaded");
	  }
	  
      if (minimumWordLength < 1) {
         throw new IllegalStateException("Minimum word lenth cannot be less than 1");
      }
      
      
      int score = 0;
      for (String element: words) {
    	  if (element.length() >= minimumWordLength && isValidWord(element)) {
    		  score += 1 + (element.length() - minimumWordLength);
    	  }
      }
      return score;
   }
   
   /**
    * Determines if the given word is in the lexicon.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidWord(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException("wordToCheck is null");
      }
      
      if (lexiconLoaded == false) {
         throw new IllegalArgumentException("Lexicon was not loaded");
      }
      
      return lexicon.contains(wordToCheck);
   }
   
   /**
    * Determines if there is at least one word in the lexicon with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException("wordToCheck is null");
      }
      
      if (lexiconLoaded == false) {
         throw new IllegalStateException("Lexicon was not loaded");
      }
      String word = lexicon.ceiling(prefixToCheck);
      if (word != null) {
          return word.startsWith(prefixToCheck);
       }
       return false;
   }
      
   /**
    * Determines if the given word is in on the game board. If so, it returns
    * the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with  the path
    *     that makes up the word on the game board. If word is not on the game
    *     board, return an empty list. Positions on the board are numbered from zero
    *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
    *     board, the upper left position is numbered 0 and the lower right position
    *     is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException("wordToCheck is null");
      }
      
      if (lexiconLoaded == false) {
         throw new IllegalStateException("Lexicon was not loaded");
      }
      
      visited = new boolean[height][width];
      List<Integer> path = new ArrayList<Integer>();
      
      String currentWord = "";
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < width; j++) {
            if (wordToCheck.startsWith(board[i][j])) {
               findWord(i, j, wordToCheck, currentWord, path);
            }
         }
      }
      return path;
   }
   
   //DFS to find word
   public boolean findWord(int x, int y, String wordToCheck, String currentWord, List<Integer> path) {
      
	  Position start = new Position(x,y);
	  
	  visit(start);
      currentWord += board[start.x][start.y];
      int p = start.x * width + start.y;
      path.add(p);
      if ((currentWord.toString().equals(wordToCheck))) {
     	 return true; 
      }

      if (!wordToCheck.startsWith(currentWord)) {
    	  path.remove(path.size() - 1);
    	  visited[start.x][start.y] = false;
    	  //currentWord = currentWord.substring(0, currentWord.length());
		  return false;
	  }

      Position[] validNeighbors = start.neighbors();
      for (Position n: validNeighbors) {
    	  if (!isVisited(n)) {
    		  if (findWord(n.x, n.y, wordToCheck, currentWord, path)) {
    			  return true;
    		  }
    	  }  
      }
      path.remove(path.size() - 1);
	  visited[start.x][start.y] = false;
     return false;
  }   
   
   private class Position {
	   int x;
	   int y;
	   
	   public Position(int x, int y) {
		   this.x = x;
		   this.y = y;
	   }
	   
	   public Position[] neighbors() {
		   Position[] nbrs = new Position[MAX_NEIGHBORS];
		   int count = 0;
		   Position p;
		   for (int i = -1; i <= 1; i++) {
			   for (int j = -1; j <= 1; j++) {
				   if (!((i == 0) && (j == 0))) {
					   p = new Position(x + i, y + j);
					   if (isValid(p)) {
						   nbrs[count++] = p;
					   }
				   }
			   }
		   }
		   return Arrays.copyOf(nbrs, count);
	   }
   }
   private void visit(Position p) {
	   visited[p.x][p.y] = true;
   }
   
   private boolean isVisited(Position p) {
	   return visited[p.x][p.y];
   }
   
   private boolean isValid(Position p) {
	   return (p.x >= 0) && (p.x < width) &&
	   (p.y >= 0) && (p.y < height);
   }
}
