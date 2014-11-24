package terrainDecorator;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.texture.Texture;

public class BasicTerrain implements ITerrain{
    Material mat_terrain1;
    String texture = "Textures/alphamap.png";
        
    public Material addTexture(Material mat_terrain, SimpleApplication app){
        Texture basic = app.getAssetManager().loadTexture(texture);
        mat_terrain.setTexture("Tex2", basic);
        return mat_terrain;
    }
}
