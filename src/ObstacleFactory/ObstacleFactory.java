/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.helloworld;

/**
 *
 * @author mallika
 */
public class ObstacleFactory {
    public Obstacle createObstacle(String type) {
        Obstacle obstacle= null;
        
        if (type.equals("wall")) {
            obstacle = new BrickWall();
        }
        else if (type.equals("ball")) {
            obstacle = new TexturedObstacles();
        }
        else if (type.equals("human")) {
            obstacle = new Animation();
        }
        return obstacle;
    }
}
