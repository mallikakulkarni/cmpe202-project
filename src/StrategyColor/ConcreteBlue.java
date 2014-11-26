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
public class ConcreteBlue implements StrategyColorInterface{

    public ColorRGBA getColor() {
       return ColorRGBA.Blue;
    }
    
    
}
