/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package obstacleFactory;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

/**
 *
 * @author mallika
 */
public class BrickWall extends SimpleApplication {
  
    
 
  public void runApplication() {
        this.start();
    }
  
 
  @Override
  public void simpleInitApp() {
    Box box = new Box(2.5f, 2.5f, 1.0f);
    Spatial wall = new Geometry("Box", box);
    //box.setTextureMode(Box.TextureMode.Projected); // better quality on spheres
    TangentBinormalGenerator.generate(box);           // for lighting effect
    Material boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    boxMat.setTexture("ColorMap", assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
    wall.setMaterial(boxMat);
    wall.setLocalTranslation(0,2,-2); // Move it a bit
    //boxGeo.rotate(1.6f, 0, 0);          // Rotate it a bit
    rootNode.attachChild(wall);
 
    /** Must add a light to make the lit object visible! */
    DirectionalLight sun = new DirectionalLight();
    sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
    sun.setColor(ColorRGBA.White);
    rootNode.addLight(sun);
 
  }
}
