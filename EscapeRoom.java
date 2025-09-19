/*
* Problem 1: Escape Room
* 
* V1.0
* 10/10/2019
* Copyright(c) 2019 PLTW to present. All rights reserved
*/
import java.util.Scanner;

/**
 * Create an escape room game where the player must navigate
 * to the other side of the screen in the fewest steps, while
 * avoiding obstacles and collecting prizes.
 */
public class EscapeRoom
{

      // Describing the game with a brief message
      // Determine the size (length and width) a player must move to stay within the grid markings
      // Allow game commands:
      //    right, left, up, down: if you try to go off grid or bump into wall, score decreases
      //    jump over 1 space: you cannot jump over walls
      //    if you land on a trap, spring a trap to increase score: you must first check if there is a trap, if none exists, penalty
      //    pick up prize: score increases, if there is no prize, penalty
      //    help: display all possible commands
      //    end: reach the far right wall, score increase, game ends, if game ended without reaching far right wall, penalty
      //    replay: shows number of player steps and resets the board, you or another player can play the same board
      // Note that you must adjust the score with any method that returns a score
      // Optional: create a custom image for your player use the file player.png on disk
    
      /**** provided code:
      // set up the game
      boolean play = true;
      while (play)
      {
        // get user input and call game methods to play 
        play = false;
      }
      */

  public static void main(String[] args) 
  {      
    // The welcome message
    System.out.println("Welcome to EscapeRoom!");
    System.out.println("Get to the other side of the room, avoiding walls and invisible traps,");
    System.out.println("pick up all the prizes.\n");
    
    GameGUI game = new GameGUI();
    game.createBoard();

    // Size of move
    int m = 60; 
    // Individual player moves
    int px = 0;
    int py = 0; 
    
    int score = 0;

    Scanner in = new Scanner(System.in);
    String[] validCommands = { "right", "left", "up", "down", "r", "l", "u", "d",
    "jump", "jr", "jumpleft", "jl", "jumpup", "ju", "jumpdown", "jd",
    "pickup", "p", "quit", "q", "replay", "help", "?", "addtrap", "detrap", "trapvictim"};
  
    // Set up the game
    boolean play = true;
    boolean won = false;
    while (play)
    {
      /* TODO: get all the commands working */
	  /* Your code here */
      System.out.print("> ");
      String cmd = in.nextLine().trim().toLowerCase();

      // Mapping single-letter aliases to full words for consistency
      if (cmd.equals("r")) cmd = "right";
      if (cmd.equals("l")) cmd = "left";
      if (cmd.equals("u")) cmd = "up";
      if (cmd.equals("d")) cmd = "down";
      if (cmd.equals("p")) cmd = "pickup";
      if (cmd.equals("q")) cmd = "quit";

      int deltaX = 0;
      int deltaY = 0;
      boolean handled = true;

      switch (cmd)
      {
        case "right":
          deltaX = m; deltaY = 0;
          score += game.movePlayer(deltaX, deltaY);
          break;
        case "left":
          deltaX = -m; deltaY = 0;
          score += game.movePlayer(deltaX, deltaY);
          break;
        case "up":
          deltaX = 0; deltaY = -m;
          score += game.movePlayer(deltaX, deltaY);
          break;
        case "down":
          deltaX = 0; deltaY = m;
          score += game.movePlayer(deltaX, deltaY);
          break;

        // Jump variants (two spaces); cannot jump through walls per GameGUI checks
        case "jr":
        case "jumpright":
          score += game.movePlayer(2*m, 0);
          break;
        case "jl":
        case "jumpleft":
          score += game.movePlayer(-2*m, 0);
          break;
        case "ju":
        case "jumpup":
          score += game.movePlayer(0, -2*m);
          break;
        case "jd":
        case "jumpdown":
          score += game.movePlayer(0, 2*m);
          break;
        case "jump":
          System.out.print("Jump which direction (r/l/u/d)? > ");
          String jdir = in.nextLine().trim().toLowerCase();
          if (jdir.equals("r")) score += game.movePlayer(2*m, 0);
          else if (jdir.equals("l")) score += game.movePlayer(-2*m, 0);
          else if (jdir.equals("u")) score += game.movePlayer(0, -2*m);
          else if (jdir.equals("d")) score += game.movePlayer(0, 2*m);
          else { System.out.println("Invalid jump direction"); score -= 1; }
          break;

        // Spring trap ahead of player in a direction
        case "sr":
        case "springright":
          if (game.isTrap(m, 0)) { score += game.springTrap(m, 0); }
          else { score += game.springTrap(m, 0); }
          break;
        case "sl":
        case "springleft":
          if (game.isTrap(-m, 0)) { score += game.springTrap(-m, 0); }
          else { score += game.springTrap(-m, 0); }
          break;
        case "su":
        case "springup":
          if (game.isTrap(0, -m)) { score += game.springTrap(0, -m); }
          else { score += game.springTrap(0, -m); }
          break;
        case "sd":
        case "springdown":
          if (game.isTrap(0, m)) { score += game.springTrap(0, m); }
          else { score += game.springTrap(0, m); }
          break;
        case "spring":
          System.out.print("Spring trap which direction (r/l/u/d)? > ");
          String sdir = in.nextLine().trim().toLowerCase();
          if (sdir.equals("r")) score += game.springTrap(m, 0);
          else if (sdir.equals("l")) score += game.springTrap(-m, 0);
          else if (sdir.equals("u")) score += game.springTrap(0, -m);
          else if (sdir.equals("d")) score += game.springTrap(0, m);
          else { System.out.println("Invalid spring direction"); score -= 1; }
          break;

        case "pickup":
          score += game.pickupPrize();
          break;

        case "help":
        case "?":
          System.out.println("Commands: right(r), left(l), up(u), down(d)");
          System.out.println("          jump jr/jl/ju/jd or 'jump' then direction");
          System.out.println("          spring sr/sl/su/sd or 'spring' then direction");
          System.out.println("          pickup(p), replay, help(?)");
          System.out.println("          addtrap, detrap, trapvictim");
          System.out.println("          quit(q)");
          break;

        case "replay":
          System.out.println("steps=" + game.getSteps());
          score += game.replay();
          break;

        case "quit":
          play = false;
          break;

        case "addtrap":
          System.out.println("Added a new trap to the board!");
          game.setTraps(game.getTotalTraps() + 1);
          game.createBoard();
          score += 5; // Bonus for adding trap
          break;

        case "detrap":
          System.out.println("Removed a trap from the board!");
          if (game.getTotalTraps() > 0) {
            game.setTraps(game.getTotalTraps() - 1);
            game.createBoard();
            score += 3; // Bonus for removing trap
          } else {
            System.out.println("No traps to remove!");
            score -= 2; // Penalty for trying to remove non-existent trap
          }
          break;

        case "trapvictim":
          System.out.println("You've become a victim of your own trap!");
          score -= 10; // Penalty for being trapped
          break;

        default:
          handled = false;
          break;
      }

      if (!handled)
      {
        System.out.println("Invalid command. Type 'help' for options.");
        score -= 1; // Penalty for invalid command
      }

      System.out.println("score=" + score);
      // Win condition: score > 50
      if (score > 50)
      {
        System.out.println("YOU WIN! Score exceeded 50.");
        game.close();
        won = true;
        play = false;
      }
      
    }

  
    if (!won)
    {
      score += game.endGame();
    }

    System.out.println("score=" + score);
    System.out.println("steps=" + game.getSteps());
  }
}

        
