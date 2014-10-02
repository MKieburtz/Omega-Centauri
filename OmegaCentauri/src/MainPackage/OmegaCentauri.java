package MainPackage;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import java.util.concurrent.*;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class OmegaCentauri extends Game implements GameActionListener {
    
    private final String Version = "Dev 1.0.5";
    /*
     * GAME STATE VARIBLES:
     */
    private boolean forward, rotateRight, rotateLeft, shooting = false; // movement booleans
    private boolean paused = false;
    private boolean loading = false;
    private final Point screenSize = new Point(10000, 10000); // game screensize
    private Point2D.Double middleOfPlayer = new Point2D.Double(); // SCREEN LOCATION of the middle of the player
    // TIMING STUFF
    private int FPS = 0;
    private int UPS = 0;
    private int updates = 0;
    private final int TARGETFPS = 70;
    private final long loopTimeUPS = (long) ((1000.0 / TARGETFPS) * 1000000); // Change the constant
    private int framesDrawn = 0;
    /*
     * OBJECTS:
     */
    private final Renderer renderer;
    private Panel panel;
    private Camera camera;
    private GraphicsDevice gd;
    private MainMenu mainMenu;
    private Resources resources;
    // TIMERS
    private ScheduledExecutorService timingEx;
    private ScheduledExecutorService recordingEx;
    /*
     * LOADING VARIBLES:
     */
    private int[] yPositions = {-10000, -10000, 0, 0}; // starting y positions
    private int starChunksLoaded = 0;
    private ArrayList<StarChunk> stars = new ArrayList<>();
    private ArrayList<Ship> deadShips = new ArrayList<>();
    private ArrayList<Shot> deadShots = new ArrayList<>();
    
    public OmegaCentauri() {
       
        resources = new Resources();
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        renderer = new Renderer(resources);
        
        camera = new Camera(1000, 600);
        loading = true;
        
        addShips();
        
        setUpWindow();
    }
    
    private void addShips() {
        player = new Player(0, 0, MainPackage.Type.Fighter, 8, 8, 4, 4, .15, camera.getLocation(), 150, 1000, resources);
        enemyShips.add(new EnemyFighter(200, 200, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 1, resources));
        enemyShips.add(new EnemyFighter(200, 500, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 2, resources));
        enemyShips.add(new EnemyFighter(-200, -200, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 3, resources));
        enemyShips.add(new EnemyFighter(-500, 200, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 4, resources));
        enemyShips.add(new EnemyFighter(-300, 200, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 5, resources));
        enemyShips.add(new EnemyFighter(200, -500, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 6, resources));
        enemyShips.add(new EnemyFighter(-300, 400, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 7, resources));
        enemyShips.add(new EnemyFighter(-400, 200, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 8, resources));
        enemyShips.add(new EnemyFighter(200, 900, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 9, resources));
        enemyShips.add(new EnemyFighter(-200, 500, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 10, resources));
        enemyShips.add(new EnemyFighter(-250, -200, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 11, resources));
        enemyShips.add(new EnemyFighter(-500, 290, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 12, resources));
        enemyShips.add(new EnemyFighter(-200, -200, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 13, resources));
        enemyShips.add(new EnemyFighter(-200, 980, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 14, resources));
        enemyShips.add(new EnemyFighter(-200, -230, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 15, resources));
        enemyShips.add(new EnemyFighter(-530, 200, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 16, resources));
        enemyShips.add(new EnemyFighter(210, -200, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 17, resources));
        enemyShips.add(new EnemyFighter(200, 6500, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 18, resources));
        enemyShips.add(new EnemyFighter(-20, -200, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 19, resources));
        enemyShips.add(new EnemyFighter(-700, 200, MainPackage.Type.Fighter, 5, 3, 5, 5, .15, camera.getLocation(), 500, 20, 20, resources));
        //enemyShips.add(new EnemyMediumFighter(-560, 80, MainPackage.Type.Cruiser, 3, 3, 2, 1, .15, camera.getLocation(), 150, 4000, 200, 5, player, resources));
        syncGameStateVaribles();
        
        player.setUpHitbox(camera.getLocation());
    }
    
    private void setUpWindow() {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Omega Centauri");
        setMinimumSize(new Dimension(600, 600));
        
        mainMenu = new MainMenu(this, resources);
        
        if (!mainMenu.getSettings().getData().getWindowed()) // if fullscreen
        {
            setUndecorated(true);
            if (!System.getProperty("os.name").contains("Mac"))
            {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
            else
            {
                gd.setFullScreenWindow(this);
            }
            this.setVisible(false);
            panel = new Panel(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        } else {
            setSize(1000, 600);
            panel = new Panel(1000, 600);
        }
        
        setBackground(Color.BLACK);
        setInputMaps();
        
        getContentPane().add(panel);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                try {
                    setVisible(false);
                    dispose();
                } catch (java.awt.IllegalComponentStateException ex) {
                    System.err.println("ERROR!");
                }
            }
        });
        
        addWindowFocusListener(new WindowFocusListener() {
            
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }
            
            @Override
            public void windowLostFocus(WindowEvent e) {
//                shooting = false;
//                rotateLeft = false;
//                rotateRight = false;
//                forward = false;
                
                requestFocus();
            }
        });
        
        timingEx = Executors.newScheduledThreadPool(4);
        
        recordingEx = Executors.newSingleThreadScheduledExecutor();
        setLocationRelativeTo(null);
        
        setVisible(true);
        
        timingEx.schedule(new MainMenuService(), 1, TimeUnit.MILLISECONDS);
    }
    
    private void setInputMaps() {
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "W");
        panel.getActionMap().put("W", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                wPressed();
            }
        });
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "Wr");
        panel.getActionMap().put("Wr", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                wReleased();
            }
        });
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "D");
        panel.getActionMap().put("D", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dPressed();
            }
        });
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "Dr");
        panel.getActionMap().put("Dr", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dReleased();
            }
        });
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "A");
        panel.getActionMap().put("A", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                aPressed();
            }
        });
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "Ar");
        panel.getActionMap().put("Ar", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                aReleased();
            }
        });
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, false), "Shift");
        panel.getActionMap().put("Shift", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                shiftPressed();
            }
        });
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, true), "Shiftr");
        panel.getActionMap().put("Shiftr", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                shiftReleased();
            }
        });
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "Space");
        panel.getActionMap().put("Space", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                spacePressed();
            }
        });
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "Spacer");
        panel.getActionMap().put("Spacer", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                spaceReleased();
            }
        });
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, false), "Q");
        panel.getActionMap().put("Q", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                qPressed();
            }
        });
        
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Esc");
        panel.getActionMap().put("Esc", new AbstractAction() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                escapePressed();
            }
        });
    }
    
    @Override
    public void gameStart() {
        System.gc();
        startGame();
    }
    
    @Override
    public void enteredFullScreen() {
        dispose();
        setUndecorated(true);
        gd.setFullScreenWindow(this);
        panel.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        this.setVisible(false);
        this.setVisible(true);
    }
    
    @Override
    public void exitedFullScreen() {
        dispose();
        gd.setFullScreenWindow(null);
        setUndecorated(false);
        setSize(1000, 600);
        panel.setBounds(0, 0, 1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    @Override
    public void settingsChangedToHigh() {
    }
    
    @Override
    public void settingsChangedToLow() {
    }
    
    private void startGame() {
        timingEx.schedule(new LoadingService(), 1, TimeUnit.MILLISECONDS);
    }
    
    private void gameUpdate() {

        //long start = 0;
        if (!paused) {
            //start = System.currentTimeMillis();
            if (camera.getSize().x != getWidth() || camera.getSize().y != getHeight()) {
                camera.setSize(getWidth(), getHeight());
            }
            
            if (forward) {
                player.move(ShipState.Thrusting);
            }
            if (rotateRight) {
                
                player.rotate(ShipState.TurningRight);
            }
            if (rotateLeft) {
                player.rotate(ShipState.TurningLeft);
            }
            if (!forward && player.isMoving()) {
                player.move(ShipState.Drifting);
            }
            if (!rotateRight && !rotateLeft && player.isRotating()) {
                player.rotate(player.rotatingRight() ? ShipState.AngleDriftingRight : ShipState.AngleDriftingLeft);
            }
            if (shooting && player.canShoot()) {
                player.shoot(camera.getLocation());
            }
            for (EnemyShip enemyShip : enemyShips) {
                enemyShip.update(player, camera.getLocation(), enemyShips);
            }
            
            for (int i = deadShips.size() - 1; i > -1; i--) {
                if (!deadShips.get(i).isExploding())
                {
                    deadShots.addAll(deadShips.get(i).getShots());
                    
                    if (enemyShips.contains(deadShips.get(i))) {
                        enemyShips.remove(deadShips.get(i));
                    }
                    shipsToDraw.remove(deadShips.get(i));
                    deadShips.remove(deadShips.get(i));
                }
            }
            
            for (int i = deadShots.size() - 1; i > -1; i--)
            {
                deadShots.get(i).getOwner().removeShot(deadShots.get(i));
                
                if (!deadShots.get(i).isDying())
                {
                    allShots.remove(deadShots.get(i));
                    deadShots.remove(deadShots.get(i));
                }
            }
            
            for (Ship ship : shipsToDraw) {
                ship.updateHitbox(camera.getLocation());
                
                allShots.addAll(ship.getShots()); // should be fine because hashset doesn't allow dups
            }
            
            for (Shot shot : allShots)
            {
                shot.updateHitbox(camera.getLocation());
                
                shot.update();
                if (shot.isDying() && !deadShots.contains(shot))
                {
                    deadShots.add(shot);
                }
            }
            
            for (Ship ship : shipsToDraw) {
                
                if (ship.getShield().isActive()) {
                    ship.getShield().decay();
                }
                boolean collision = false;
                for (Ship collisionShip : shipsToDraw) {
                    //System.out.println(ship.getClass() + " " + collisionShip.getClass());
                    if (!collisionShip.equals(ship) && !(collisionShip instanceof EnemyShip && ship instanceof EnemyShip)) {
                        if (ship.returnHitbox().collides(collisionShip.returnHitbox())
                                && !(ship instanceof EnemyShip && collisionShip instanceof EnemyShip)) {
                            collision = true;
                            if (!ship.isColliding() && !collisionShip.isColliding()) {
                                                                
                                boolean shipDied = ship.CollisionEventWithShip(ship, collisionShip);
                                boolean collisionShipDied = collisionShip.CollisionEventWithShip(ship, collisionShip);
                                
                                if (shipDied) {
                                    deadShips.add(ship);
                                }  if (collisionShipDied) {
                                    deadShips.add(collisionShip);
                                }
                            }
                        }
                    }
                }
                
                if (!collision) {
                    ship.setColliding(false);
                }
                
                for (Shot shot : allShots) {
                    if (!shot.isDying() && !ship.isExploding())
                    {
                        if (!shot.getOwner().equals(ship) || !(shot.getOwner() instanceof EnemyShip && ship instanceof EnemyShip))
                        {
                            if (shot.returnHitbox().collides(ship.returnHitbox())) {
                                boolean[] removals = ship.CollisionEventWithShot(ship, shot, shipsToDraw);
                                if (removals[0]) // ship 
                                { 
                                    deadShips.add(ship);
                                }
                                if (removals[1]) // shot
                                {
                                    if (shot instanceof Missile)
                                    {
                                        Missile m = (Missile)shot;
                                        m.explode();
                                    }
                                    deadShots.add(shot); 
                                }
                            }
                        }
                    }
                }
                
                deadShots.addAll(ship.purgeShots());
            }
            
            for (Shot shot : allShots)
            {
                if (!shot.isDying())
                {
                    for (Shot collisionShot : allShots)
                    {
                        if (!shot.equals(collisionShot) && !(shot.getOwner().equals(collisionShot.getOwner())) &&
                                !(shot.getOwner() instanceof EnemyShip && collisionShot.getOwner() instanceof EnemyShip))
                        {
                            if (shot.returnHitbox().collides(collisionShot.returnHitbox()))
                            {
                                boolean shotGotRemoved = shot.collisionEventWithShot(shot, collisionShot, shipsToDraw);
                                boolean collisionShotGotRemoved = collisionShot.collisionEventWithShot(collisionShot, shot, shipsToDraw);

                                if (shotGotRemoved)
                                {
                                    deadShots.add(shot);
                                }
                                if (collisionShotGotRemoved)
                                {
                                    deadShots.add(collisionShot);
                                }
                            }
                        }
                    }
                }
            }
            syncGameStateVaribles();
        }

//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
    }

    /*
     * 0 = stationary
     * 1 = thrusting
     * 2 = turning right
     * 3 = turning left
     */
    //<editor-fold defaultstate="collapsed" desc="Key bindings">
    
    private void wPressed() {
        if (!paused) {
            forward = true;
            if (!rotateRight && !rotateLeft) {
                player.changeImage(ShipState.Thrusting);
            } else if (rotateLeft && !rotateRight) {
                player.changeImage(ShipState.TurningLeftThrusting);
            } else if (!rotateLeft && rotateRight) {
                player.changeImage(ShipState.TurningRightThrusting);
            }
        }
    }
    
    private void wReleased() {
        forward = false;
        player.changeImage(ShipState.Idle);
        if (rotateRight) {
            player.changeImage(ShipState.TurningLeft);
        } else if (rotateLeft) {
            player.changeImage(ShipState.TurningRight);
        }
    }
    
    private void dPressed() {
        if (!paused) {
            if (rotateLeft)
            {
                rotateRight = false;
                rotateLeft = false;
                player.changeImage(forward ? ShipState.Thrusting : ShipState.Idle);
                return;
            }
            else
            {
                rotateRight = true;
            }
            if (!forward) {
                player.changeImage(ShipState.TurningRight);
            } else if (forward) {
                player.changeImage(ShipState.TurningRightThrusting);
            } else if (rotateLeft) {
                player.changeImage(ShipState.Idle);
            }
        }
    }
    
    private void dReleased() {
        rotateRight = false;
        if (!forward) {
            player.changeImage(ShipState.Idle);
        } else {
            player.changeImage(ShipState.Thrusting);
        }
    }
    
    private void aPressed() {
        if (!paused) {
            
            if (rotateRight)
            {
                rotateRight = false;
                rotateLeft = false;
                player.changeImage(forward ? ShipState.Thrusting : ShipState.Idle);
                return;
            }
            else
            {
                rotateLeft = true;
            }
            
            if (!forward) {
                player.changeImage(ShipState.TurningLeft);
            } else if (forward) {
                player.changeImage(ShipState.TurningLeftThrusting);
            } else if (rotateRight) {
                player.changeImage(ShipState.Idle);
            }
        }
    }
    
    private void aReleased() {
        rotateLeft = false;
        if (!forward) {
            player.changeImage(ShipState.Idle);
        } else {
            player.changeImage(ShipState.Thrusting);
        }
    }
    
    private void shiftPressed() {
        if (!paused) {
            player.speedBoost();
        }
    }
    
    private void shiftReleased() {
        player.stopSpeedBoosting();
    }
    
    private void spacePressed() {
        if (!paused) {
            shooting = true;
        }
    }
    
    private void spaceReleased() {
        shooting = false;
    }
    
    private void qPressed() {
        System.exit(0);
    }
    
    private void escapePressed() {
        paused = !paused;
    }
    
    public class Panel extends JPanel {
        
        public Panel(int width, int height) {
            setSize(width, height);
            setBackground(Color.BLACK);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (mainMenu.isActive()) {
                        mainMenu.checkMouseMoved(e.getPoint());
                        
                    }
                }
            });
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!mainMenu.isActive()) {
                        Rectangle rect = new Rectangle(20, 110, 200, 100);
                        if (rect.contains(e.getPoint()) && paused) {
                            resetGame();
                            mainMenu.setActive(true);
                            timingEx.schedule(new MainMenuService(), 1, TimeUnit.MILLISECONDS);
                        }
                    } else {
                        mainMenu.checkMousePressed(e.getPoint());
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    if (mainMenu.isActive()) {
                        mainMenu.checkMouseExited();
                    }
                }
            });
            
            setVisible(true);
        }
    }
//</editor-fold>
    
    private void syncGameStateVaribles() {
        camera.move(player.getLocation().x - (getWidth() / 2), player.getLocation().y - (getHeight() / 2));

        middleOfPlayer = Calculator.getScreenLocationMiddle(player.getLocation(), camera.getLocation(), player.getActiveImage().getWidth(), player.getActiveImage().getHeight());
    }
    
    class RecordingService implements Runnable {
        
        @Override
        public void run() {
            FPS = framesDrawn;
            UPS = updates;
            framesDrawn = 0;
            updates = 0;
            
            recordingEx.schedule(new RecordingService(), 1, TimeUnit.SECONDS);
        }
    }
    
    long startTime, endtime, sleeptime, additionalTime;
    
    class UpdatingService implements Runnable {
        
        @Override
        public void run() {
            SwingUtilities.invokeLater(new Runnable() {
                
                @Override
                public void run() {
                    try {
                        if (!mainMenu.isActive()) {
                            startTime = System.nanoTime();
                            
                            if (endtime != 0) {
                                additionalTime = Math.abs(Math.abs(sleeptime) - (startTime - endtime));
                            }

//                            System.out.println(additionalTime);
//                            System.out.println(loopTimeUPS);
                            //long startUpdateTime = System.nanoTime();
                            gameUpdate();
                            updates++;
                            //System.out.println("update time: " + (System.nanoTime() - startUpdateTime));

                            //long startRenderTime = System.nanoTime();
                            renderer.drawGameScreen(panel.getGraphics(), shipsToDraw, middleOfPlayer.x, middleOfPlayer.y,
                                    FPS, stars, camera, Version, UPS, paused, allShots);
                            framesDrawn++;
                            //System.out.println("render time: " + (System.nanoTime() - startRenderTime));

                            // doesn't work
                            if (!OmegaCentauri.this.hasFocus()) {
                                //paused = true;
                            }
                            
                            endtime = System.nanoTime();
                            sleeptime = loopTimeUPS - (endtime - startTime) - additionalTime;
                            //System.out.println(sleeptime);
                            timingEx.schedule(new UpdatingService(), sleeptime, TimeUnit.NANOSECONDS);
                            if (sleeptime < 0) {
                                sleeptime = 0;
                            }
                            endtime = System.nanoTime();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    
    class LoadingService implements Runnable {
        
        @Override
        public void run() {
            SwingUtilities.invokeLater(new Runnable() {
                
                @Override
                public void run() {
                    try {
            // load 100 starChunks from each quadrant
                        // load all the horizontal star chunks from each quadrant
                        // then move down 100 to the next chunk down

                        // quadrant 1

                        /*  _______
                         * |___|_x_|
                         * |___|___|
                         */
                        if (yPositions[0] < 0) {
                            
                            for (int x = 1; x < screenSize.x; x = x + 400) {
                                
                                stars.add(new StarChunk(x, yPositions[0], 400, 20));
                                starChunksLoaded++;
                            }
                            
                            yPositions[0] += 400;
                        }

                        // quadrant 2

                        /*  _______
                         * |_x_|___|
                         * |___|___|
                         */
                        if (yPositions[1] < 0) {
                            for (int x = -1; x > -screenSize.x; x = x - 400) {
                                
                                stars.add(new StarChunk(x, yPositions[1], 400, 20));
                                starChunksLoaded++;
                                
                            }
                            
                            yPositions[1] += 400;
                        }

                        // quadrant 3

                        /*  _______
                         * |___|___|
                         * |_x_|___|
                         */
                        if (yPositions[2] < 10000) {
                            for (int x = -1; x > -screenSize.x; x = x - 400) {
                                
                                stars.add(new StarChunk(x, yPositions[2], 400, 20));
                                starChunksLoaded++;
                            }
                            
                            yPositions[2] += 400;
                        }

                        // quadrant 4

                        /*  _______
                         * |___|___|
                         * |___|_x_|
                         */
                        if (yPositions[3] < 10000) {
                            for (int x = 1; x < screenSize.x; x = x + 400) {
                                
                                stars.add(new StarChunk(x, yPositions[3], 400, 20));
                                starChunksLoaded++;
                            }
                            
                            yPositions[3] += 400;
                        }

                        // use active rendering to draw the screen
                        renderer.drawLoadingScreen(panel.getGraphics(), starChunksLoaded / 25, panel.getWidth(), panel.getHeight());
                        
                        if (starChunksLoaded == (25 * 25) * 4) {
                            loading = false;
                            
                        }
                        
                        if (loading) {
                            timingEx.schedule(new LoadingService(), 3, TimeUnit.MILLISECONDS);
                        } else {
                            shipsToDraw.add(player);
                            shipsToDraw.addAll(enemyShips);
                            shipsToDraw.addAll(allyShips);
                            
                            recordingEx.schedule(new RecordingService(), 1, TimeUnit.SECONDS);
                            timingEx.schedule(new UpdatingService(), 1, TimeUnit.NANOSECONDS);
                        }
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    
    class MainMenuService implements Runnable {
        
        @Override
        public void run() {
            SwingUtilities.invokeLater(new Runnable() {
                
                @Override
                public void run() {
                    try {
                        if (mainMenu.isActive()) {
                            
                            if (mainMenu.getSize().x != OmegaCentauri.this.getWidth()
                                    || mainMenu.getSize().y != OmegaCentauri.this.getHeight()) {
                                mainMenu.setSize(OmegaCentauri.this.getWidth(), OmegaCentauri.this.getHeight());
                            }
                            
                            mainMenu.draw(panel.getGraphics());
                            timingEx.schedule(new MainMenuService(), 15, TimeUnit.MILLISECONDS);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    
    private void resetGame() {
        shipsToDraw.clear();
        enemyShips.clear();
        allyShips.clear();
        addShips();
        
        paused = false;
        
        FPS = 0;
        UPS = 0;
        framesDrawn = 0;
        updates = 0;
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        Thread.setDefaultUncaughtExceptionHandler(new EDTExceptionHandler());
//        System.setProperty("sun.awt.exception.handler", EDTExceptionHandler.class.getName());
//        System.setProperty("sun.java2d.opengl", "true");
//        System.setProperty("sun.java2d.ddscale", "true");
//        System.setProperty("sun.java2d.translaccel", "true");
//        System.setProperty("sun.java2d.d3d", "false");
//        System.setProperty("sun.java2d.translaccel","true");
        
        new OmegaCentauri();
    }
}
