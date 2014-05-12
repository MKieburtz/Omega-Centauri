package MainPackage;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class TwinklingStarChunk extends StarChunk {

    private Random random = new Random();

    private float opacity = 100f;
    private HashMap<Rectangle2D.Double, Float> starOpacity = new HashMap<Rectangle2D.Double, Float>();
    private HashMap<Rectangle2D.Double, Boolean> fading = new HashMap<Rectangle2D.Double, Boolean>();
    private HashMap<Rectangle2D.Double, Float> rate = new HashMap<Rectangle2D.Double, Float>();

    private final int startingTime = random.nextInt(100) + 1;
    private int time = 0;
    private float[] delays = {.5f, 1, 2, 4};

    public TwinklingStarChunk(double x, double y) {
        super(x, y - 133);
        
        for (Rectangle2D.Double rect : rects) {
            starOpacity.put(rect, opacity);
            fading.put(rect, Boolean.TRUE);
            rate.put(rect, delays[random.nextInt(delays.length)]);
        }
    }

    public void draw(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Rectangle2D.Double rect : rects) {
            if (time != 0) {
                if (time <= startingTime * 3) {
                    switch (time / startingTime) {
                        case 3: // don't break
                            drawStar(g2d, rect);
                            break;
                        case 2:
                            drawStar(g2d, rect);
                            break;
                        case 1:
                            drawStar(g2d, rect);
                            break;
                    }
                } else {
                    drawStar(g2d, rect);
                }
                drawStar(g2d, rect);
            }else
            {
                drawStar(g2d, rect);
            }
            
        }
        time++;
    }

    private void drawStar(Graphics2D g2d, Rectangle2D.Double rect) {

        Composite originalComposite = g2d.getComposite();

        int rule = AlphaComposite.SRC_OVER;

        Composite comp = AlphaComposite.getInstance(rule, (float) starOpacity.get(rect) / 100);
        g2d.setComposite(comp);
        g2d.setColor(Color.WHITE);
        g2d.draw(rect);
        g2d.setComposite(originalComposite);
        if (fading.get(rect)) {
            starOpacity.put(rect, starOpacity.get(rect) - rate.get(rect));
            if (starOpacity.get(rect) == 0) {
                fading.put(rect, Boolean.FALSE);
            }
        } else if (!fading.get(rect)) {
            starOpacity.put(rect, starOpacity.get(rect) + rate.get(rect));
            if (starOpacity.get(rect) == 100) {
                fading.put(rect, Boolean.TRUE);
            }
        }

    }
}
