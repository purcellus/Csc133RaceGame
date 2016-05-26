package a4.gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;

import a4.collection.GameObjCollection;
import a4.gameInterface.ICarStrategy;
import a4.gameInterface.ICollider;
import a4.gameInterface.ITransformer;
import a4.gameObject.Car;
import a4.gameObject.carparts.CarWheel;
import a4.strategy.DriveStrategy;
import a4.strategy.RamStrategy;

public class NPCCar extends Car implements ICollider, ITransformer
{//similar to a car, but can bee line to wherever.
	//all methods in car also apply here, such as ICollider methods.
	
	private ICarStrategy thisStrat;//the strategy the npccar implements.
	private GameObjCollection gobjcol;//npc knows all about the game object collections
	private double targx = 0;//values to where the car will go
	private double targy = 0;
	private Car thecar;
	private Pylon pylattack;//the pylon the npc is currently attacking, need for stuff
	private double tacklefrom;//a random int , where the npccar turns to attack the car.
	private double dx;
	private double dy;
	private double theta;
	private double oldtheta;//for rotations.
	private AffineTransform mytranslate;
	private AffineTransform myrotate;
	private AffineTransform myscale;
	private CarWheel wheel1;
	private CarWheel wheel2;

	
	public NPCCar( Color c, int maxp, GameObjCollection gobjcol)
	{
		super( c, maxp);//instantiate the same way as a car
		this.gobjcol = gobjcol;
		super.setSpeed(1);//npcs go at this speed
		super.setWidth(super.getHeight());
		tacklefrom = 0;//initially, they all look like one car.  Then, they split.  hehe.
		//  rand.nextDouble()*2 - 1; //use this if you want to immediately change direction
		mytranslate = new AffineTransform();
		myrotate = new AffineTransform();
		myscale = new AffineTransform();
		oldtheta = 0;
		
		wheel1 = new CarWheel(c);
		wheel1.translate(super.getWidth() - 5, 0);
		wheel2 = new CarWheel(c);
		wheel2.translate(-super.getWidth() + 5,0);
	}
	
	public void setPylon(Pylon p)
	{
		pylattack = p;
	}
	
	public double getTargx()
	{
		return targx;
	}
	
	public void setTargx(double targx)
	{
		this.targx = targx;
	}
	
	public double getTargy()
	{
		return targy;
	}
	
	public void setTargy(double targy)
	{
		this.targy = targy;
	}
	
	public double getTackleFrom()
	{
		return tacklefrom;
	}
	
	public void setTackleFrom(double tacklefrom)
	{
		this.tacklefrom = tacklefrom;
	}
	
	public void applyStrat()
	{//do the strategy
		thisStrat.apply();//do the strat.
	}
	
	public void setStrat(ICarStrategy thestrat)
	{//apply the strategy.
		super.setCurPylon(super.getCurPylon() + 1);//every time the strategy switches, the npc increments the pylonnum to go to.
		Random rand = new Random();//change direction to tackle
		tacklefrom = rand.nextDouble()*2 - 1;
		
		thisStrat = thestrat;//switch the strategy, on the fly.
	}
	
	public ICarStrategy getStrat()
	{//so the command knows what command to switch to, based on this one
		return thisStrat;
	}
	
	public void setCar(Car thecar)
	{//gets a car to attack
		this.thecar = thecar;
	}
	
	@Override
	public void draw(Graphics g)
	{
		//super.draw(g);
		//g.setColor(Color.BLACK);
		//System.out.println("Drawing NPC");
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();

		g2d.transform(mytranslate);
		//g2d.transform(myscale);
		g2d.setColor(Color.RED);
		g2d.drawLine(0, 0, (int)targx , (int)targy);
		
		//draw a rectangle at 0,0 for AT
		g2d.transform(myrotate);

		g2d.setColor(super.getColor());
		g2d.drawRect(-super.getWidth()/2, -super.getWidth()/2, super.getWidth(), super.getHeight());
		//g.drawLine(super.getX() + (super.getWidth()/2), super.getY() + (super.getHeight()/2), super.getX() + (super.getWidth()/2), (int)targy);
		//g.drawLine(super.getX() + (super.getWidth()/2), super.getY() + (super.getHeight()/2), (int)targx, super.getY() + (super.getHeight()/2));
		//g.setColor(Color.DARK_GRAY);//Was supposed to show velocity, but it sux.  Might have to Derive, yuck.
		// d(r*cos(theta))d(theta) =  
		//g.drawLine(super.getX() + (super.getWidth()/2), super.getY() + (super.getHeight()/2), (int)(super.getLocation().getX() + (super.getSpeed() * Math.cos(theta))) , (int)(super.getLocation().getY() + (super.getSpeed() * Math.sin(theta))));
		
		//TODO figure out how to pause these things when paused.
		wheel1.draw(g2d);
		wheel1.rotate(30);
		wheel2.draw(g2d);
		wheel2.rotate(30);
		
		g2d.setTransform(saveAT);
	
	}
	
	@Override
	public void translate(double dx, double dy)
	{
		mytranslate.translate(dx, dy);
	}
	
	@Override
	public void rotate(double radian)
	{
		myrotate.rotate(radian);
	}
	
	@Override
	public void scale(double sx, double sy)
	{
		System.out.println("Scaling in NPC");
		myscale.scale(sx, sy);
	}
	
	@Override
	public void resetTransform()
	{
		mytranslate.setToIdentity();
		myrotate.setToIdentity();
		myscale.setToIdentity();
	}
	
	public int getX()
	{
		return (int) mytranslate.getTranslateX();
	}
	
	public int getY()
	{
		return (int) mytranslate.getTranslateY();
	}
	
	public void move()
	{
		//System.out.println("DEFAULT MOVE");
		if (thisStrat instanceof RamStrategy)
		{
			targx = thecar.getX() - getX();
			targy = thecar.getY() - getY();
		} else if (thisStrat instanceof DriveStrategy)
		{
			if (!(pylattack == null))
			{//if the pylon it tries to attack does exist.
				targx = pylattack.getX() - getX();
				targy = pylattack.getY() - getY();
			}
			//if the pylon doesn't exist, it will go to where the "last" pylon was, the last pylon the NPC's focused before being deleted.
		}
		
		/*System.out.println("tarx " + targetx);
		System.out.println("tary " + targety);*/
		
		
		super.setAngle(super.getAngle() + super.getHeading());
		dx = targx;// - super.getX();
		dy = targy;// - super.getY();
		//System.out.println("dx" + dx);
		//System.out.println("dy" + dy);
		
		
		//double oppoveradj = dy/dx;
		//System.out.println("ARCTANGENT THIS " + oppoveradj);
		//Use atan2, somehow standart atan has a problem
		//theta = theta + tacklefrom;//do this to make the NPC move in circles
		theta = Math.atan2(dy, dx) + tacklefrom;// + Math.tan(oppoveradj);
		//System.out.println(theta);
		translate(super.getSpeed()*Math.cos(theta), super.getSpeed()*Math.sin(theta));
		rotate(theta - oldtheta);
		oldtheta  = theta;
	}
	
	/*public void move(double targetx, double targety)
	{//method to force the car to move in a specific location
		targx = targetx;
		targy = targety;
		Random rand = new Random();
		super.setSpeed((int)Math.abs(tacklefrom) + 2);

		/*System.out.println("carlocx " + super.getLocation().getX());
		System.out.println("carlocy " + super.getLocation().getY());
		System.out.println("tarx " + targetx);
		System.out.println("tary " + targety);
		
		
		super.setAngle(super.getAngle() + super.getHeading());
		double dx = targetx - getX();
		double dy = targety - getY();
		//System.out.println("dx" + dx);
		//System.out.println("dy" + dy);
		
		
		//double oppoveradj = dy/dx;
		//System.out.println("ARCTANGENT THIS " + oppoveradj);
		double theta = Math.atan2(dy, dx) + tacklefrom;// + Math.tan(oppoveradj);
		//Use atan2, somehow standard atan has a problem
		
		//System.out.println(theta);
		//System.out.println(Math.cos(theta));
		//System.out.println(Math.sin(theta));
		
	}*/
	
	@Override
	public void handleCollision(ICollider otherobject)
	{
		
	}
}
