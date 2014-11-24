package terrainDecorator;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;

public class TerrainDecorator implements ITerrain {
    ITerrain terrain;
    
    public TerrainDecorator(ITerrain t){
        this.terrain = t;
    }
    public Material addTexture(Material mat_terrain, SimpleApplication app){
        return terrain.addTexture(mat_terrain, app);
    }
}
