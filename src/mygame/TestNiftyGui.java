package mygame;

import com.jme3.app.SimpleApplication;
import mygame.AppStates.StartScreenAppState;

public class TestNiftyGui extends SimpleApplication {         
    public static void main(String[] args){
        TestNiftyGui app = new TestNiftyGui();
        app.setPauseOnLostFocus(false);
        app.start();
    }
    
    public void simpleInitApp() {
        setDisplayFps(false);
        setDisplayStatView(false);

        StartScreenAppState startScreenAppState = new StartScreenAppState();
        stateManager.attach(startScreenAppState);     
        inputManager.setCursorVisible(true);
    }        
}