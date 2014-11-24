package terrainDecorator;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.texture.Texture;

public class Alphamap extends TerrainDecorator {
    String texture = "Textures/alphamap.png";
    
    public Alphamap(ITerrain t){
        super(t);
    }
    
    public Material addTexture(Material mat_terrain, SimpleApplication app){
        Material m = super.addTexture(mat_terrain, app);
        return addedTexture(m, app);
    }
    
    public Material addedTexture(Material mat_terrain, SimpleApplication app){
        Texture rock = app.getAssetManager().loadTexture(texture);
        rock.setWrap(Texture.WrapMode.Repeat);
        mat_terrain.setTexture("Tex3", rock);
        mat_terrain.setFloat("Tex3Scale", 128f);
        return mat_terrain;
    }
}
