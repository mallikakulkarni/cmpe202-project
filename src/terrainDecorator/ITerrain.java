package terrainDecorator;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;

public interface ITerrain {
    Material addTexture(Material mat_terrain,SimpleApplication app);
}
