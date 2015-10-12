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
public class OmegaCentauri extends JFrame implements GameActionListener 
{
    private final String Version = "Dev 1.0.5";
    // GAME STATE VARIBLES:
    private boolean forward, rotateRight, rotateLeft, shooting = false; // movement booleans
    private boolean paused = false;
    private boolean loading = false;
    private Point2D.Double middleOfPlayer = new Point2D.Double(); // SCREEN LOCATION of the middle of the player
    private ArrayList<Command> updateCommands = new ArrayList<>();
    private HashMap<Integer, Boolean> keysDown = new HashMap<>();
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
    private final GameData gameData = new GameData();
    private final ArrayList<Ship> allShips = new ArrayList<>();
    private final ArrayList<Enemy> enemyShips = new ArrayList<>();
    private final ArrayList<Ally> allyShips = new ArrayList<>();
    private final HashSet<Shot> allShots = new HashSet<>();
    private final HashSet<Shot> enemyShots = new HashSet<>();
    private final HashSet<Shot> allyShots = new HashSet<>();
    private final HashSet<GameEntity> gameEntitys = new HashSet<>();
    private final ArrayList<Ship> deadShips = new ArrayList<>();
    private final ArrayList<Shot> deadShots = new ArrayList<>();
    private GameOverListener gameOverListener;
    private Player player;
    // TIMERS
    private ScheduledExecutorService timingEx;
    private ScheduledExecutorService recordingEx;
    private ScheduledExecutorService mouseRecordingEx;
    // LOADING VARIBLES:
    private int[] yPositions;
    private int starChunksLoaded = 0;
    private ArrayList<StarChunk> stars = new ArrayList<>();
    
    public OmegaCentauri() 
    {
        /* change this one, Michael */ mapSize = new Dimension(10000, 10000);

        yPositions = new int[]{-mapSize.width, -mapSize.height, 0, 0};

        resources = new Resources();
        gameData.updateResources(resources);
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        renderer = new Renderer();
        gameOverListener = renderer.getHUD();

        camera = new Camera(1000, 600);
        loading = true;

        addShips();

        setUpWindow();
        gameData.updateCameraSize(camera.getSize());
    }

    private void addShips() 
    {
        player = new Player();
        allyShips.add(new Fighter(2800, 5000, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
        player.controlShip(allyShips.get(0));
        // below
        allyShips.add(new Fighter(3200, 5300, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
        allyShips.add(new Fighter(3200, 5600, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
        allyShips.add(new Fighter(3200, 5900, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
       // allyShips.add(new Fighter(3200, 6200, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
//        // above
//        allyShips.add(new Fighter(3200, 4700, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
//        allyShips.add(new Fighter(3200, 4400, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
//        allyShips.add(new Fighter(3200, 4100, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
//        allyShips.add(new Fighter(3200, 3800, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
        // behind
//        allyShips.add(new Fighter(2400, 5300, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
//        allyShips.add(new Fighter(2400, 5600, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
//        allyShips.add(new Fighter(2400, 5900, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
//        allyShips.add(new Fighter(2400, 6200, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
//        
//        allyShips.add(new Fighter(2400, 4700, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
//        allyShips.add(new Fighter(2400, 4400, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
//        allyShips.add(new Fighter(2400, 4100, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
//        allyShips.add(new Fighter(2400, 3800, MainPackage.Type.Fighter, 8, 4, 4, .15, 150, 100, this));
       // enemyShips.add(new EnemyFighter(3500, 5000, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 1, this));
        //enemies
//        enemyShips.add(new EnemyMediumFighter(7200, 5000, MainPackage.Type.Cruiser, 3, 2, 1, .15, 300, 4000, 200, 1, this));
//        enemyShips.add(new EnemyMediumFighter(7200, 5500, MainPackage.Type.Cruiser, 3, 2, 1, .15, 300, 4000, 200, 2, this));
//        enemyShips.add(new EnemyMediumFighter(7200, 4500, MainPackage.Type.Cruiser, 3, 2, 1, .15, 300, 4000, 200, 3, this));
         //below
        enemyShips.add(new EnemyFighter(6800, 5300, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 1, this));
        enemyShips.add(new EnemyFighter(6800, 5600, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 2, this));
        enemyShips.add(new EnemyFighter(6800, 5900, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 3, this));
        enemyShips.add(new EnemyFighter(6800, 6200, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 4, this));
        //above
        enemyShips.add(new EnemyFighter(6800, 4700, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 5, this));
        enemyShips.add(new EnemyFighter(6800, 4400, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 6, this));
        enemyShips.add(new EnemyFighter(6800, 4100, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 7, this));
        enemyShips.add(new EnemyFighter(6800, 3800, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 8, this));
         //behind
        enemyShips.add(new EnemyFighter(7600, 5300, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 9, this));
        enemyShips.add(new EnemyFighter(7600, 5600, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 10, this));
        enemyShips.add(new EnemyFighter(7600, 5900, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 11, this));
        enemyShips.add(new EnemyFighter(7600, 6200, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 12, this));

        enemyShips.add(new EnemyFighter(7600, 4700, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 13, this));
        enemyShips.add(new EnemyFighter(7600, 4400, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 14, this));
        enemyShips.add(new EnemyFighter(7600, 4100, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 15, this));
        enemyShips.add(new EnemyFighter(7600, 3800, MainPackage.Type.Fighter, 3, 5, 5, .15, 300, 50, 16, this));
        
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
        
        keysDown.put(KeyEvent.VK_W, false);
        keysDown.put(KeyEvent.VK_A, false);
        keysDown.put(KeyEvent.VK_D, false);

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
                if (!keysDown.get(KeyEvent.VK_W)) // so if it's not down, we need to change it
                {
                    setKeyPressedOrReleased(KeyEvent.VK_W, true);
                    handleInput(KeyEvent.VK_W, true);
                }
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "Wr");
        panel.getActionMap().put("Wr", new AbstractAction() 
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (keysDown.get(KeyEvent.VK_W))
                {
                    setKeyPressedOrReleased(KeyEvent.VK_W, false);
                    handleInput(KeyEvent.VK_W, false);
                }
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "D");
        panel.getActionMap().put("D", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!keysDown.get(KeyEvent.VK_D))
                {   
                    setKeyPressedOrReleased(KeyEvent.VK_D, true);
                    handleInput(KeyEvent.VK_D, true);
                }
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "Dr");
        panel.getActionMap().put("Dr", new AbstractAction() 
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (keysDown.get(KeyEvent.VK_D))
                {
                    setKeyPressedOrReleased(KeyEvent.VK_D, false);
                    handleInput(KeyEvent.VK_D, false);
                }
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "A");
        panel.getActionMap().put("A", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!keysDown.get(KeyEvent.VK_A))
                {
                    setKeyPressedOrReleased(KeyEvent.VK_A, true);
                    handleInput(KeyEvent.VK_A, true);
                }
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "Ar");
        panel.getActionMap().put("Ar", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (keysDown.get(KeyEvent.VK_A))
                {
                    setKeyPressedOrReleased(KeyEvent.VK_A, false);
                    handleInput(KeyEvent.VK_A, false);
                }
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "Space");
        panel.getActionMap().put("Space", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                handleInput(KeyEvent.VK_SPACE, true);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "Spacer");
        panel.getActionMap().put("Spacer", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleInput(KeyEvent.VK_SPACE, false);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, false), "Q");
        panel.getActionMap().put("Q", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleInput(KeyEvent.VK_Q, true);
            }
        });

        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Esc");
        panel.getActionMap().put("Esc", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleInput(KeyEvent.VK_ESCAPE, true);
            }
        });
    }
//</editor-fold>
    
    private void setKeyPressedOrReleased(int keycode, boolean down)
    {
         keysDown.put(keycode, down);
    }
    
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
    
    @Override
    public void entityDoneExploding(GameEntity entity)
    {
        if (entity instanceof Ship)
        {
            Ship e = (Ship)entity;
            deadShips.add(e);
        }
        else if (entity instanceof Shot)
        {
            Shot s = (Shot)entity;
            deadShots.add(s);
        }
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
                gameData.updateCameraSize(camera.getSize());
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
            
            for (int i = deadShots.size() - 1; i > -1; i--)
            {
                gameEntitys.remove(deadShots.get(i));
                allShots.remove(deadShots.get(i));
                enemyShots.remove(deadShots.get(i));
                allyShots.remove(deadShots.get(i));
                deadShots.get(i).getOwner().removeShot(deadShots.get(i));
                deadShots.remove(deadShots.get(i));
            }
            
            for (int i = deadShips.size() -1; i > -1; i--)
            {
                if (deadShips.get(i) instanceof Enemy)
                {
                    System.out.println("enemy died");
                }
                else if (deadShips.get(i) instanceof Ally)
                {
                    System.out.println("ally died");
                }
                gameEntitys.remove(deadShips.get(i));
                allShips.remove(deadShips.get(i));
                enemyShips.remove(deadShips.get(i));
                allyShips.remove(deadShips.get(i));
                if (player.getControllingShip().equals(deadShips.get(i)))
                {
                    if (!allyShips.isEmpty())
                    {
                        player.controlShip(allyShips.get(0));
                        gameEntitys.remove(player.getControllingShip());
                    }
                    else
                    {
                        gameOverListener.gameOver();
                    }
                }
                deadShips.remove(deadShips.get(i));
            }
            
            player.update(updateCommands);
            
            updateCommands.clear();
            
            for (GameEntity entity : gameEntitys)
            {
                entity.update();
            }
            
            for (Enemy s : enemyShips)
            {
                enemyShots.addAll(s.getShots());
            }
            
            for (Ally s : allyShips)
            {
                allyShots.addAll(s.getShots());
            }
            
            allShots.addAll(enemyShots);
            allShots.addAll(allyShots);
            gameEntitys.addAll(allShots);
                        
            for (Shot shot : allShots) 
            {
                if (!shot.isRegistered())
                {
                    shot.registerListener(this); // makes sure the shot can tell OC if it is done exploding
                }
                
                if (shot.outOfRange())
                {
                    shot.explode();
                }
            }
            
            for (Ship ship : allShips)
            {
                if (ship.getShield().isActive()) 
                {
                    ship.getShield().decay();
                }
            }

            for (int i = allyShips.size() - 1; i > -1; i--) // to avoid ConcurrentModificationExeption 
            {
                for (Shot shot : enemyShots) 
                {
                    testForCollision(allyShips.get(i), shot);
                }
                allyShips.get(i).purgeShots(mapSize);
            }
            
            for (int i = enemyShips.size() - 1; i > -1; i--) // to avoid ConcurrentModificationExeption
            {
                for (Shot shot : allyShots)
                {
                    testForCollision(enemyShips.get(i), shot);
                }
                enemyShips.get(i).purgeShots(mapSize);
            }

            for (Shot shot : allyShots) 
            {
                for (Shot collisionShot : enemyShots) 
                {
                    if (!shot.isDying() && !collisionShot.isDying())
                    {
                        if (Calculator.getDistance(shot.getLocation(), collisionShot.getLocation()) < 500) 
                        {   
                            if (shot.returnHitbox().collides(collisionShot.returnHitbox())) 
                            {
                                boolean shotGotRemoved = shot.collisionEventWithShot(shot, collisionShot, allShips);
                                boolean collisionShotGotRemoved = collisionShot.collisionEventWithShot(collisionShot, shot, allShips);

                                if (shotGotRemoved)
                                {
                                    shot.explode();
                                }
                                if (collisionShotGotRemoved) 
                                {
                                    collisionShot.explode();
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
    
    private void testForCollision(Ship ship, Shot shot)
    {
        if (!shot.isDying() && !ship.isExploding())
        {
            if (Calculator.getDistance(ship.getLocation(), shot.getLocation()) < 500)
            {
                if (ship.returnHitbox().collides(shot.returnHitbox()))
                { 
                    boolean[] removals;
                    if (ship.getShield().getEnergy() > 0)
                    {
                        EllipseHitbox hitbox = (EllipseHitbox)ship.returnHitbox();
                        removals = ship.CollisionEventWithShotWithShield(ship, shot, allShips,
                                hitbox.getAngleOnEllipse(shot.returnHitbox()), hitbox.getAngleToHitbox(shot.returnHitbox()));
                    }
                    else
                    {
                         removals = ship.CollisionEventWithShotWithHull(ship, shot, allShips);
                    }
                    if (removals[0]) // ship
                    {
                        ship.explode();
                    }
                    
                    if (removals[1]) // shot
                    {
                        shot.explode();
                    }
                }
            }
        }
    }
    
    private void handleInput(int keycode, boolean down)
    {
        //System.out.println("called");
        switch (player.getImageRotationState())
        {
            case Idle: //neither
                if (down && keycode == KeyEvent.VK_A)
                {
                    rotateLeft = true;
                }
                if (down && keycode == KeyEvent.VK_D) 
                {
                    rotateRight = true; 
                }
                break;
            case rotatingRight: // d has to be down
                if (down && keycode == KeyEvent.VK_A) // start going left
                {
                    rotateRight = false;
                    rotateLeft = true;
                }
                else if (!down && keycode == KeyEvent.VK_D)
                {
                    rotateRight = false;
                    if (keysDown.get(KeyEvent.VK_A))
                    {
                        rotateLeft = true;
                    }
                }
                break;
            case rotatingLeft: // a has to be down
                if (down && keycode == KeyEvent.VK_D)
                {
                    rotateLeft = false;
                    rotateRight = true;
                }
                else if (!down && keycode == KeyEvent.VK_A)
                {
                    rotateLeft = false;
                    if (keysDown.get(KeyEvent.VK_D))
                    {
                        rotateRight = true;
                    }
                }
                break;
        }
        
        switch(player.getImageMovementState())
        {
            case Idle: // w isn't down
                if (down && keycode == KeyEvent.VK_W)
                {
                    forward = true;
                }
                break;
            case Thrusting:
                if (!down && keycode == KeyEvent.VK_W)
                {
                    forward = false;
                }
                break;
        }
        
        if (down && keycode == KeyEvent.VK_SPACE)
        {
            shooting = true;
        }
        else if (!down && keycode == KeyEvent.VK_SPACE)
        {
            shooting = false;
        }
        
        if (down && keycode == KeyEvent.VK_Q)
        {
            System.exit(0);
        }
        
        if (down && keycode == KeyEvent.VK_ESCAPE)
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
        gameData.updateEnemyShips(enemyShips);
        gameData.updateAllyShips(allyShips);
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
                            renderer.drawGameScreen(panel.getGraphics(), allShips, middleOfPlayer.x, middleOfPlayer.y,
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
                            allShips.add(player.getControllingShip());
                            allShips.addAll(enemyShips);
                            allShips.addAll(allyShips);
                            gameEntitys.addAll(allShips);
                            gameEntitys.remove(player.getControllingShip());

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
        allShips.clear();
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
