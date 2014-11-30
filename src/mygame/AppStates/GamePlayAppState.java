/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.AppStates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import StrategyColor.ConcreteRed;
import StrategyColor.ConcreteBlue;
import StrategyColor.ConcretePink;
import StrategyColor.StrategyColorInterface;
import com.jme3.bullet.BulletAppState; 
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import terrainDecorator.RoadTerrain;
import terrainDecorator.GrassTerrain;
import terrainDecorator.ITerrain;
import terrainDecorator.BasicTerrain;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.util.TangentBinormalGenerator;
import com.jme3.water.WaterFilter;
import observer.CarControlActionListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import java.util.HashSet;

/**
 *
 * @author Tanveer
 */
public class GamePlayAppState extends AbstractAppState implements PhysicsCollisionListener{
    private AssetManager assetManager;
    private InputManager inputManager;
    private ViewPort guiViewPort;
    private AudioRenderer audioRenderer;
    private SimpleApplication app;
    private Camera cam;
    private Node rootNode;
    private AppStateManager stateManager;
    private ViewPort viewPort;
    private Node guiNode;
    private BitmapFont guiFont;    
    BulletAppState bulletAppState;
    VehicleControl vehicle;
    //CharacterControl vehicle;
    final float accelerationForce = 1000.0f;
    final float brakeForce = 100.0f;
    float steeringValue = 0;
    float accelerationValue = 0;
    Vector3f jumpForce = new Vector3f(0, 3000, 0);
    // kandarp
    StrategyColorInterface strategy;
    private StartScreenAppState startScreenAppState;
    private int gameScore = 0;
    int iGumballId = 0;
    int iCountGumballCollision = 0;
    Vector3f vctr;
    static HashSet<String> seenGumballs = new HashSet<String>();

    // kandarp
    private static GamePlayAppState instance = new GamePlayAppState();

    public static GamePlayAppState getInstance() {
        return instance;
    }
    public GamePlayAppState() {
        //instance = this;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        this.guiNode = this.app.getGuiNode();
        this.guiViewPort = this.app.getGuiViewPort();       
        this.audioRenderer = this.app.getAudioRenderer();
        this.stateManager = stateManager;
        
        startScreenAppState = stateManager.getState(StartScreenAppState.class);
        
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached

        //this.app.getFlyByCamera().setEnabled(true);
        //this.app.getFlyByCamera().setDragToRotate(true);
        inputManager.setCursorVisible(true);
        setUpGame(this.app);
    }
    
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
        cam.lookAt(vehicle.getPhysicsLocation(), Vector3f.UNIT_Y);
        
        startScreenAppState.updateScore(gameScore);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }
   
    /*Custom methods*/
    public void scoreApi() {
        gameScore++;
        dispenseGumballs(vctr, gameScore, app);
        vctr.x += 5;
        
    }
    public void setUpGame(SimpleApplication app) {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
        initTerrain();
        initPPcWater();

        //kandarp
        setupKeys();
        buildPlayer();
        Vector3f v = vehicle.getPhysicsLocation();
        
        
        v.z += 10;
        initSphere(v);
        
        v.x += 10;
        vctr = v;
        gumballMachine(v);
        
        strategy = new ConcreteBlue();
        v.z += 20;
        v.x += 10;
        initGumballs(v);
        
        strategy = new ConcretePink();
        v.z += 20;
        v.x += 10;
        initGumballs(v);

        
        v.z += 10;
        v.x += -20;
        initAnimation(v);
        
        strategy = new ConcreteRed();
        v.z += 10;
        v.x += -20;
        initGumballs(v);

        v.z += 10;
        v.x += -20;
        initWall(v);
        
        strategy = new ConcreteRed();
        v.z += 10;
        v.x += -20;
        initGumballs(v);
        
        v.z += 5;
        v.x += -5;
        initAnimation(v);
        
        strategy = new ConcreteBlue();
        v.z -= 20;
        v.x -= -20;
        initGumballs(v);
        
        
       
        
        //flyCam.setEnabled(true);
        ChaseCamera chaseCam = new ChaseCamera(cam, car, inputManager);
        chaseCam.setSmoothMotion(true);
        //kandarp
    }
    
    
    
    public void initAnimation(Vector3f v) {
        Node player = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        player.setLocalScale(1f);
        player.setLocalTranslation(v);
        player.addControl(new RigidBodyControl(0));

        rootNode.attachChild(player);
        getPhysicsSpace().add(player);
    }

    public void initLight(float x, float y, float z) {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(x, y, z));
        rootNode.addLight(sun);

    }

    public void initWall(Vector3f v) {
        Box box = new Box(3f, 3f, 3f);
        Spatial wall = new Geometry("Box", box);
        //box.setTextureMode(Box.TextureMode.Projected); // better quality on spheres
        TangentBinormalGenerator.generate(box);           // for lighting effect
        Material boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setTexture("ColorMap", assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
        wall.setMaterial(boxMat);
        wall.setLocalTranslation(v); // Move it a bit
        //boxGeo.rotate(1.6f, 0, 0);          // Rotate it a bit
        wall.addControl(new RigidBodyControl(0));
        rootNode.attachChild(wall);
        getPhysicsSpace().add(wall);
    }

    public void initSphere(Vector3f v) {
        Sphere sphereMesh = new Sphere(3, 3, 3f);
        Geometry sphereGeo = new Geometry("Shiny rock", sphereMesh);
        sphereMesh.setTextureMode(Sphere.TextureMode.Projected); // better quality on spheres
        TangentBinormalGenerator.generate(sphereMesh);           // for lighting effect
        Material sphereMat = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        sphereMat.setTexture("DiffuseMap",
                assetManager.loadTexture("Textures/Terrain/Rocky/RockyNormals.jpg"));
        sphereMat.setTexture("NormalMap",
                assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg"));
        sphereMat.setBoolean("UseMaterialColors", true);
        sphereMat.setColor("Diffuse", ColorRGBA.Red);
        sphereMat.setColor("Specular", ColorRGBA.Yellow);
        sphereMat.setFloat("Shininess", 64f);  // [0,128]
        sphereGeo.setMaterial(sphereMat);
        sphereGeo.setLocalTranslation(v); // Move it a bit
        sphereGeo.rotate(1.6f, 0, 0);          // Rotate it a bit
        sphereGeo.addControl(new RigidBodyControl(0));
        rootNode.attachChild(sphereGeo);
        getPhysicsSpace().add(sphereGeo);
    }

    public void initGumballs(Vector3f v) {

        //Vector3f v = new Vector3f(x, y, z);
        Sphere s = new Sphere(8, 8, 1, false, true);
        Geometry geoms = new Geometry("Sphere", s);
        Material mats1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mats1.setColor("Color", strategy.getColor());
        geoms.setMaterial(mats1);
        geoms.setLocalTranslation(v);
        iGumballId ++;
        geoms.setName("gumball"+iGumballId);
        geoms.addControl(new RigidBodyControl(0));
        rootNode.attachChild(geoms);
        getPhysicsSpace().add(geoms);


    }
    
    private void setupKeys() {

        inputManager.addMapping("Lefts", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Rights", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Ups", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Downs", new KeyTrigger(KeyInput.KEY_S));
        //inputManager.addMapping("B", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Reset", new KeyTrigger(KeyInput.KEY_R));

        CarControlActionListener actionListener = new CarControlActionListener();

        inputManager.addListener(actionListener, "Lefts");
        inputManager.addListener(actionListener, "Rights");
        inputManager.addListener(actionListener, "Ups");
        inputManager.addListener(actionListener, "Downs");
        inputManager.addListener(actionListener, "Space");
        inputManager.addListener(actionListener, "Reset");
    }
    
    Spatial car = null;

    private void buildPlayer() {

        car = assetManager.loadModel("Models/Ferrari/Car.j3o");

        vehicle = car.getControl(VehicleControl.class);
        vehicle.setPhysicsLocation(new Vector3f(-10, 10, 10));
        vehicle.setGravity(new Vector3f(30, 30, 30));

        car.addControl(vehicle);
        car.setName("car");
        rootNode.attachChild(car);
        bulletAppState.getPhysicsSpace().add(car);
    }
    
    //kandarp
    private PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }

    /*
    @Override
    public void simpleUpdate(float tpf) {
        cam.lookAt(vehicle.getPhysicsLocation(), Vector3f.UNIT_Y);
    }
*/
    public BulletAppState getBulletAppState() {
        return bulletAppState;
    }

    public void setBulletAppState(BulletAppState bulletAppState) {
        this.bulletAppState = bulletAppState;
    }

    public VehicleControl getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleControl vehicle) {
        this.vehicle = vehicle;
    }

    public float getSteeringValue() {
        return steeringValue;
    }

    public void setSteeringValue(float steeringValue) {
        this.steeringValue = steeringValue;
    }

    public float getAccelerationValue() {
        return accelerationValue;
    }

    public void setAccelerationValue(float accelerationValue) {
        this.accelerationValue = accelerationValue;
    }

    public Vector3f getJumpForce() {
        return jumpForce;
    }

    public void setJumpForce(Vector3f jumpForce) {
        this.jumpForce = jumpForce;
    }

    public float getAccelerationForce() {
        return accelerationForce;
    }

    public float getBrakeForce() {
        return brakeForce;
    }
    
    public void initTerrain() {

        TerrainQuad terrain;
        Material mat_terrain;
        
        DirectionalLight light = new DirectionalLight();
        light.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        light.setColor(ColorRGBA.White);
        rootNode.addLight(light);

        ITerrain customTerrain = 
                new RoadTerrain(new GrassTerrain(new BasicTerrain()));
        
        
        
        mat_terrain = new Material(assetManager,
                "Common/MatDefs/Terrain/Terrain.j3md");
        
        mat_terrain = customTerrain.addTexture(mat_terrain, this.app);

        AbstractHeightMap heightmap = null;
        Texture heightMapImage = assetManager.loadTexture(
                "Textures/heightmapK.png");
        heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightmap.load();
        
        terrain = new TerrainQuad("my terrain", 65, 513, null);

        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(0, -1, 0);
        terrain.setLocalScale(2f, 1f, 2f);
        rootNode.attachChild(terrain);


        terrain.addControl(new RigidBodyControl(0));

        getPhysicsSpace().add(terrain);

    }

    public void initPPcWater() {
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
        this.app.getViewPort().addProcessor(fpp);
    }
    
    @Override
    public void collision(PhysicsCollisionEvent event) 
    {
       String nameA = event.getNodeA().getName();
       String nameB = event.getNodeB().getName();
        
        if(nameA.equals("car") && nameB.startsWith("gumball") && !seenGumballs.contains(nameB)) 
        {
            seenGumballs.add(nameB);
            scoreApi();
            rootNode.detachChild(event.getNodeB());
            bulletAppState.getPhysicsSpace().remove(event.getNodeB());
        }
        if(nameA.equals("car") && nameB.startsWith("wall")) 
        {
            System.out.println("Call Tanveer's API to exit the game !!");
        }
        
    }
    //kandarp
    
    public void gumballMachine(Vector3f v)
    {
        Vector3f crank_box=new Vector3f(v.x,v.y,v.z);
        Vector3f headsphere=new Vector3f(v.x,(v.y+2f),v.z);
        Vector3f footer_box=new Vector3f(v.x,(v.y-1.7f),v.z);

        Box b_crankbox = new Box(1, 1.5f, 1);
        Sphere s_headsphere=new Sphere(3,3,1.7f);
        Box b_footer=new Box(1.3f,0.3f,1.3f);
        
        Geometry geom_crankbox = new Geometry("Box", b_crankbox);
        Geometry geom_headsphere=new Geometry("Sphere", s_headsphere);
        Geometry geom_footer=new Geometry("Box",b_footer);
        
        Material mat_crankbox = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_crankbox.setColor("Color", ColorRGBA.Red);
        geom_crankbox.setMaterial(mat_crankbox);
        geom_crankbox.setLocalTranslation(crank_box);

        Material mat_headsphere = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_headsphere.setColor("Color", ColorRGBA.White);
        geom_headsphere.setMaterial(mat_headsphere);
        geom_headsphere.setLocalTranslation(headsphere);

        Material mat_footer = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_footer.setColor("Color", ColorRGBA.Yellow);
        geom_footer.setMaterial(mat_footer);
        geom_footer.setLocalTranslation(footer_box);
        
        rootNode.attachChild(geom_crankbox);
        rootNode.attachChild(geom_headsphere);
        rootNode.attachChild(geom_footer);
        
    }
    
    public void dispenseGumballs(Vector3f v, int i, SimpleApplication app)
    {
        int j;
        for(j=0;j<i;j++)
        {
        Vector3f gumball=new Vector3f(j+2,1,0);
        Sphere s_gumball=new Sphere(30,30,0.4f);
        Geometry geom_gumball=new Geometry("Sphere", s_gumball);

        
        Material mat_gumball = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_gumball.setColor("Color", ColorRGBA.randomColor());
        geom_gumball.setMaterial(mat_gumball);
        geom_gumball.setLocalTranslation(gumball);
        
        
        rootNode.attachChild(geom_gumball);
        }
    }
}
