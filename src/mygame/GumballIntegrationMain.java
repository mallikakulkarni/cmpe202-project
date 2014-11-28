package mygame;

import StrategyColor.ConcreteRed;
import StrategyColor.ConcreteBlue;
import StrategyColor.ConcretePink;
import StrategyColor.StrategyColorInterface;
import terrainDecorator.RoadTerrain;
import terrainDecorator.GrassTerrain;
import terrainDecorator.ITerrain;
import terrainDecorator.BasicTerrain;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.util.TangentBinormalGenerator;
import com.jme3.water.WaterFilter;


public class GumballIntegrationMain extends SimpleApplication {
  
    StrategyColorInterface strategy;
    public static void main(String[] args) {
        GumballIntegrationMain app = new GumballIntegrationMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        initTerrain();
        initPPcWater();
        
        flyCam.setMoveSpeed(50);
        initLight(10f, 10f, 10f);
        initAnimation(10f, 10f, 10f);
        initLight(100f, 10f, 100f);
        initAnimation(100f, 10f, 100f);
        initLight(500f, 10f, 500f);
        initAnimation(500f, 10f, 500f);
        initLight(-200f, 10f, -200f);
        initAnimation(-200f, 10f, -200f);
        initLight(-200f, 10f, -200f);
        initAnimation(-200f, 10f, -200f);
        initLight(10f, 10f, 500f);
        initWall(10f, 10f, 200f);
        initLight(200f, 10f, 200f);
        initWall(200f, 10f, 200f);
        initLight(500f, 10f, 400f);
        initWall(500f, 10f, 400f);
        initLight(-200f, 10f, -200f);
        initWall(-200f, 10f, -200f);
        initLight(-500f, 10f, -400f);
        initWall(-500f, 10f, -400f);
        initLight(-500f, 10f, -200f);
        initSphere(-500f, 10f, -200f);
        initLight(0f, 0f, 0f);
        initSphere(50f, 50f, 50f);
        initLight(-300f, 10f, -200f);
        initSphere(-300f, 10f, -200f);
        initLight(-150f, 10f, -150f);
        initSphere(-150f, 10f, -150f);
        initLight(300f, 10f, 200f);
        initSphere(300f, 10f, 200f);

	strategy=new ConcreteBlue();
        initGumballs(-150.0f , 3.0f , 60.0f);
        strategy=new ConcreteRed();
        initGumballs(-50.0f , 3.0f , 40.0f);
        strategy=new ConcreteBlue();
        initGumballs(50.0f , 3.0f , 60.0f);
        strategy=new ConcreteRed();
        initGumballs(10.0f , 3.0f , 80.0f);
        strategy=new ConcretePink();
        initGumballs(30f , 3.0f , 40.0f);
        strategy=new ConcreteRed();
        initGumballs(200.0f , 3.0f , 100.0f);
        strategy=new ConcretePink();
        initGumballs(-50.0f , 3.0f , 100.0f);
        strategy=new ConcreteRed();
        initGumballs(100.0f , 3.0f , 30.0f);
         strategy=new ConcretePink();
        initGumballs(-450.0f , 3.0f , 160.0f);
        strategy=new ConcreteRed();
        initGumballs(450.0f , 3.0f , -40.0f);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
     void initTerrain(){
        AbstractHeightMap heightmap = null;
        Texture heightMapImage = assetManager.loadTexture("Textures/heightmap1.jpg");
        heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
        //heightmap.load();
        
         ITerrain customTerrain = 
                new RoadTerrain(new GrassTerrain(new BasicTerrain()));
        
        Material mat_terrain = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
        mat_terrain = customTerrain.addTexture(mat_terrain, this);
        
        TerrainQuad terrain = new TerrainQuad("my terrain", 65, 513, heightmap.getHeightMap());
        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(0, 1, 0);
        terrain.setLocalScale(2f, 1f, 2f);
        rootNode.attachChild(terrain);
        
        TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
        terrain.addControl(control);
     }
    
     public void initPPcWater(){
        Vector3f lightDir = new Vector3f(1, -1, 5);
        WaterFilter water = new WaterFilter(rootNode, lightDir);
        water.setCenter(Vector3f.ZERO);
        water.setRadius(2600);
        
        water.setWaveScale(0.001f);
        water.setMaxAmplitude(2f);
        water.setFoamExistence(new Vector3f(1f, 3, 0.5f));
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Textures/texture_Foam.jpg"));
        water.setRefractionStrength(0.5f);
        water.setWaterHeight(-50.0f);
        
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(water);
        viewPort.addProcessor(fpp);
         
    } 
     
    public void initAnimation(float x, float y, float z) {
      Node player = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
      player.setLocalScale(3f);
      player.setLocalTranslation(x,y,z);
      rootNode.attachChild(player);
    }
    
    public void initLight(float x, float y, float z) {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(x,y,z));
        rootNode.addLight(sun);
    }
    
    public void initWall(float x, float y, float z) {
    Box box = new Box(20f, 20f, 20f);
    Spatial wall = new Geometry("Box", box);
    //box.setTextureMode(Box.TextureMode.Projected); // better quality on spheres
    TangentBinormalGenerator.generate(box);           // for lighting effect
    Material boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    boxMat.setTexture("ColorMap", assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
    wall.setMaterial(boxMat);
    wall.setLocalTranslation(x, y, z); // Move it a bit
    //boxGeo.rotate(1.6f, 0, 0);          // Rotate it a bit
    rootNode.attachChild(wall);
    }
    
    public void initSphere(float x, float y, float z) {
        Sphere sphereMesh = new Sphere(32,32, 20f);
        Geometry sphereGeo = new Geometry("Shiny rock", sphereMesh);
        sphereMesh.setTextureMode(Sphere.TextureMode.Projected); // better quality on spheres
        TangentBinormalGenerator.generate(sphereMesh);           // for lighting effect
        Material sphereMat = new Material(assetManager, 
        "Common/MatDefs/Light/Lighting.j3md");
        sphereMat.setTexture("DiffuseMap", 
        assetManager.loadTexture("Textures/Terrain/Rocky/RockyNormals.jpg"));
        sphereMat.setTexture("NormalMap", 
        assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg"));
        sphereMat.setBoolean("UseMaterialColors",true);    
        sphereMat.setColor("Diffuse",ColorRGBA.Red);
        sphereMat.setColor("Specular",ColorRGBA.Yellow);
        sphereMat.setFloat("Shininess", 64f);  // [0,128]
        sphereGeo.setMaterial(sphereMat);
        sphereGeo.setLocalTranslation(x,y,z); // Move it a bit
        sphereGeo.rotate(1.6f, 0, 0);          // Rotate it a bit
        rootNode.attachChild(sphereGeo);
    }

public void initGumballs(float x, float y, float z)
     {
           
        Vector3f v = new Vector3f(x,y,z);
        Sphere s = new Sphere(30, 30, 1, false, true);
        Geometry geoms = new Geometry("Sphere", s);
        Material mats1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mats1.setColor("Color", strategy.getColor());
        geoms.setMaterial(mats1);
        geoms.setLocalTranslation(v);
        rootNode.attachChild(geoms);

         
     }

    
    
}