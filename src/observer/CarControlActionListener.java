/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import mygame.GameStarter;

/**
 *
 * @author Kandarp
 */
public class CarControlActionListener implements ActionListener {

    public void onAction(String binding,  boolean value, float tpf) {
        
        GameStarter instance = GameStarter.getInstance();
        
        if (binding.equals("Lefts")) {
            if (value) {
                instance.setSteeringValue( instance.getSteeringValue() + .5f );
            } else {
                instance.setSteeringValue( instance.getSteeringValue() - .5f );
            }
            instance.getVehicle().steer(instance.getSteeringValue());
        } else if (binding.equals("Rights")) {
            if (value) {
                instance.setSteeringValue( instance.getSteeringValue() - .5f );
            } else {
                instance.setSteeringValue( instance.getSteeringValue() + .5f );
            }
            instance.getVehicle().steer(instance.getSteeringValue());
        } else if (binding.equals("Ups")) {
            if (value) {
                instance.setAccelerationValue( instance.getAccelerationValue() + instance.getAccelerationForce());
            } else {
                instance.setAccelerationValue( instance.getAccelerationValue() - instance.getAccelerationForce());
            }
            instance.getVehicle().accelerate( instance.getAccelerationValue() );
        }else if (binding.equals("Downs")) {
            if (value) {
                instance.setAccelerationValue( instance.getAccelerationValue() - instance.getAccelerationForce());
            } else {
               instance.setAccelerationValue( instance.getAccelerationValue() + instance.getAccelerationForce());
            }
            instance.getVehicle().accelerate( instance.getAccelerationValue() );
        }  
        else if (binding.equals("N")) {
            if (value) {
                instance.getVehicle().brake( instance.getBrakeForce() );
            } else {
               instance.getVehicle().brake(0f);
            }
        } else if (binding.equals("Space")) {
            if (value) {
                instance.getVehicle().applyImpulse( instance.getJumpForce(), Vector3f.ZERO);
            }
        } else if (binding.equals("Reset")) {
            if (value) {
                System.out.println("Reset");
                instance.getVehicle().setPhysicsLocation(Vector3f.ZERO);
                instance.getVehicle().setPhysicsRotation(new Matrix3f());
                instance.getVehicle().setLinearVelocity(Vector3f.ZERO);
                instance.getVehicle().setAngularVelocity(Vector3f.ZERO);
                instance.getVehicle().resetSuspension();
            } else {
            }
        }
    }

    
    
    
}
