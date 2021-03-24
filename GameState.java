/**
* Filename: GameState.java
* @author Aryan Pareek
* Login: cs8bwi20jn
* Date: 2/6/2020
* Sources of Help: Course notes and textbook
*
* This file is designed to demonstrate my knowledge with objects, implementing
* methods, logic regarding 2D arrays, and creating a class from scratch.
*/

/**
* This class is used to implement the methods necessary to make the game 2048,
* including getting and setting the board, getting and setting the score,
* creating and adding random tiles, counting empty tiles, rotating the board,
* and performing moves and slides.
*/
import java.util.*;

public class GameState
{
  private Random rng;
  private int[][] board;
  private int score;

  // REQUIRED - put this in your GameState.java
  public String toString()
  {
    StringBuilder outputString = new StringBuilder();
    outputString.append(String.format("Score: %d\n", getScore()));
    for (int row = 0; row < getBoard().length; row++)
    {
      for (int column = 0; column < getBoard()[0].length; column++)
      {
        outputString.append(getBoard()[row][column] == 0 ? "    -" :
        String.format("%5d", getBoard()[row][column]));
      }
      outputString.append("\n");
    }
    return outputString.toString();
  }

  /**
    * Constructor that takes in two ints and assigns them as the numRows append
    * numCols of the board
    *
    * @param numRows   The number of rows in the board
    * @param numCols The number of columns in the board
    */
  public GameState(int numRows, int numCols)
  {
    //initializes the variables
    board = new int[numRows][numCols];
    score = 0;
  }

  /**
    * Method which returns a deep copied 2D array of the board
    *
    * @return the deep copied 2D array
    */
  public int[][] getBoard()
  {
    //makes a copyBoard with the same dimensions
    int[][] copyBoard = new int[this.board.length][this.board[0].length];
    for (int r = 0 ; r < this.board.length ; r++)
    {
      for (int c = 0 ; c < this.board[0].length ; c++)
      {
        if (this.board[r][c] != 0)
        {
          //copy if the space isn't empty
          copyBoard[r][c] = this.board[r][c];
        }
        else
        {
          //otherwise, input a 0
          copyBoard[r][c] = 0;
        }
      }
    }
    //return copyBoard
    return copyBoard;
  }

  /**
    * Method which takes a 2D array and sets the gameBoards equal to the copied
    * newBoard
    *
    * @param newBoard the 2D array being passed in to set
    */
  public void setBoard(int[][] newBoard)
  {
    //check null case
    if (newBoard == null)
    {
      return;
    }
    //create a new copyBoard with the proper dimensions
    int[][] copyBoard = new int[newBoard.length][newBoard[0].length];
    for (int r = 0 ; r < newBoard.length ; r++)
    {
      for (int c = 0 ; c < newBoard[0].length ; c++)
      {
        if (newBoard[r][c] != 0)
        {
          //input the newBoard element into copyBoard if the element isnt empty
          copyBoard[r][c] = newBoard[r][c];
        }
        else
        {
          //otherwise, input 0
          copyBoard[r][c] = 0;
        }
      }
    }
    //set the calling object board to the copyBoard
    this.board = copyBoard;
  }

  /**
    * Method which returns the score
    *
    * @return score
    */
  public int getScore()
  {
    //return score
    return score;
  }

  /**
    * Method which sets the score
    *
    * @param newScore the score to be set
    */
  public void setScore(int newScore)
  {
    //set score
    score = newScore;
  }

  /**
    * Method which takes a bound and generates a random number from 0 to that
    * bound
    *
    * @param bound the max number that can be generated
    * @return the random int generated
    */
  protected int rollRNG(int bound)
  {
    //instance of random class
    rng = new Random();
    //generate a random int from 0 to the bound
    int random = rng.nextInt(bound);
    //return random number
    return random;
  }

  /**
    * Method which generates a random tile
    *
    * @return the random int generated based on predetermined probability
    */
  protected int randomTile()
  {
    //instance of random class
    rng = new Random();
    //generate a random int from 0 to 100
    int random = rng.nextInt(100);
    if (random < Config.TWO_PROB)
    {
      //if random int falls within range of TWO_PROB, return 2
      return Config.TWO_TILE;
    }
    //else return 4
    return Config.FOUR_TILE;
  }

  /**
    * Method which counts the number of empty tiles on the board
    *
    * @return the number of empty tiles counted
    */
  protected int countEmptyTiles()
  {
    //initialize empty count variable
    int empty = 0;
    for (int r = 0 ; r < this.board.length ; r++)
    {
      for (int c = 0 ; c < this.board[0].length ; c++)
      {
        //iterate through the 2D array
        if (this.board[r][c] == 0)
        {
          //increment empty if the element is 0
          empty++;
        }
      }
    }
    //return empty
    return empty;
  }

  /**
    * Method which adds the random tile to a random empty tile on the board
    *
    * @return the random tile added to the board
    */
  protected int addTile()
  {
    //count empty tiles
    int numEmptyTiles = countEmptyTiles();
    //check case where no tiles are empty
    if (numEmptyTiles == 0)
    {
      return 0;
    }
    //pick a random tile index
    int randomTileIndex = rollRNG(numEmptyTiles);
    //initialize emptyIndex count
    int emptyIndex = 0;
    for (int r = 0 ; r < this.board.length ; r++)
    {
      for (int c = 0 ; c < this.board[0].length ; c++)
      {
        //iterate
        if (this.board[r][c] == 0)
        {
          if (emptyIndex == randomTileIndex)
          {
            this.board[r][c] = randomTile();
            return this.board[r][c];
          }
          emptyIndex++;
        }
      }
    }
    return 0;
  }

  /**
    * Method rotates the board counterclockwise to assist in performing
    * the slide
    */
  protected void rotateCounterClockwise()
  {
    int tempBoard[][] = new int[this.board.length][this.board[0].length];
    int thisBoard[][] = new int[this.board[0].length][this.board.length];
    tempBoard = this.getBoard();
    for (int r = 0 ; r < thisBoard.length ; r++)
    {
      for (int c = 0 ; c < thisBoard[0].length ; c++)
      {
        thisBoard[r][c] = tempBoard[c][thisBoard.length - r - 1];
      }
    }
    this.board = thisBoard;
  }

  /**
    * Method to check whether if slide down can be performed on the board
    *
    * @return true if slide down can be performed, false otherwise
    */
  protected boolean canSlideDown()
  {
    for (int r = 0 ; r < this.board.length - 1 ; r++)
    {
      for (int c = 0 ; c < this.board[0].length ; c++)
      {
        if (this.board[r][c] != 0)
        {
          if (this.board[r + 1][c] == 0 || this.board[r + 1][c] == this.board[r][c])
          {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
    * Method which checks if any slides can be performed, and returns false if
    * this is the case since the game is not over. Returns true otherwise
    *
    * @return true if the game is over, and false otherwise
    */
  public boolean isGameOver()
  {
    for (int sides = 0 ; sides < Config.DEFAULT_SIZE ; sides++)
    {
      if (this.canSlideDown())
      {
        return false;
      }
      this.rotateCounterClockwise();
    }
    return true;
  }

  /**
    * Method which performs the sliding down of the tiles
    *
    * @return true once the slide down is completed
    */
  public boolean slideDown()
  {
    //check if slidedown cant be performed
    if (this.canSlideDown() == false)
    {
      return false;
    }
    //iterate for the amound of rows - 1
    for (int emptySlides = 0 ; emptySlides < Config.DEFAULT_SIZE ; emptySlides++)
    {
      //iterate the whole 2D array
      for (int r = 0 ; r < this.board.length - 1 ; r++)
      {
        for (int c = 0 ; c < this.board[0].length ; c++)
        {
          //check if the next row is 0, but the current row isn't
          if (this.board[r + 1][c] == 0 && this.board[r][c] != 0)
          {
            //set the next row element equal to the row before it
            this.board[r + 1][c] = this.board[r][c];
            //set the element at the current row to 0
            this.board[r][c] = 0;
          }
        }
      }
    }
    //iterate through the 2D array from the 2nd to last row going up
    for (int r = this.board.length - 1 ; r > 0 ; r--)
    {
      for (int c = 0 ; c < this.board[0].length ; c++)
      {
        //check if two of the same elements are on top of each other and arent 0
        if (this.board[r - 1][c] == this.board[r][c] && this.board[r - 1][c] != 0)
        {
          //merge the 2 elements by adding
          this.board[r][c] += this.board[r - 1][c];
          //set the top element to an empty tile, 0
          this.board[r - 1][c] = 0;
          //add to the score
          score += this.board[r][c];
        }
      }
    }
    //same as first nested for loop statement
    //but only run it once to account for any extra empty tile added after merge
    for (int r = 0 ; r < this.board.length - 1 ; r++)
    {
      for (int c = 0 ; c < this.board[0].length ; c++)
      {
        if (this.board[r + 1][c] == 0 && this.board[r][c] != 0)
        {
          this.board[r + 1][c] = this.board[r][c];
          this.board[r][c] = 0;
        }
      }
    }
    //return true once the slide down is complete
    return true;
  }

  /**
    * Method which takes a Direction object as a parameter and performs
    * slide movements for the game
    *
    * @param dir the object being passed in
    * @return true if a slide in a particular direction is successful and a new
    * tile is added. Return false otherwise
    */
  public boolean move(Direction dir)
  {
    //check null case
    if (dir == null)
    {
      return false;
    }
    //get rotationcount from the method from the direction object
    int rotationCount = dir.getRotationCount();
    for (int i = 0 ; i < rotationCount ; i++)
    {
      //rotateCounterClockwise for the rotationcount
      this.rotateCounterClockwise();
    }
    //check if slideDown can be performed
    boolean direction = this.slideDown();
    if (direction == true)
    {
      for (int j = 0 ; j < Config.DEFAULT_SIZE - rotationCount ; j++)
      {
        //rotateCounterClockwise for the default board size minus rotationCount
        this.rotateCounterClockwise();
      }
      //addTile
      this.addTile();
      //return true once process is successfully completed
      return true;
    }
    else
    {
      for (int j = 0 ; j < Config.DEFAULT_SIZE - rotationCount ; j++)
      {
        //otherwise, rotate board back to original position
        //based on default size minus rotationCount
        this.rotateCounterClockwise();
      }
    }
    //return false if slideDown cant be performed
    return false;
  }
}
