
import battleship.BattleShip; //from BattleShip.jar
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains the algorithm to hit five ships in a battleship game by using minimum shots.
 */

public class BattleshipStrategy
{
   static final int NUMBEROFGAMES = 10000;

  /**
   * This method creates an instance of battleship game and make shots on the board to hit the ships.
   */
  public static void startingSolution() {
     long start = System.nanoTime();
     int totalShots = 0;
     System.out.println(BattleShip.version());
     for (int game = 0; game < NUMBEROFGAMES; game++) {
       BattleShip battleShip = new BattleShip();
       SampleBot sampleBot = new SampleBot(battleShip);
       /**
        * shotPoints contains all the points for shots made in a single game
        */
       ArrayList<Point> shotPoints = new ArrayList<>();
       int row = 0;
       int col = 0;
       Random rand = new Random();
       while (!battleShip.allSunk()) {
         row = rand.nextInt(battleShip.boardSize);
         col = rand.nextInt(battleShip.boardSize);
         /**
          * To make a shot, the randomly selected row and column should either be odd or even.
          */
         boolean condition = (row%2 == 0 && col%2 == 0) || (row%2 != 0 && col%2 != 0);
         if (!shotPoints.contains(new Point(row, col)) && (condition)){
           shotPoints.add(new Point(row, col));
           boolean hit = sampleBot.fireShot(row, col);
           if (hit) {
             /**
              * shotPointList contains the cells having a HIT, for a specific point of time.
              */
             ArrayList<Point> shotPointList = new ArrayList<>();
             shotPointList.add(new Point(row, col));
             while(shotPointList.size() > 0){
               /**
                * pointList tracks that which cells out of 4 cells(upper cell, lower cell, left cell, right cell) have a HIT
                *
                */
               ArrayList<Point> pointList = new ArrayList<>();
               for(Point p:shotPointList){
                 sampleBot.shootAfterHit(p.x-1,p.y,shotPoints,pointList);
                 sampleBot.shootAfterHit(p.x+1,p.y,shotPoints,pointList);
                 sampleBot.shootAfterHit(p.x,p.y-1,shotPoints,pointList);
                 sampleBot.shootAfterHit(p.x,p.y+1,shotPoints,pointList);
               }
               shotPointList.clear();
               shotPointList.addAll(pointList);
               pointList.clear();
             }
           }
         }
         }
         int gameShots = battleShip.totalShotsTaken();
         totalShots += gameShots;
       }
       System.out.printf("SampleBot - The Average # of Shots required in %d games to sink all Ships = %.2f\n", NUMBEROFGAMES, (double) totalShots / NUMBEROFGAMES);
       System.out.println("The time taken to solve is: "+(System.nanoTime()-start)/1000+" us");

   }

  /**
   * Main method
   * @param args unused
   */
  public static void main(String[] args)
  {
    startingSolution();
  }
}
