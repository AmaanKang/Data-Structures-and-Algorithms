
import battleship.BattleShip;
import java.awt.Point;
import java.util.ArrayList;

/**
 * A Sample random shooter - Takes no precaution on double shooting and has no strategy once
 * a ship is hit.
 *
 * @author mark.yendt
 */
public class SampleBot {
    private int gameSize;
    private BattleShip battleShip;

    /**
     * Constructor keeps a copy of the BattleShip instance
     *
     * @param b previously created battleship instance - should be a new game
     */
    public SampleBot(BattleShip b) {
        battleShip = b;
        gameSize = b.boardSize;
    }

    /**
     * Creates a shot and calls the battleship shoot method
     *
     * @return true if a Ship is hit, false otherwise
     */

    public boolean fireShot(int row, int col) {
        if(row >= 0 && row <= 9 && col >= 0 && col <= 9){
            Point shot = new Point(row, col);
            boolean hit = battleShip.shoot(shot);
            return hit;
        }
        return false;
    }

    /**
     *
     * @param row board row
     * @param col board column
     * @param points arrayList containing all the shots made in a single game
     * @param pointsList arraylist containing the shot point if the hit was successful
     */
    public void shootAfterHit(int row, int col, ArrayList<Point> points,ArrayList<Point> pointsList){
        if(row >= 0 && row <= 9 && col >= 0 && col <= 9){
            if(!points.contains(new Point(row, col))){
                points.add(new Point(row, col));
                boolean hit = fireShot(row, col);
                if(hit){
                    if(!pointsList.contains(new Point(row, col))){
                        pointsList.add(new Point(row,col));
                    }
                }
            }
        }
    }

}
