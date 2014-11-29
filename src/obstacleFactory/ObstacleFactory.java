/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obstacleFactory;

/**
 *
 * @author mallika
 */
public class ObstacleFactory {
    public Obstacle createObstacle(int i) {
        Obstacle obstacle;
        
        if ((i % 3) == 0) {
            obstacle = new BrickWall();
        }
        else if ((i % 5) == 0) {
            obstacle = new TexturedObstacles();
        }
        else {
            obstacle = new Animation();
        }
        return obstacle;
    }
}
