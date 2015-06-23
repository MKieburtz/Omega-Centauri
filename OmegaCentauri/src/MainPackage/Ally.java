package MainPackage;

import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public abstract class Ally extends Ship implements Controllable {

    public Ally(int x, int y, Type shipType, double maxVel, double maxAngleVel,
            double angleIncrement, double acceleration, int shootingDelay, int health, GameActionListener actionListener) 
    {
        super(x, y, shipType, maxVel, maxAngleVel, angleIncrement, acceleration, shootingDelay, 
             health, actionListener);
    }

    @Override
    public void shoot() 
    {
        
    }
    
    @Override
    public void update()
    {
        updateHitbox();
    }

    @Override
    public void update(ArrayList<Command> commands) 
    {
        updateHitbox();
    }   

    @Override
    public abstract void changeImage(ImageMovementState movementState, ImageRotationState rotationState);

}
