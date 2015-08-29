package MainPackage;

import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public abstract class Ally extends Ship implements Controllable {

    protected boolean beingControlled = false;
    
    public Ally(int x, int y, Type shipType, double maxVel, double maxAngleVel,
            double angleIncrement, double acceleration, int shootingDelay, int health, GameActionListener actionListener) 
    {
        super(x, y, shipType, maxVel, maxAngleVel, angleIncrement, acceleration, shootingDelay, 
             health, actionListener);
    }

    public boolean isBeingControlled()
    {
        return beingControlled;
    }
    
    public void setControllingShip(boolean beingControlled)
    {
        this.beingControlled = beingControlled;
    }
    
    @Override
    public void shoot() 
    {
        
    }
    
    @Override
    public void update()
    {
        updateHitbox();
        explosion.updateLocation(location);
    }

    @Override
    public void update(ArrayList<Command> commands) 
    {
        updateHitbox();
    }   

    @Override
    public abstract void changeImage(ImageMovementState movementState, ImageRotationState rotationState);
    
    public abstract void setHighlighted(boolean highlighted);

}
