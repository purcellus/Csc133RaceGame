package a4.gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import a4.ISteerable;
import a4.Sound;
import a4.command.AddOilSlick;
import a4.command.CollideWithBird;
import a4.command.CollideWithCar;
import a4.command.CollideWithFuelCan;
import a4.command.CollideWithPylon;
import a4.command.EnterOilSlick;
import a4.command.ExitOilSlick;
import a4.gameInterface.ICollider;
import a4.gameInterface.IDrawable;
import a4.gameInterface.ITransformer;
import a4.gameObject.carparts.CarAxle;
import a4.gameObject.carparts.CarWheel;


public class Car extends MoveableObject implements ISteerable, IDrawable, ICollider, ITransformer
{
	private Color color;
	private int heading;
	private int maxheading = 40;
	private int oldsteer;//for rotating wheels
	private int steeringdirection;
	private int speed;
	private int maxspeed;
	private int width;
	private int height;
	private boolean traction = true;//whether the car can turn, accelerate, brake, etc.
	private boolean inoil = false;
	private int fuellevel;
	private int maxfuel = 10000;
	private int damagelevel;
	private double angle;//set as double to deal with trig operations involving Math.toRadians();
	private int maxdamagelevel = 40;//car can take up to this many hits
	private int curpylon = 2;//detects what pylon the car should hit
	private int maxpylon;//the max pylon to hit.
	private int hitdelay;//current tick delay to be damaged again.
	private int maxhitdelay = 50;//the time it takes to be damaged again, is arbitrary so that the car can get away
	private boolean beenhit = false;//if the player has been hit
	
	private AffineTransform mytranslate;
	private AffineTransform myrotate;
	private AffineTransform myscale;

	private CarWheel wheel1, wheel2, wheel3, wheel4;
	private CarAxle axle1, axle2;
	
	private CollideWithPylon cwp;
	private CollideWithFuelCan cwfc;
	private CollideWithBird cwb;
	private CollideWithCar cwc;
	private ExitOilSlick exos;
	private EnterOilSlick enos;
	private AddOilSlick aos;
	
	private Sound getfuel;
	private Sound hitNPC;
	private Sound loselife;

	public Car( Color carcolor, int maxpylon)
	{
		
		setColor(carcolor);
		setDamageLevel(0);//always starts with 0 damage
		setHeading(0);
		setSteeringDirection(0);
		oldsteer = 0;
		maxspeed = 10;
		speed = 0;
		angle = 0;//for trigonometry, added to heading each tick to go into circles.
		width = 30;
		height = 20;
		setFuelLevel(maxfuel);
		traction = true;
		this.maxpylon = maxpylon;
		hitdelay = maxhitdelay;//car can be hit immediately after spawning
		mytranslate = new AffineTransform();//transformations for the car
		myrotate = new AffineTransform();
		myscale = new AffineTransform();
		
		wheel1 = new CarWheel();
		wheel1.translate(width/2, (height/2 + 5));
		wheel2 = new CarWheel();
		wheel2.translate(width/2, -(height/2 + 5));
		wheel3 = new CarWheel();
		wheel3.translate(-width/2, (height/2 + 5));
		wheel4 = new CarWheel();
		wheel4.translate(-width/2, -(height/2 + 5));
		
		axle1 = new CarAxle();
		axle1.translate(width/2, 0);
		axle2 = new CarAxle();
		axle2.translate(-width/2, 0);
		//translate(100, 100);//spawn the car at this location, relative to the origin (0,0)
	}


	public void setGetFuelSound(String filename)
	{
		String soundDir = "." + File.separator + "sounds" + File.separator;
		//filenames of sounds
		//filename = "test.wav";
		//String filepath = soundDir + filename;
		//Sound test = new Sound(filepath);
		getfuel = new Sound(filename);
	}
	
	public Sound getGetFuelSound()
	{
		return getfuel;
	}
	
	public void setHitNPC(String filename)
	{
		//String soundDir = "." + File.separator + "sounds" + File.separator;
		//filenames of sounds
		//filename = "test.wav";
		//String filepath = soundDir + filename;
		//Sound test = new Sound(filepath);
		hitNPC = new Sound(filename);
	}
	
	public Sound getHitNPC()
	{
		return hitNPC;
	}
	
	public void setLoseLife(String filename)
	{
		//String soundDir = "." + File.separator + "sounds" + File.separator;
		//filenames of sounds
		//filename = "test.wav";
		//String filepath = soundDir + filename;
		//Sound test = new Sound(filepath);
		loselife = new Sound(filename);
	}
	
	public Sound getLoseLife()
	{
		return loselife;
	}

	//no need to make methods to play or stop, as I can get the Sound and then just play those.

	public String toString()
	{
		return "Car: loc = "   + " headtopylno = " + curpylon + " color =  " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " heading = " + heading + " speed = " + speed + " width = " + width + " length = " + height + " maxSpeed = " + maxspeed + " steeringDirection = " + steeringdirection + " fuellevel = " + fuellevel + " damage =" + damagelevel;
	}


	public Point2D.Float getLocation()
	{//TODO fix this!!!
		return null;
	}

	public void setLocation(Point2D.Float loc) 
	{
		//location = loc;
	}

	public void move()
	{//this will use the speed and heading(turning) variables.  I wanted to make this private, but...I have to make it public...
		if (beenhit == true)
		{//if the car has been hit recently, add it to this.
			hitdelay++;
		}
		angle = angle + heading;
		//apply movement transformation here

		translate(speed*Math.cos(Math.toRadians(angle)), speed*Math.sin(Math.toRadians(angle)));
		//translate the object based on the angle, not the heading
		rotate(Math.toRadians(heading));
		//Now, notice that rotate isn't necessary:  A3 had no rotations, only translations.
		//Also note that we use heading, not angle:  This is because the car only rotates on heading, but can move based on a "world" angle.
		
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{//I set this as public so any class can mess with the color, whenever.
		this.color = color;
	}

	public int getDamageLevel()
	{
		return damagelevel;
	}

	public int getMaxDmg()
	{//get the car's max damage level
		return maxdamagelevel;
	}

	public void setDamageLevel(int dmg)
	{//for this one, the car will take more or less damage, not have a flat "set" damage.
		damagelevel = dmg;
	}

	public int getHeading()
	{
		return heading;
	}

	public void setHeading(int head)
	{
		heading = head;
	}

	public double getAngle()
	{
		return angle;
	}

	public void setAngle(double angle)
	{
		this.angle = angle;
	}
	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{

		this.speed = speed;
	}

	public int getMaxSpeed()
	{
		return maxspeed;
	}

	public void setMaxSpeed(int maxspeed)
	{
		//System.out.println("Changing max speed");
		if (maxspeed > 1)
		{//if the car can still go after being hit, basically
			this.maxspeed = maxspeed;
		} else
		{
			maxspeed = 1;
		}
		if (speed > maxspeed)
		{//if the car is now going faster than it should
			speed = maxspeed;
		}
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public boolean getTraction()
	{
		return traction;
	}

	public void setTraction(boolean tra)
	{
		traction = tra;
	}

	public int getFuelLevel()
	{
		return fuellevel;
	}

	public void setFuelLevel(int flvl)
	{//I do plus equals so that I can send in a value, such as -1, and that will deduct from the car per turn.
		this.fuellevel = flvl;

	}

	public int getSteeringDirection()
	{
		return steeringdirection;
	}

	public void setSteeringDirection(int sdir) 
	{
		steeringdirection = sdir;
	}

	public int getMaxHeading() 
	{
		return maxheading;
	}

	public void reset()
	{//a nice method to reset all the variables
		steeringdirection = 0;
		damagelevel = 0;
		maxspeed = 10;
		heading = 0;
		angle = 0;
		speed = 0;
		fuellevel = maxfuel;
		setLocation(new Point2D.Float(100,100));
		//location doesn't reset on a pylon, sadly.
	}

	public int getCurPylon()
	{
		return curpylon;
	}

	public void setCurPylon(int c)
	{//this is assuming hte car will go in loops
		if (c <= maxpylon)
		{
			curpylon = c;
		}
		else
		{
			curpylon = 1;
		}
	}

	public int getMaxPylons()
	{
		return maxpylon;
	}


	public void setMaxPylons(int maxp)
	{//set maximum number of pylons
		maxpylon = maxp;
	}



	@Override
	public void draw(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;//we need graphics 2D to do the affine transformations
		AffineTransform saveAT = g2d.getTransform();
		//do this so we can restore the graphic object's transform

		
		g2d.setColor(color);//the g2d's were just g, but since g2d and g are a composite like thing, I can do this.

		
		
		//I'll translate the car at the same rate as if the car was moving from 0,0
		//Again, this moves THE WHOLE SCREEN, WHY DOES IT DO THIS?
		g2d.transform(mytranslate);//this is done last, like a stack
		g2d.transform(myrotate);
		//g2d.transform(myscale);
		
		g2d.fillRect(-getWidth()/2, -getHeight()/2, getWidth(), getHeight());
		
		//here, we draw the components hierarchically for the car parts
		//draw the wheels
		
		wheel1.rotate((steeringdirection - oldsteer)*4);
		wheel1.draw(g2d);
		wheel2.rotate((steeringdirection - oldsteer)*4);
		wheel2.draw(g2d);
		wheel3.rotate((steeringdirection - oldsteer)*4);
		wheel3.draw(g2d);
		wheel4.rotate((steeringdirection - oldsteer)*4);
		wheel4.draw(g2d);
		oldsteer = steeringdirection;
		
		//draw the axles
		axle1.draw(g2d);
		axle2.draw(g2d);

		
		//Ok, so resetting transforms here(using the resetTransform method) prevents the car from going past the max speed distance from the center
		g2d.setTransform(saveAT);//Having this here will solve the issue of the whole map moving with the car.
	}
	
	public void resetTransform()
	{//reset any transformations, TODO does order matter?
		mytranslate.setToIdentity();
		myrotate.setToIdentity();
		myscale.setToIdentity();
	}
	
	public void translate(double dx, double dy)
	{//translates a point by that much, in x y coord
		mytranslate.translate(dx, dy);
	}
	
	public void rotate(double radian)
	{//rotating
		myrotate.rotate(radian);
	}
	
	public void scale(double sx, double sy)
	{//scaling
		myscale.scale(sx, sy);
	}
	



	@Override
	public boolean collidesWith(ICollider otherobject) 
	{

		// TODO Figure out collision with bird.
		//System.out.println("Car checking collision");
		int objshape = otherobject.getShape();
		int objx = otherobject.getX();
		int objy = otherobject.getY();
		int objwid = otherobject.getWidth();
		int objhei = otherobject.getHeight();


		if (beenhit == true)
		{//if the player was hit recently
			//System.out.println("Car was hit recently");
			//hitdelay++;
			if (hitdelay / maxhitdelay == 1)
			{//if the player reached the max hit delay (waited a while)
				//Systaaaem.out.println("resetting delay");
				hitdelay = 0;//reset the counter.
				beenhit = false;//the player can now be hit again
			}
		}


		// since every object is ICollider, I can do this
		if (objshape == 0) 
		{// circle
			//System.out.println("Found circle");
			if (getX() + width < objx || getX() > objx + objwid*2)
			{// if the x distances
				//System.out.println("No horiz overlap");	// don't overlap
				return false;
			} else if (getY() + height < objy || getY() > objy + objhei*2)
			{// if the y distances
				//System.out.println("No vert overlap");	// don't overlap
				return false;
			}
			//System.out.println("	COLLISION DETECTED");
			return true;
		} else if (objshape == 1) // if a square
		{//note that the square returns a centerx and centery value, so those have to be changed
			//System.out.println("Found square");
			if (getX() + width < objx || getX() > objx  + objwid) 
			{// if the x distances
				//System.out.println("No horiz overlap");	// don't overlap
				return false;
			} else if (getY() + height < objy || getY() > objy +  objhei) 
			{// if the y distances
				//System.out.println("No vert overlap");	// don't overlap
				return false;
			}
			//System.out.println("	COLLISION DETECTED");
			return true;
		}
		
		
		return false;

	}



	@Override
	public void handleCollision(ICollider otherobject) 
	{
		exos = exos.getExitOilSlick(null);//do this beforehand, just in case the car exited the oil slick(if it entered, it will find so in collision
		exos.execute();
		inoil = false;
		traction = true;




		// TODO Auto-generated method stub
		//System.out.println("Car Collided with Another Object ");
		if (otherobject instanceof Bird)
		{
			//System.out.println("Car collided with bird");
			if (beenhit == false)
			{
				cwb = cwb.getCollideWithBird(null);//we can make this null,as its assumed the singleton command is already made
				cwb.execute();//do the thing.
				beenhit = true;
			}
			//damagelevel++;
		} else if (otherobject instanceof FuelCan)
		{

			getfuel.play();
			cwfc = cwfc.getCollideWithFuelCan(null);
			cwfc.execute();
			
			
		} else if (otherobject instanceof Pylon)
		{
			//System.out.println("Car collided with pylon");
			cwp = cwp.getCollideWithPylon(null);



			if (curpylon == ((Pylon)otherobject).getSequenceNumber())
			{
				cwp.execute();

			} else
			{
				//System.out.println("INCORRECT PYLON NUMBER.");
			}

		} else if (otherobject instanceof OilSlick)
		{
			//System.out.println("Car collided with oilslick");
			enos = enos.getEnterOilSlick(null);
			enos.execute();
			inoil = true;
			traction = false;
		} else if (otherobject instanceof NPCCar)
		{
			if (beenhit == false)
			{
				//damagelevel++;
				hitNPC.play();
				cwc = cwc.getCollideWithCar(null);
				cwc.execute();
				Random rand = new Random();
				if (rand.nextInt(5) == 0)
				{//one in 5 chance to spawn an oil slick.
					aos = aos.getAddOilSlick(null);
					aos.execute();
				}


				beenhit = true;
			}
		}


	}



	@Override
	public int getShape() 
	{
		return 1;//square
	}


	@Override
	public int getX() 
	{
		// TODO Auto-generated method stub
		//return (int) location.getX() - (width/2);
		return (int) mytranslate.getTranslateX();//hey, lookie here, AT does it for me.  Thanks, AT!
	}



	@Override
	public int getY() 
	{
		// TODO Auto-generated method stub
		//return (int) location.getY() - (height/2);
		return (int) mytranslate.getTranslateY();
	}

	@Override
	public int getHeight() 
	{
		// TODO Auto-generated method stub
		return height;
	}



	public int getMaxFuel() 
	{
		// TODO Auto-generated method stub
		return maxfuel;
	}

	public void setInOil(boolean inoil)
	{
		this.inoil = inoil;
		traction = !inoil;
	}

	public boolean getInOil()
	{
		return inoil;
	}


	public void setSound(boolean sound) 
	{//turns all sounds on or off.
		// TODO Auto-generated method stub
		getfuel.setSound(sound);
		hitNPC.setSound(sound);
		loselife.setSound(sound);
	}


	


}
