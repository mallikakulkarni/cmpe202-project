package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 * test
 * @author normenhansen
 */
public class GumballMachine extends SimpleApplication {

    public static void main(String[] args) {
        GumballMachine app = new GumballMachine();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        gumballMachine();
        dispenseGumballs(3);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    
    public void gumballMachine()
    {
          Vector3f crank_box=new Vector3f(0,0,0);
        Vector3f headsphere=new Vector3f(0,2,0);
        Vector3f footer_box=new Vector3f(0,-1.7f,0);

        Box b_crankbox = new Box(1, 1.5f, 1);
        Sphere s_headsphere=new Sphere(30,30,1.7f);
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
    
    public void dispenseGumballs(int i)
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
