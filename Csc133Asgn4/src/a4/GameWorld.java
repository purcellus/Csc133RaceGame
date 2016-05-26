package a4;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;
import java.util.Observable;

import a4.collection.GameObjCollection;
import a4.gameInterface.IGameWorldProxy;
import a4.gameObject.Bird;
import a4.gameObject.Car;
import a4.gameObject.FixedObject;
import a4.gameObject.FuelCan;
import a4.gameObject.GameObject;
import a4.gameObject.MoveableObject;
import a4.gameObject.NPCCar;
import a4.gameObject.OilSlick;
import a4.gameObject.Pylon;
import a4.gameObject.Wall;
import a4.proxy.ViewGameWorldProxy;
import a4.view.MapView;
import a4.view.ScoreView;

public class GameWorld extends Observable implements IGameWorldProxy
{
	private GameObjCollection gobjcol = new GameObjCollection();//collection of game objects.
	private int clockspeed;//the speed of the time, in millis
	
	private static int numofpyl = 4;// maximum number of pylons, I can make more, but this is the "active game" limit.
	private static int numofFcan = 2;// maximum number of fuel cans
	private int numofnpc = 50;
	private static int numofbirds = 2;//max number of birds
	
	private int pyltomake = 1;//this indicates what pylon will be made when createPylon() is called
	
	private final float flocx = 100;//for pylon, shouldn't change.  ever.
	private final float flocy = 100;
	
	private Pylon pyl1;//the most important pylon
	private Car playercar;//the most important car
	
	private int mapwidth = 700;//based on game's map size
	private int mapheight = 500;
	private int clock;
	private int playerlives;
	private int pylnum = 2;//where car should be
	private boolean sound = true;//if sound is on or off
	private Sound bgm;
	
	private ViewGameWorldProxy vgworldp;//a proxy to be sent to MapView and ScoreView
	
	private Vector<FixedObject> preparefixedobjects = new Vector<FixedObject>();//oil slicks to be added after the tick, not at the oil slick add method.

	
	public GameWorld(int clock, int playerlives)
	{//constructor
		this.clock = clock;
		this.playerlives = playerlives;
		this.clockspeed = 20;
		
		String soundDir = "." + File.separator + "sounds" + File.separator;
		//filenames of sounds
		String filename = "thebgm.wav";
		String filepath = soundDir + filename;
		bgm = new Sound(filepath);
		//bgm.loop();
		
		initLayout();//initialize layout

	}
	
	public Sound getBGM()
	{
		return bgm;
	}
	
	public int getMapWidth()
	{
		return mapwidth;
	}
	
	public void setMapWidth(int mapw)
	{
		mapwidth = mapw;
	}
	
	public int getMapHeight()
	{
		return mapheight;
	}
	
	public void setMapHeight(int height)
	{
		this.mapheight = height;
	}
	
	public int getPylNum()
	{
		return pylnum;
	}
	
	public void setPylNum(int pnum)
	{
		pylnum = pnum;
	}
	
	public int getPylToMake()
	{
		return pyltomake;
	}
	
	public void setPylToMake(int pyln)
	{
		pyltomake = pyln;
	}
	
	public void initLayout()
	{
		pyl1 = createFirstPylon();
		playercar = createPlayerCar();
		gobjcol.add(pyl1);
		gobjcol.add(playercar);
		int pylc = 2;//2 because work on 2nd pylon
		while (pylc <= numofpyl)
		{//make pylons
			//System.out.println("Adding pylons using Factory Design");
			gobjcol.add(createPylon(pylc));//put newly created pylon in list
			pylc++;
			pyltomake = pylc;
		}
		int fcc = 1;//fuel can counter
		while (fcc <= numofFcan)
		{//make fuel cans
			
			FuelCan forstring = createFuelCan();
			//System.out.println(fcc + "  " + forstring);
			gobjcol.add(forstring);
			fcc++;
		}
		int bc = 1;//bird counter 
		while (bc <= numofbirds)
		{
			//System.out.println("adding birds using factory design");
			
			gobjcol.add(createBird());
			bc++;
		}
		int npcc = 1;//npcCar counter
		while (npcc <= numofnpc)
		{//TODO FIX THIS
			gobjcol.add(createNPC());
			npcc++;
		}
		
		
		/*int numwall = 1;//four walls
		boolean iswall = true;
		while (numwall <= 4)
		{//make four walls
			Point2D.Float wallc = new Point2D.Float();
			if (numwall == 1)
			{//if this is the top horiz wall
				wallc.setLocation(500, 0);
			} else if (numwall == 2)
			{//left vert
				wallc.setLocation(0, 400);
			} else if (numwall == 3)
			{//bot horiz
				wallc.setLocation(500, 800);
			} else
			{//right vert
				wallc.setLocation(1000, 400);
			}
			
			gobjcol.add(createWall(iswall, wallc, numwall));
			numwall++;//don't forget this.
		}*/
		
		
		//System.out.println("Finished making world");
		
		
	}
	
	

	

	public GameObjCollection getGameObjCollection()
	{
		return gobjcol;
	}
	
	public void setGameObjCollection(GameObjCollection gcol)
	{
		gobjcol = gcol;
	}


	@Override
	public void addObserver(Observer obs) 
	{
		// TODO Auto-generated method stub
		//obs.update(this,null);
		System.out.println("Probably shouldn't put observers to GameWorld, use proxy");
	}

	@Override
	public void notifyObservers() 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void setClock(int t)
	{
		clock = t;
	}
	
	public int getClock()
	{
		return clock;
	}
	
	
	public int getNumOfNPC() 
	{
		// TODO Auto-generated method stub
		return numofnpc;
	}
	
	
	
	
	
	public Pylon createPylon(int pyltomake)
	{//creates a pylon at a random location
		Random rand= new Random();
		float locx = rand.nextFloat()*mapwidth;
		float locy = rand.nextFloat()*mapheight;;
		Pylon pyltoadd = new Pylon( Color.ORANGE, pyltomake);
		pyltoadd.translate(locx, locy);
		return pyltoadd;
	}
	
	public Pylon createFirstPylon()
	{
		//no need for location
		pyl1 = new Pylon(  Color.ORANGE, 1);//generate the first pylon, I think this one is important, and make it alone
		pyl1.translate(flocx, flocy);
		return pyl1;
	}
	
	public Pylon createPylon(double locx, double locy, int pyltomake)
	{//creates a pylon based on mouse position
		Pylon pyltoadd = new Pylon( Color.ORANGE, pyltomake);
		pyltoadd.translate(locx, locy);
		
		return pyltoadd;
	}
	
	public FuelCan createFuelCan()
	{//creates a fuel can
		Random rand = new Random();
		int size = rand.nextInt(50) + 1;//fuel cans better get some fuel, so add 1
		float locx = rand.nextFloat()*mapwidth;
		float locy = rand.nextFloat()*mapheight;
		Point2D.Float location = new Point2D.Float(locx, locy);//make new point for random pylon locations
		FuelCan fc =  new FuelCan( Color.BLACK, size);
		fc.translate(locx, locy);
		return fc;
	}
	
	public FuelCan createFuelCan(double mousex, double mousey, int size)
	{
		FuelCan fctoadd = new FuelCan(Color.BLACK, size);
		fctoadd.translate(mousex, mousey);
		return fctoadd;
	}

	public Bird createBird()
	{//creates a bird
		Random rand = new Random();
		int speed = rand.nextInt(3);
		float locx = rand.nextFloat()*mapwidth;
		float locy = rand.nextFloat()*mapheight;
		Bird thebird = new Bird(speed);
		thebird.translate(locx, locy);
		return thebird;
		
	}
	

	public Car createPlayerCar()
	{//creates a car
		Car thecar = new Car( Color.blue, numofpyl);//generate the player car next to the first pylon.
		String soundDir = "." + File.separator + "sounds" + File.separator;
		//filenames of sounds
		String filename = "pickupfc.wav";
		String filepath = soundDir + filename;
		thecar.setGetFuelSound(filepath);
		
		filename = "npcCollide.wav";
		filepath = soundDir + filename;
		thecar.setHitNPC(filepath);
		
		filename = "dead.wav";
		filepath = soundDir + filename;
		thecar.setLoseLife(filepath);
		
		thecar.translate(flocx, flocy);
		
		return thecar;

	}
	
	public NPCCar createNPC()
	{
		Random rand = new Random();
		//change code here to change starting point of NPC cars
		float locx = 500;
		float locy = 500;
		NPCCar thenpc = new NPCCar(Color.MAGENTA, numofpyl, getGameObjCollection());
		thenpc.translate(locx, locy);
		return thenpc;
	}
	
	public NPCCar createNPC(double mousex, double mousey)
	{
		return new NPCCar( Color.MAGENTA, 2, getGameObjCollection());
	}
	
	public Wall createWall(boolean iswall, Point2D.Float wallc, int walltype)
	{
		return new Wall(iswall, wallc, walltype);
	}

	public void addToCol(GameObject gobj)
	{
		gobjcol.add(gobj);
	}
	
	public int getPlayerLives() 
	{
		// TODO Auto-generated method stub
		return playerlives;
	}

	public void setPlayerLives(int plives) 
	{
		// TODO Auto-generated method stub
		this.playerlives = plives;
	}

	@Override
	public boolean getSound() 
	{
		// TODO Auto-generated method stub
		return sound;
	}
	
	public void setSound(boolean sound)
	{
		this.sound = sound;
		System.out.println("Swapping sounds in Game world");
		playercar.setSound(sound);
	}
	
	public int getNumOfPyl()
	{
		return numofpyl;
	}
	
	public void setNumOfPyl(int nopyl)
	{
		numofpyl = nopyl;
	}

	public int getClockSpeed() 
	{//to keep things consistent
		return clockspeed;
	}

	public void setClockSpeed(int clockspeed) 
	{
		this.clockspeed = clockspeed;
	}


}
