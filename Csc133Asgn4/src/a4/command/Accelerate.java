package a4.command;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;

import javax.swing.AbstractAction;

import java.util.Iterator;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameObject.Car;
import a4.gameObject.GameObject;

public class Accelerate extends AbstractAction implements IPlayerCommand 
{//command design to accelerate the car

	private int speed = 1;//only used to update the car's speed

	private static Accelerate checka;//for singleton design\
	private GameWorld gw;//target the command works on
	
	private Accelerate(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static Accelerate getAccelerate(GameWorld gw)
	{//singleton design.  this will check if an instance of this class exists.  we only want one, so we'll return one if one exists.
		//System.out.println("GetAccelerate()");
		if (checka == null)
		{
			//System.out.println("making new Accelerate Command");
			checka = new Accelerate(gw);
		}
		return checka;
	}
	
	@Override
	public void execute() 
	{
		// TODO Auto-generated method stub
		Iterator it = gw.getGameObjCollection().iterator();
		Car thecar = new Car( Color.BLACK, 0);
		boolean carfound = false;
		while (it.hasNext())
		{
			//System.out.println("Checking for car to accelerate...");
			GameObject test =  (GameObject) it.next();
			if (test instanceof Car)
			{//when we find a car.
				//System.out.println("Found a car");
				thecar = (Car) test;
				carfound = true;
				break;
			}
		}
		if (!carfound)
		{
			System.out.println("Could not find car.");
		}
			//System.out.println("accelerating...");
			if (thecar.getTraction() && speed + thecar.getSpeed() < thecar.getMaxSpeed())
			{//if the car has traction and wouldn't be moving at its fastest
				//System.out.println("speed successful");
				thecar.setSpeed(thecar.getSpeed() + speed);//increase the speed
			} else 
			{
				//System.out.println("No traction, or at max speed");
			}
		//System.out.println(thecar.toString());
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
	

	
	
	
}
