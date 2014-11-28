/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.AppStates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.Listener;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;


/**
 *
 * @author Tanveer
 */
public class StartScreenAppState extends AbstractAppState implements ScreenController {
    
    private Nifty nifty;
    private Camera cam;
    private SimpleApplication app;
    private AssetManager assetManager;
    private InputManager inputManager;
    private ViewPort guiViewPort;
    private AudioRenderer audioRenderer;
    private Node rootNode;
    private Listener listener;
    private AppStateManager stateManager;
    //private static GamePlayAppState gamePlayAppState;

    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.guiViewPort = this.app.getGuiViewPort();
        this.audioRenderer = this.app.getAudioRenderer();
        this.app.getFlyByCamera().setEnabled(false);
        this.listener = this.app.getListener();

        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/screen.xml", "start", this);
        nifty.addXml("Interface/screen2.xml");
        
        //attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
        //flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
    }

   /*custom methods */     
    public void startGame() {
        AudioNode click = new AudioNode(assetManager, "Sounds/click.wav", false);
        click.setPositional(false);
        click.setLooping(false);
        click.setVolume(2);
        rootNode.attachChild(click);
        click.play();
        GamePlayAppState gamePlayAppState = GamePlayAppState.getInstance();
        stateManager.attach(gamePlayAppState);
        setEnabled(false);
        gamePlayAppState.setEnabled(true);
        nifty.gotoScreen("hud");
    }
    public void quitGame() {
        AudioNode click = new AudioNode(assetManager, "Sounds/click.wav", false);
        click.setPositional(false);
        click.setLooping(false);
        click.setVolume(2);
        rootNode.attachChild(click);
        click.play();
        this.app.stop(); 
    }
    public void pauseGame() {
        AudioNode click = new AudioNode(assetManager, "Sounds/click.wav", false);
        click.setPositional(false);
        click.setLooping(false);
        click.setVolume(2);
        rootNode.attachChild(click);
        click.play();
        GamePlayAppState instance = GamePlayAppState.getInstance();
        instance.setEnabled(false);
        nifty.gotoScreen("start");
        setEnabled(true);
    }
        
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }
    
    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }
}
