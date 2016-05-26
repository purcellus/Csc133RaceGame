package a4;
//Note:  Sometimes, the layout doesn't load.  Have no idea why.
//I think it has to do with the (WHEN_IN_FOCUSED_WINDOW).
//to fix it, just adjust window size.


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import a4.collection.GameObjCollection;
import a4.command.Accelerate;
import a4.command.AddFuelCan;
import a4.command.AddOilSlick;
import a4.command.AddPylon;
import a4.command.Brake;
import a4.command.ChangeColors;
import a4.command.ChangeStrat;
import a4.command.CollideWithBird;
import a4.command.CollideWithCar;
import a4.command.CollideWithFuelCan;
import a4.command.CollideWithPylon;
import a4.command.Delete;
import a4.command.EnterOilSlick;
import a4.command.ExitOilSlick;
import a4.command.IPlayerCommand;
import a4.command.IPlayerCommandHolder;
import a4.command.Pause;
import a4.command.QuitGame;
import a4.command.Select;
import a4.command.SelectMany;
import a4.command.ShowMap;
import a4.command.SteerLeft;
import a4.command.SteerRight;
import a4.command.TickTime;
import a4.gameInterface.ISelectable;
import a4.gameObject.Car;
import a4.gameObject.FixedObject;
import a4.gameObject.GameObject;
import a4.gameObject.MoveableObject;
import a4.gameObject.OilSlick;
import a4.proxy.ViewGameWorldProxy;
import a4.view.MapView;
import a4.view.ScoreView;

public class Game extends JFrame implements ActionListener, KeyListener, IPlayerCommandHolder, MouseListener, MouseMotionListener
{//Controller of Observable, MVC
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int playerlives;
	private int clock;
	private int pylnumtoadd = 0;//for objects to add
	private int fuelsizetoadd = 0;
	private int FWIDTH = 1000;
	private int FHEIGHT = 600;
	//don't need to make a private JFrame, this class IS the JFrame
	private GameWorld g12world;//reference to eminem song, also a Model
	private ViewGameWorldProxy gpworld;//proxy world.
	private Sound bgm;
	
	private double oldmousex;
	private double oldmousey;
	
	private IPlayerCommand thecmds;//commands the player can invoke.
	
	private MapView mv;
	private ScoreView sv;
	
	private boolean sound = true;
	private boolean clickedone;
	private boolean isPaused;//if the game is paused
	
	private boolean addpylclick = false;//see if the addpylon was clicked
	private boolean addfcClick = false;
	
	private Timer thetick;
	
	private Accelerate acc;
	private AddOilSlick aos;
	private Brake brk;
	private ChangeColors chc;
	private CollideWithBird cwb;
	private ChangeStrat chs;
	private CollideWithCar cwc;
	private CollideWithFuelCan cwfc;
	private CollideWithPylon cwp;
	private EnterOilSlick enos;
	private ExitOilSlick exos;
	private Delete thed;
	private AddPylon addp;
	private AddFuelCan addfc;
	
	
	private QuitGame qgame;
	private ShowMap smap;
	private SteerLeft sleft;
	private SteerRight sright;
	private TickTime ttime;
	private Select sel;
	private SelectMany selm;
	private Pause pas;
	
	private int pylnum;//the pylon number the car collided with
	
	private JButton pauseButton;
	private JButton cWithNPC;

	private JButton switchStrat;
	private JButton quitButton;
	
	private JButton delButton;
	private JButton addpylButton;
	private JButton addfcButton;
	
	public Game(int clock, int plives)
	{//constructor to build clock and plives
		
		//System.out.println("Starting JFrame");
		
		playerlives = plives;
		g12world = new GameWorld(clock, playerlives);
		thetick = new Timer(g12world.getClockSpeed(),this);//time
		sound = g12world.getSound();
		bgm = g12world.getBGM();
		bgm.loop();
		gpworld = new ViewGameWorldProxy(clock, playerlives, g12world.getGameObjCollection(), g12world.getSound(), g12world.getMapWidth(), g12world.getMapHeight());//keep proxy consistent with actual
		
		acc = acc.getAccelerate(g12world);//instantiate commands, so Car can use them later and put null as gameworld
		aos = aos.getAddOilSlick(g12world);
		brk = brk.getBrake(g12world);
		chc = chc.getChangeColors(g12world);
		cwb = cwb.getCollideWithBird(g12world);
		chs = chs.getChangeStrat(g12world);
		cwc = cwc.getCollideWithCar(g12world);
		cwfc = cwfc.getCollideWithFuelCan(g12world);
		cwp = cwp.getCollideWithPylon(g12world);
		enos = enos.getEnterOilSlick(g12world);
		exos = exos.getExitOilSlick(g12world);
		thed = thed.getDelete(g12world);
		addp = addp.getAddPylon(g12world);
		addfc = addfc.getAddFuelCan(g12world);
		
		qgame = qgame.getQuitGame();
		smap = smap.getShowMap(gpworld);
		sleft = sleft.getSteerLeft(g12world);
		sright = sright.getSteerRight(g12world);
		ttime = ttime.getTickTime(g12world);
		
		
		
		
		
		this.clock = 0;
		
		mv = new MapView(gpworld);
		sv = new ScoreView(gpworld);
		
		gpworld.addObserver(mv);
		gpworld.addObserver(sv);
		
		setVisible(true);
		setSize(FWIDTH, FHEIGHT);//sets sizee of the WHOLE JFrame
		setLocation(100,100);
		setLayout(new BorderLayout());
		setTitle("Austin's Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem fileitem = new JMenuItem("New");
		fileitem.addActionListener(this);
		file.add(fileitem);
		fileitem = new JMenuItem("Save");
		fileitem.addActionListener(this);
		file.add(fileitem);
		fileitem = new JMenuItem("Sound");
		fileitem.addActionListener(this);
		file.add(fileitem);
		fileitem = new JMenuItem("About");
		fileitem.addActionListener(this);
		file.add(fileitem);
		fileitem = new JMenuItem("Quit");
		fileitem.addActionListener(this);
		file.add(fileitem);
		bar.add(file);//add file menu
		
		JMenu cmd = new JMenu("Commands");
		JMenuItem cmditem = new JMenuItem("Add Oil Slick");
		cmditem.addActionListener(this);
		cmd.add(cmditem);
		cmditem = new JMenuItem("New Colors");
		cmditem.addActionListener(this);
		cmd.add(cmditem);
		bar.add(cmd);//add command item
		setJMenuBar(bar);//add the whole jmenu
		
		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridLayout(0,1));//set the jpanel's layout to have one column
		//if you don't, even though it is in borderlayout, jp1 itself is the default layout
		JLabel butText = new JLabel("Commands");
		jp1.add(butText);
		

		int mapname = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap imap =  jp1.getInputMap(mapname);
		ActionMap amap = jp1.getActionMap();
		
		
		
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(this);
		pauseButton.setFocusable(false);
		jp1.add(pauseButton);
			
		
		delButton = new JButton("Delete Selected");
		delButton.addActionListener(this);
		delButton.setFocusable(false);
		delButton.setEnabled(false);
		jp1.add(delButton);
		
		addpylButton = new JButton("Add Pylon");
		addpylButton.addActionListener(this);
		addpylButton.setFocusable(false);
		addpylButton.setEnabled(false);
		jp1.add(addpylButton);
		
		addfcButton = new JButton("Add FuelCan");
		addfcButton.addActionListener(this);
		addfcButton.setFocusable(false);
		addfcButton.setEnabled(false);
		jp1.add(addfcButton);
		
		quitButton = new JButton("Quit");
		quitButton.addActionListener(this);
		quitButton.setFocusable(false);
		jp1.add(quitButton);
		
		jp1.requestFocus();
		
		add(jp1, BorderLayout.WEST);
		
		
		
		JPanel jp2 = new JPanel();
		jp2.setBorder(BorderFactory.createLineBorder(Color.BLUE));//lotta writing for making a border on this jpanel.
		
		add(jp2, BorderLayout.NORTH);
		
		
		
		MapView jp3 = mv;
		
		//add listeners to this panel, mapview
		mv.addMouseListener(this);//add mouse listener
		mv.addMouseMotionListener(this);//add mouse motion listeners
		
		//jp3.setLayout(new BorderLayout());
		jp3.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		add(jp3, BorderLayout.CENTER);
		//jp3.setFocusable(false);//so you can't write in the text box
		jp3.update( gpworld, null);
		
		ScoreView jp4 = sv;
		add(jp4, BorderLayout.NORTH);
		jp3.setFocusable(false);
		jp4.update(gpworld,null);
		
		addKeyListener(this);//for keyboard inputs
		
		setSize(FWIDTH, FHEIGHT);//sets sizee of the WHOLE JFrame
		

		thetick.start();//start the timer.
		thetick.setActionCommand("inctime");

	}
	
	public Timer getTheTick()
	{
		return thetick;
	}
	
	
	
	
	public void actionPerformed(ActionEvent e)
	{
	
		
		
		
		//buttons
		if (e.getSource() == switchStrat)
		{
			//System.out.println("StratButton Pressed.");
			chs = chs.getChangeStrat(g12world);
			setCommand(chs);
			getCommand().execute();
		}
		else if (e.getSource() == quitButton)
		{
			//System.out.println("QuitButton Pressed.");
			qgame = qgame.getQuitGame();
			setCommand(qgame);
			getCommand().execute();
		} else if (e.getSource() == delButton)
		{//delete selected objects
			//System.out.println("DeleteButton Pressed");
			thed = thed.getDelete(g12world);
			setCommand(thed);
			getCommand().execute();
			doupdate();
		} else if (e.getSource() == addpylButton)
		{//add pylon, then set new max num of pylons for game world and cars
			//System.out.println("AddPylButton pressed");

			
			addp = addp.getAddPylon(g12world);
			JOptionPane jpane = new JOptionPane();
			String result = jpane.showInputDialog("Pylon number to add:");
			if (result != null)
			{//if this isn't a number.
				pylnumtoadd = Integer.parseInt(result);//turn result into an integer
				addpylclick = true;
				addfcClick = false;
			}
			
			//setCommand(addp);
			//getCommand().execute();
			//doupdate();
		} else if (e.getSource() == addfcButton)
		{//add fuel can
			//System.out.println("AddFuelCan button Pressed");
			addfc = addfc.getAddFuelCan(g12world);
			
			
			JOptionPane jpane = new JOptionPane();
			String result = jpane.showInputDialog("Fuel Can size:");
			if (result != null)
			{
				fuelsizetoadd = Integer.parseInt(result);//turn result into an integer
				addpylclick = false;
				addfcClick = true;
			}

			
			
			//setCommand(addfc);
			//getCommand().execute();
			//doupdate();
		} else if (e.getSource() == pauseButton)
		{//pause the game
			pas = pas.getPause(this);//send the game there
			if (isPaused)
			{//if the game is already paused
				if (g12world.getSound())
				{//if the sound is enabled.
					bgm.loop();//restart looping.
				}
				pas.execute();//thetick.start();//continue where we left off
				pauseButton.setText("Pause");
				delButton.setEnabled(false);//can't do these things while not paused.
				addpylButton.setEnabled(false);
				addfcButton.setEnabled(false);
				
				isPaused = false;
			} else
			{//if the game isn't paused
			
				bgm.stop();//stop the sounds.

				pas.execute();//thetick.stop();//stop the timer
				pauseButton.setText("Resume");
				delButton.setEnabled(true);//can do these things while not paused.
				addpylButton.setEnabled(true);
				addfcButton.setEnabled(true);
				
				isPaused = true;
			}
			mv.setPaused(isPaused);
		}
		
		//menu, make sure the time action commmand has a name.
		if (e.getActionCommand().equals("inctime"))
		{
			//do this every time the clock ticks
			clock = clock + 1;//increment pseudo clock counter.
			ttime = ttime.getTickTime(g12world);
			setCommand(ttime);
			getCommand().execute();
			doupdate();
		}
		if (e.getActionCommand().equals("Fuel Pick Up"))
		{
			cwfc = cwfc.getCollideWithFuelCan(g12world);
			setCommand(cwfc);
			getCommand().execute();
		} else if (e.getActionCommand().equals("Add Oil Slick"))
		{
			aos = aos.getAddOilSlick(g12world);
			setCommand(aos);
			getCommand().execute();
		} else if (e.getActionCommand().equals("New Colors"))
		{
			chc = chc.getChangeColors(g12world);
			setCommand(chc);
			getCommand().execute();
			
		} else if (e.getActionCommand().equals("Quit"))
		{
			qgame = qgame.getQuitGame();
			setCommand(qgame);
			getCommand().execute();
		} else if (e.getActionCommand().equals("Sound"))
		{
			bgm.stop();
			sound = !sound;
			//System.out.println("Sound is currently " + sound);
			g12world.setSound(sound);
			if (g12world.getSound())
			{
				if (!isPaused)
				{//if the game is paused
					bgm.loop();
				}
			} else
			{
				System.out.println("STOP THE MUSIC");
				bgm.stop();
			}
			doupdate();
			
		} else if (e.getActionCommand().equals("About"))
		{
			JOptionPane jpane = new JOptionPane();
			jpane.showMessageDialog(jpane, "Made By Austin Purcell for Csc133.");
		}
		

	}
	


	@Override
	public IPlayerCommand getCommand() 
	{
		return thecmds;
	}

	@Override
	public void setCommand(IPlayerCommand thecmd) 
	{
		thecmds = thecmd;
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		//System.out.println("No KeyReleased Action");
	}

	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		//System.out.println("No KeyTyped Action");
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (!isPaused)
		{//only do these if not paused
			if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_UP)
			{//accelerate
				acc = acc.getAccelerate(g12world);
				setCommand(acc);
				getCommand().execute();
			} else if (e.getKeyCode() == KeyEvent.VK_B ||  e.getKeyCode() == KeyEvent.VK_DOWN)
			{//brake
				brk = brk.getBrake(g12world);
				setCommand(brk);
				getCommand().execute();
			}else if (e.getKeyCode() == KeyEvent.VK_L ||  e.getKeyCode() == KeyEvent.VK_LEFT)
			{//left turn
				sleft = sleft.getSteerLeft(g12world);
				setCommand(sleft);
				getCommand().execute();
			}else if (e.getKeyCode() == KeyEvent.VK_R ||  e.getKeyCode() == KeyEvent.VK_RIGHT)
			{//right turn
				sright = sright.getSteerRight(g12world);
				setCommand(sright);
				getCommand().execute();
			}else if (e.getKeyCode() == KeyEvent.VK_O)
			{//add oil slick
				aos = aos.getAddOilSlick(g12world);
				setCommand(aos);
				getCommand().execute();
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_Q)
		{//quit
			qgame = qgame.getQuitGame();
			setCommand(qgame);
			getCommand().execute();
		}else if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{//change strategies
			chs = chs.getChangeStrat(g12world);
			setCommand(chs);
			getCommand().execute();
		} else if (e.getKeyCode() == KeyEvent.VK_DELETE)
		{
			if (isPaused)
			{//only delete if the game is paused
				thed = thed.getDelete(g12world);
				setCommand(thed);
				getCommand().execute();
				doupdate();
			}
		}
	}
	
	
	public void doupdate()
	{//does update functions for mapview and scoreview
		
		gpworld = new ViewGameWorldProxy(g12world.getClock()/(1000/g12world.getClockSpeed()), g12world.getPlayerLives(), g12world.getGameObjCollection(), g12world.getSound(), g12world.getMapWidth(), g12world.getMapHeight());//keep proxy consistent with actual
		//keep it consistent by sending these values. TODO see best number above clockspeed
		mv.update(gpworld, null);
		sv.update(gpworld, null);
		mv.repaint();//repaint the graphics of mapview, which is a JPanel
	}






	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}






	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}






	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}






	@Override
	public void mousePressed(MouseEvent e)
	{
		//System.out.println(mv.getWidth());
		//apply the VTM inverse
		oldmousex = e.getPoint().getX();
		oldmousey = e.getPoint().getY();
		
		
		//First, we will invert the point using VTM, which turns it from screen, to normalized device, to the world.
		Point2D VTMe = mv.getInverseVTM().transform(e.getPoint(), null);
		Point VMTp = new Point((int) VTMe.getX(), (int) VTMe.getY());
		
		
		if (isPaused)
		{//only select/add things  if the game is paused
			
			
			
			
			
			if (e.isControlDown())
			{//if control button is down and a button is pressed
				//System.out.println("Button one AND Ctrl pushed down");
				
				selm = selm.getSelectMany(g12world);
				selm.execute(VMTp);
				
				
				doupdate();//update screen even if time is frozen.
				
			} else if (e.getButton() == MouseEvent.BUTTON1)
			{//if mouse button 1 was pressed
				//System.out.println("Mouse Button 1 clicked");
				//System.out.println(getMousePosition());
				
				if (addpylclick)
				{//if we are adding a pylon
					//System.out.println("ADDING PYLON ");
					
					setCommand(addp);
					//Switched the getPoint() from e to the VTM e
					addp.execute(((double)VMTp.getX()), ((double)VMTp.getY()), pylnumtoadd);
					
					doupdate();
					addpylclick = false;
				} else if (addfcClick)
				{//if we are adding a fuel can
					//System.out.println("ADDING FUEL CAN ");
					
					setCommand(addp);
					addfc.execute(((double)VMTp.getX()), ((double)VMTp.getY()), fuelsizetoadd);
					//I have to execute it like this, and remember, use point to make it relative to jpanel
					
					doupdate();
					
					
					addfcClick = false;
				} else 
				{//default
					sel = sel.getSelect(g12world);
					sel.execute(VMTp);
					doupdate();//TODO figure out how to make NPCCars stop by not updating it.
				}

			}
			//doupdate();//don't put this here, or else the NPC rotors will continue to move after pausing.
		}
		
	}






	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		//System.out.println("Mouse was Dragged");
		double mousex = arg0.getPoint().getX();//This is always positive.  FIX THIS
		double mousey = arg0.getPoint().getY();//this is relative to the point.
		
		if (!isPaused)
		{
			mv.pan(oldmousex - mousex, oldmousey - mousey);
			repaint();
		}
		oldmousex = mousex;
		oldmousey = mousey;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		//System.out.println("Mouse was moved");
	}
	
	public AffineTransform getInverseVTM()
	{
		return mv.getInverseVTM();
	}
	

}
