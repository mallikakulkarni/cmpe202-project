/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StrategyColor;

import com.jme3.math.ColorRGBA;

/**
 *
 * @author Ultimate
 */
public class ConcreteRed implements StrategyColorInterface {

    public ColorRGBA getColor() {
   return ColorRGBA.Red;
    }
    
    
}
