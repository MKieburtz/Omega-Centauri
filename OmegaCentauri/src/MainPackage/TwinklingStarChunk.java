package MainPackage;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class TwinklingStarChunk extends StarChunk {

    private Random random = new Random();

    private float opacity = 100f;
    private HashMap<Ellipse2D.Double, Float> starOpacity = new HashMap<Ellipse2D.Double, Float>();
    private HashMap<Ellipse2D.Double, Boolean> fading = new HashMap<Ellipse2D.Double, Boolean>();
    private HashMap<Ellipse2D.Double, Float> rate = new HashMap<Ellipse2D.Double, Float>();
    
    private final int startingTime = random.nextInt(100) + 1;
    private int time = 0;
    private float[] delays = {.5f, 1, 2};
    protected ArrayList<Integer> possibleSizes = new ArrayList<Integer>();
    
    public TwinklingStarChunk(double x, double y) {
        super(x, y);
        possibleSizes.add(1);
        possibleSizes.add(2);
        possibleSizes.add(3);
        
        for (Ellipse2D.Double star : stars) {
            int size = possibleSizes.get(random.nextInt(possibleSizes.size()));
            System.out.println(possibleSizes.size() + " " + size);
            possibleSizes.remove(new Integer(size));
            star.setFrame(star.getX(), star.getY(), size, size);
            
            starOpacity.put(star, opacity);
            fading.put(star, Boolean.TRUE);
            rate.put(star, delays[random.nextInt(delays.length)]);
        }
        
    }

    public void draw(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for (Ellipse2D.Double star : stars) {
            if (time != 0) {
                if (time <= startingTime * 3) {
                    switch (time / startingTime) {
                        case 3: // don't break
                            drawStar(g2d, star);
                            break;
                        case 2:
                            drawStar(g2d, star);
                            break;
                        case 1:
                            drawStar(g2d, star);
                            break;
                    }
                } else {
                    drawStar(g2d, star);
                }
                drawStar(g2d, star);
            }else
            {
                drawStar(g2d, star);
            }
            
        }
        time++;
    }

    private void drawStar(Graphics2D g2d, Ellipse2D.Double star) {

        Composite originalComposite = g2d.getComposite();

        int rule = AlphaComposite.SRC_OVER;

        Composite comp = AlphaComposite.getInstance(rule, (float) starOpacity.get(star) / 100);
        g2d.setComposite(comp);
        g2d.setColor(Color.WHITE);
        
        g2d.fill(star);
        
        g2d.setComposite(originalComposite);
        
        if (fading.get(star)) {
            starOpacity.put(star, starOpacity.get(star) - rate.get(star));
            if (starOpacity.get(star) == 0) {
                fading.put(star, Boolean.FALSE);
            }
        } else if (!fading.get(star)) {
            starOpacity.put(star, starOpacity.get(star) + rate.get(star));
            if (starOpacity.get(star) == 100) {
                fading.put(star, Boolean.TRUE);
            }
        }

    }
}
