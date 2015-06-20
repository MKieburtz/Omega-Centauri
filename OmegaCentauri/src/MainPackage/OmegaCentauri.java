package MainPackage;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import java.util.concurrent.*;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class OmegaCentauri extends Game implements GameActionListener 
{

    private final String Version = "Dev 1.0.5";
    // GAME STATE VARIBLES:
    private boolean forward, rotateRight, rotateLeft, shooting = false; // movement booleans
    private boolean paused = false;
    private boolean loading = false;
    private Point2D.Double middleOfPlayer = new Point2D.Double(); // SCREEN LOCATION of the middle of the player
    private ArrayList<Command> updateCommands = new ArrayList<>();
    // TIMING STUFF
    private int FPS = 0;
    private int UPS = 0;
    private int updates = 0;
    private final int TARGETFPS = 70;
    private final long loopTimeUPS = (long) ((1000.0 / TARGETFPS) * 1000000); // Change the constant
    private int framesDrawn = 0;
    // Objects
    private final Renderer renderer;
    private Panel panel;
    private Camera camera;
    private GraphicsDevice gd;
    private MainMenu mainMenu;
    private Resources resources;
    private Dimension mapSize;
    private Point mouseLocation = new Point();
    private Dimension borderSize = null;
    private GameData gameData = new GameData();
    // TIMERS
    private ScheduledExecutorService timingEx;
    private ScheduledExecutorService recordingEx;
    private ScheduledExecutorService mouseRecordingEx;
    // LOADING VARIBLES:
    private int[] yPositions;
    private int starChunksLoaded = 0;
    private ArrayList<StarChunk> stars = new ArrayList<>();
    private ArrayList<Ship> deadShips = new ArrayList<>();
    private ArrayList<Shot> deadShots = new ArrayList<>();
    
    public OmegaCentauri() 
    {
        /* change this one, Michael */ mapSize = new Dimension(10000, 10000);

        yPositions = new int[]{-mapSize.width, -mapSize.height, 0, 0};

        resources = new Resources();
        gameData.updateResources(resources);
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        renderer = new Renderer();

        camera = new Camera(1000, 600);
        loading = true;

        addShips();

        setUpWindow();
    }

    private void addShips() 
    {
        player = new Player();
        player.controlShip(new Fighter(5500, 5000, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 500));
        
        enemyShips.add(new EnemyFighter(4700, 5050, MainPackage.Type.Fighter, 3, 5, 5, .15, 700, 20, 1));
        enemyShips.add(new EnemyFighter(4800, 5025, MainPackage.Type.Fighter, 3, 5, 5, .15, 600, 20, 2));
        enemyShips.add(new EnemyFighter(4900, 5000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 3));
        enemyShips.add(new EnemyFighter(4800, 4975, MainPackage.Type.Fighter, 3, 5, 5, .15, 800, 20, 4));
        enemyShips.add(new EnemyFighter(4700, 4950, MainPackage.Type.Fighter, 3, 5, 5, .15, 750, 20, 5));
        enemyShips.add(new EnemyFighter(4600, 5000, MainPackage.Type.Fighter, 3, 5, 5, .15, 1000, 20, 6));
//        enemyShips.add(new EnemyFighter(2000, 2000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 3));
//        enemyShips.add(new EnemyFighter(3000, 2200, MainPackage.Type.Fighter, 3, 5, 5, .15, 5000, 20, 4));
//        enemyShips.add(new EnemyFighter(3000, 2000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 5));
//        enemyShips.add(new EnemyFighter(2000, 2000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 6));
//        enemyShips.add(new EnemyFighter(3000, 4000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 7));
//        enemyShips.add(new EnemyFighter(4000, 2000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 8));
//        enemyShips.add(new EnemyFighter(2000, 9000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 9));
//        enemyShips.add(new EnemyFighter(2000, 5000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 10));
//        enemyShips.add(new EnemyFighter(250, 2000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 11));
//        enemyShips.add(new EnemyFighter(5000, 290, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 12));
//        enemyShips.add(new EnemyFighter(2000, 2000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 13));
//        enemyShips.add(new EnemyFighter(2000, 980, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 14));
//        enemyShips.add(new EnemyFighter(2000, 230, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 15));
//        enemyShips.add(new EnemyFighter(530, 2000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 16));
//        enemyShips.add(new EnemyFighter(210, 2000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 17));
//        enemyShips.add(new EnemyFighter(20, 2000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 19));
//        enemyShips.add(new EnemyFighter(7000, 2000, MainPackage.Type.Fighter, 3, 5, 5, .15, 500, 20, 20));
         enemyShips.add(new EnemyMediumFighter(4500, 4850, MainPackage.Type.Cruiser, 3, 2, 1, .15, 300, 4000, 200, 5));
        
        syncGameStateVaribles();
    }

    private void setUpWindow() 
    {
        BufferedImage logo =  resources.getImageForObject("resources/LogoWindows.png");
        setIconImage(logo);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Omega Centauri");
        setMinimumSize(new Dimension(700, 600));
        this.setFocusable(true);

        mainMenu = new MainMenu(this, System.getProperty("os.name").contains("Mac"));

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
            panel = new Panel(true);
        } 
        else 
        {
            setPreferredSize(new Dimension(1000, 600));
            panel = new Panel(false);
        }        

        setBackground(Color.BLACK);
        setInputMaps();

        getContentPane().add(panel);

        if (System.getProperty("os.name").contains("Mac"))
        {
            borderSize = new Dimension(getSize().width - getContentPane().getSize().width,
                getSize().height - getContentPane().getSize().height);
        }
        else
        {
            borderSize = new Dimension(getSize().width - getContentPane().getSize().width - 6,
                getSize().height - getContentPane().getSize().height - 8);
        }
        
        addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent we) 
            {
                try 
                {
                    setVisible(false);
                    dispose();
                } 
                catch (java.awt.IllegalComponentStateException ex)
                {
                    System.err.println("ERROR!");
                }
            }
        });

        addWindowFocusListener(new WindowAdapter()
        {

            @Override
            public void windowLostFocus(WindowEvent e) 
            {
//                shooting = false;
//                rotateLeft = false;
//                rotateRight = false;
//                forward = false;

                requestFocusInWindow();
            }
        });

        timingEx = Executors.newScheduledThreadPool(4);
        recordingEx = Executors.newSingleThreadScheduledExecutor();
        mouseRecordingEx = Executors.newSingleThreadScheduledExecutor();
        
        setLocationRelativeTo(null);

        setVisible(true);

        timingEx.schedule(new MainMenuService(), 1, TimeUnit.MILLISECONDS);
        mouseRecordingEx.schedule(new MouseChecker(), 1, TimeUnit.MILLISECONDS);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Key bindings">
    private void setInputMaps() 
    {
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "W");
        panel.getActionMap().put("W", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                handleInput(KeyEvent.VK_W, false);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "Wr");
        panel.getActionMap().put("Wr", new AbstractAction() 
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleInput(KeyEvent.VK_W, true);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "D");
        panel.getActionMap().put("D", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleInput(KeyEvent.VK_D, false);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "Dr");
        panel.getActionMap().put("Dr", new AbstractAction() 
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleInput(KeyEvent.VK_D, true);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "A");
        panel.getActionMap().put("A", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleInput(KeyEvent.VK_A, false);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "Ar");
        panel.getActionMap().put("Ar", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleInput(KeyEvent.VK_A, true);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "Space");
        panel.getActionMap().put("Space", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                handleInput(KeyEvent.VK_SPACE, false);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "Spacer");
        panel.getActionMap().put("Spacer", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleInput(KeyEvent.VK_SPACE, true);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, false), "Q");
        panel.getActionMap().put("Q", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleInput(KeyEvent.VK_Q, false);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Esc");
        panel.getActionMap().put("Esc", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleInput(KeyEvent.VK_ESCAPE, false);
            }
        });
    }
//</editor-fold>
    
    @Override
    public void gameStart() 
    {
        //System.gc();
        startGame();
    }

    @Override
    public void enteredFullScreen() 
    {
        dispose();
        setUndecorated(true);
        gd.setFullScreenWindow(this);
        panel.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        this.setVisible(false);
        this.setVisible(true);
    }

    @Override
    public void exitedFullScreen()
    {
        dispose();
        gd.setFullScreenWindow(null);
        setUndecorated(false);
        setSize(1000, 600);
        panel.setBounds(0, 0, 1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void settingsChangedToHigh() 
    {
        
    }

    @Override
    public void settingsChangedToLow() 
    {
        
    }

    private void startGame() 
    {
        timingEx.schedule(new LoadingService(), 1, TimeUnit.MILLISECONDS);
    }

    private void gameUpdate() 
    {
        //long start = 0;
        if (!paused) 
        {
            //start = System.currentTimeMillis();
            if (camera.getSize().x != getWidth() || camera.getSize().y != getHeight())
            {
                camera.setSize(getWidth(), getHeight());
            }
            // player has to decide if they need to drift. If no command is sent to move,
            // and they still have velocity of some kind, they need to drift.
            if (forward)
            {
                updateCommands.add(new MovementCommand(MovementCommand.MOVE_FORWARD));
            }
            if (rotateRight)
            {
                updateCommands.add(new RotationCommand(RotationCommand.ROTATE_RIGHT));
            }
            else if (rotateLeft)
            {
                updateCommands.add(new RotationCommand(RotationCommand.ROTATE_LEFT));
            }
            if (shooting) 
            {
                updateCommands.add(new ShootingCommand(ShootingCommand.SHOOT));
            }
            player.update(updateCommands);
            
            updateCommands.clear();
            
            for (EnemyShip enemyShip : enemyShips) 
            {
                enemyShip.update();
            }
            
            for (int i = deadShips.size() - 1; i > -1; i--)
            {
                if (!deadShips.get(i).isExploding())
                {
                    deadShots.addAll(deadShips.get(i).getShots());

                    if (enemyShips.contains(deadShips.get(i)))
                    {
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

            for (Ship ship : shipsToDraw)
            {
                ship.updateHitbox();

                allShots.addAll(ship.getShots()); // should be fine because hashset doesn't allow dups
            }

            for (Shot shot : allShots) 
            {
                shot.updateHitbox();
                shot.update();
               
                if (shot.outOfRange())
                {
                    deadShots.add(shot);
                }
            }

            for (Ship ship : shipsToDraw) 
            {

                if (ship.getShield().isActive()) 
                {
                    ship.getShield().decay();
                }

                for (Shot shot : allShots) 
                {
                    if (!shot.isDying() && !ship.isExploding())
                    {
                        if (Calculator.getDistance(ship.getLocation(), shot.getLocation()) < 500)
                        {
                            if (!(shot.getOwner() instanceof EnemyShip && ship instanceof EnemyShip)) 
                            {
                                if (ship.returnHitbox().collides(shot.returnHitbox()))
                                {
                                    boolean[] removals;
                                    if (ship.getShield().getEnergy() > 0)
                                    {
                                        EllipseHitbox hitbox = (EllipseHitbox)ship.returnHitbox();
                                        removals = ship.CollisionEventWithShotWithShield(ship, shot, shipsToDraw,
                                                hitbox.getAngleOnEllipse(shot.returnHitbox()), hitbox.getAngleToHitbox(shot.returnHitbox()));
                                    }
                                    else
                                    {
                                         removals = ship.CollisionEventWithShotWithHull(ship, shot, shipsToDraw);
                                    }
                                   
                                    if (removals[0]) // ship 
                                    {
                                        deadShips.add(ship);
                                    }
                                    if (removals[1]) // shot
                                    {
                                        shot.explode(ship.getShield().getEnergy() > 0);
                                        deadShots.add(shot);
                                    }
                                }
                            }
                        }
                    }
                }
                deadShots.addAll(ship.purgeShots(mapSize));
            }

            for (Shot shot : allShots) 
            {
                if (!shot.isDying()) 
                {
                    for (Shot collisionShot : allShots) 
                    {
                        if (Calculator.getDistance(shot.getLocation(), collisionShot.getLocation()) < 500) 
                        {   
                            if (!shot.equals(collisionShot) && !(shot.getOwner().equals(collisionShot.getOwner()))
                                    && !(shot.getOwner() instanceof EnemyShip && collisionShot.getOwner() instanceof EnemyShip))
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
            }
            syncGameStateVaribles();
        }

//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
    }

    private void handleInput(int keycode, boolean released)
    {
        switch (player.getImageRotationState())
        {
            case Idle: // either both keys or niether key
                if (!released && keycode == KeyEvent.VK_A)
                {
                    rotateLeft = true;
                }
                else if (released && keycode == KeyEvent.VK_A) // both keys were down and a was released so we go right
                {
                    rotateLeft = false;
                    rotateRight = true;
                }
                if (!released && keycode == KeyEvent.VK_D) 
                {
                    rotateRight = true; 
                }
                else if (released && keycode == KeyEvent.VK_D) // both keys were down and d was released so we go left
                {
                    rotateRight = false;
                    rotateLeft = true;
                }
                break;
            case rotatingRight: // d has to be down
                if (!released && keycode == KeyEvent.VK_A)
                {
                    rotateRight = false;
                }
                else if (released && keycode == KeyEvent.VK_D)
                {
                    // may have to check for a, but I don't think so
                    rotateRight = false;
                }
                break;
            case rotatingLeft: // a has to be down
                if (!released && keycode == KeyEvent.VK_D)
                {
                    rotateLeft = false;
                }
                else if (released && keycode == KeyEvent.VK_A)
                {
                    // may have to check for d, but I don't think so
                    rotateLeft = false;
                }
                break;
        }
        
        switch(player.getImageMovementState())
        {
            case Idle: // w isn't down
                if (!released && keycode == KeyEvent.VK_W)
                {
                    forward = true;
                }
                break;
            case Thrusting:
                if (released && keycode == KeyEvent.VK_W)
                {
                    forward = false;
                }
                break;
        }
        
        if (!released && keycode == KeyEvent.VK_SPACE)
        {
            shooting = true;
        }
        else if (released && keycode == KeyEvent.VK_SPACE)
        {
            shooting = false;
        }
        
        if (!released && keycode == KeyEvent.VK_Q)
        {
            System.exit(0);
        }
        
        if (!released && keycode == KeyEvent.VK_ESCAPE)
        {
            paused = !paused;
        }
    }
    
    public class Panel extends JPanel 
    {

        public Panel(boolean fullScreen) 
        {
            if (fullScreen)
            {
                setSize(Toolkit.getDefaultToolkit().getScreenSize());
            }
            pack();
            
            setBackground(Color.BLACK);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            

            addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e) 
                {
                    if (!mainMenu.isActive()) 
                    {
                        Rectangle rect = new Rectangle(20, 110, 200, 100);
                        if (rect.contains(e.getPoint()) && paused) 
                        {
                            resetGame();
                            mainMenu.setActive(true);
                            timingEx.schedule(new MainMenuService(), 1, TimeUnit.MILLISECONDS);
                        }
                    } 
                    else
                    {
                        mainMenu.checkMousePressed(e.getPoint());
                    }
                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    if (mainMenu.isActive())
                    {
                        mainMenu.checkMouseExited();
                    }
                }
            });

            setVisible(true);
        }
    }

    private void syncGameStateVaribles() 
    {
        camera.move(player.getShipLocation().x - (getWidth() / 2), player.getShipLocation().y - (getHeight() / 2));

        middleOfPlayer = Calculator.getScreenLocationMiddle(player.getShipLocation(),
                camera.getLocation(), player.getShipActiveImage().getWidth(), player.getShipActiveImage().getHeight());
        
        gameData.updateCameraLocation(camera.getLocation().x, camera.getLocation().y);
        gameData.updatePlayer(player.getControllingShip());
        gameData.updateShips(enemyShips);
    }

    class RecordingService implements Runnable
    {
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

    class UpdatingService implements Runnable 
    {

        @Override
        public void run() 
        {
            SwingUtilities.invokeLater(new Runnable() 
            {

                @Override
                public void run() 
                {
                    try
                    {
                        if (!mainMenu.isActive()) 
                        {
                            startTime = System.nanoTime();

                            if (endtime != 0) 
                            {
                                additionalTime = Math.abs(Math.abs(sleeptime) - (startTime - endtime));
                            }

//                            System.out.println(additionalTime);
//                            System.out.println(loopTimeUPS);
                            //long startUpdateTime = System.nanoTime();
                            if (!paused)
                            {
                                gameUpdate();
                                updates++;
                            }
                            
                            //System.out.println("update time: " + (System.nanoTime() - startUpdateTime));

                            //long startRenderTime = System.nanoTime();
                            renderer.drawGameScreen(panel.getGraphics(), shipsToDraw, middleOfPlayer.x, middleOfPlayer.y,
                                    FPS, stars, camera, Version, UPS, paused, allShots, mapSize);
                            framesDrawn++;
                            //System.out.println("render time: " + (System.nanoTime() - startRenderTime));

                            // doesn't work
                            if (!OmegaCentauri.this.hasFocus()) 
                            {
                                //paused = true;
                            }

                            endtime = System.nanoTime();
                            sleeptime = loopTimeUPS - (endtime - startTime) - additionalTime;
                            //System.out.println(sleeptime);
                            timingEx.schedule(new UpdatingService(), sleeptime, TimeUnit.NANOSECONDS);
                            if (sleeptime < 0) 
                            {
                                sleeptime = 0;
                            }
                            endtime = System.nanoTime();
                        }
                    }
                    catch (Exception ex) 
                    {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }

    class LoadingService implements Runnable 
    {

        int numStarsPerQuadH = (int) Math.ceil(mapSize.width / 400D);
        int numStarsPerQuadV = (int) Math.ceil(mapSize.height / 400D);
        int max = (numStarsPerQuadH * numStarsPerQuadV) * 4;

        @Override
        public void run() 
        {
            SwingUtilities.invokeLater(new Runnable() 
            {

                @Override
                public void run() 
                {
                    try
                    {

                        for (int x = 1; x < mapSize.width; x += 400)
                        {
                            for (int y = 1; y < mapSize.height; y += 400)
                            {
                                stars.add(new StarChunk(x, y, 400, 8));
                                starChunksLoaded++;
                            }
                        }
                        // use active rendering to draw the screen
                        renderer.drawLoadingScreen(panel.getGraphics(), (starChunksLoaded / max) * 100, panel.getWidth(), panel.getHeight());

                        if (starChunksLoaded == max) 
                        {
                            loading = false;

                        }

                        if (loading) 
                        {
                            timingEx.schedule(new LoadingService(), 3, TimeUnit.MILLISECONDS);
                        }
                        else 
                        {
                            shipsToDraw.add(player.getControllingShip());
                            shipsToDraw.addAll(enemyShips);
                            shipsToDraw.addAll(allyShips);

                            recordingEx.schedule(new RecordingService(), 1, TimeUnit.SECONDS);
                            timingEx.schedule(new UpdatingService(), 1, TimeUnit.NANOSECONDS);
                        }

                    } 
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }

    class MainMenuService implements Runnable
    {
        @Override
        public void run() 
        {
            SwingUtilities.invokeLater(new Runnable()
            {

                @Override
                public void run()
                {
                    try 
                    {
                        if (mainMenu.isActive()) 
                        {
                            long start = System.currentTimeMillis();

                            if (mainMenu.getSize().x != OmegaCentauri.this.getWidth()
                                    || mainMenu.getSize().y != OmegaCentauri.this.getHeight()) 
                            {
                                mainMenu.setSize(OmegaCentauri.this.getWidth(), OmegaCentauri.this.getHeight());
                            }
                            mainMenu.checkMouseMoved(mouseLocation);

                            mainMenu.draw(panel.getGraphics());
                            long elapsed = System.currentTimeMillis() - start;
                            timingEx.schedule(new MainMenuService(), 15 - elapsed, TimeUnit.MILLISECONDS);
                        }
                    } 
                    catch (Exception ex) 
                    {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    
    class MouseChecker implements Runnable 
    {
        @Override
        public void run()
        {
            SwingUtilities.invokeLater(new Runnable() 
            {
                @Override
                public void run() 
                {
                    
                    mouseLocation.x = MouseInfo.getPointerInfo().getLocation().x
                            - OmegaCentauri.this.getLocationOnScreen().x
                            - borderSize.width;
                    
                    mouseLocation.y = MouseInfo.getPointerInfo().getLocation().y
                            - OmegaCentauri.this.getLocationOnScreen().y
                            -borderSize.height;
                    
                    mouseRecordingEx.schedule(new MouseChecker(), 10, TimeUnit.MILLISECONDS);
                }
            });
        }
    }

    private void resetGame() 
    {
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

    public static void main(String[] args) 
    {
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) 
        {
            e.printStackTrace();
        }

        Thread.setDefaultUncaughtExceptionHandler(new EDTExceptionHandler());
        System.setProperty("sun.awt.exception.handler", EDTExceptionHandler.class.getName());
//        System.setProperty("sun.java2d.opengl", "true");
//        System.setProperty("sun.java2d.ddscale", "true");
//        System.setProperty("sun.java2d.translaccel", "true");
//        System.setProperty("sun.java2d.d3d", "false");
//        System.setProperty("sun.java2d.translaccel","true");

        new OmegaCentauri();
    }
}
