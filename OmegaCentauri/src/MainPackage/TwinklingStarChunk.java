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

    private int opacity = 100;
    private HashMap<Rectangle2D.Double, Integer> starOpacity = new HashMap<Rectangle2D.Double, Integer>();
    private HashMap<Rectangle2D.Double, Boolean> fading = new HashMap<Rectangle2D.Double, Boolean>();

    private final int startingTime = random.nextInt(20) + 1;
    private int time = 0;

    public TwinklingStarChunk(double x, double y) {
        super(x, y);

        for (int i = 0; i < rects.length; i++) {
            starOpacity.put(rects[i], opacity);
            fading.put(rects[i], Boolean.TRUE);
        }
    }

    public void draw(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Rectangle2D.Double rect : rects) {
//            if (time <= startingTime * 3) {
//                switch (time / startingTime) {
//                    case 3: // don't break
//                        drawStar(g2d, rect);
//                    case 2:
//                        drawStar(g2d, rect);
//                    case 1:
//                        drawStar(g2d, rect);
//                }
//            } else {
//                drawStar(g2d, rect);
//            }
            drawStar(g2d, rect);
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
            starOpacity.put(rect, starOpacity.get(rect) - 2);
            if (starOpacity.get(rect) == 0) {
                fading.put(rect, Boolean.FALSE);
            }
        } else if (!fading.get(rect)) {
            starOpacity.put(rect, starOpacity.get(rect) + 2);
            if (starOpacity.get(rect) == 100) {
                fading.put(rect, Boolean.TRUE);
            }
        }

    }
}
