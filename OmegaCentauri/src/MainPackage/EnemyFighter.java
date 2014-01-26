package MainPackage;

public class EnemyFighter extends EnemyShip{

    public EnemyFighter(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration) {
        
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration);
        
        imagePaths.add("src/resources/EnemyShip.png");
        
        images = mediaLoader.loadImages(imagePaths);
        
        activeImage = images.get(0);
    }
    
}
