package terrainDecorator;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.texture.Texture;

public class GrassTerrain extends TerrainDecorator {
    String texture = "Textures/grass.jpg";
    
    public GrassTerrain(ITerrain t){
        super(t);
    }
    
    public Material addTexture(Material mat_terrain, SimpleApplication app){
        Material m = super.addTexture(mat_terrain, app);
        return addedTexture(m, app);
    }
    
    public Material addedTexture(Material mat_terrain, SimpleApplication app){
        Texture grass = app.getAssetManager().loadTexture(texture);
        grass.setWrap(Texture.WrapMode.Repeat);
        mat_terrain.setTexture("Tex1", grass);
        mat_terrain.setFloat("Tex1Scale", 256f);
        return mat_terrain;
    }
}
