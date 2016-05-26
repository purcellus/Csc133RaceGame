package a4.command;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.gameObject.Car;
import a4.gameObject.GameObject;

public class SteerLeft extends AbstractAction implements IPlayerCommand
{//Command design.  Steers left if the car has traction and isn't turned the max to the left

	private Car thecar;//the target
	private int heading = 5;//only used to adjust heading, by 5 degrees.
	private GameWorld gw;
	private static SteerLeft sl;
	
	private SteerLeft(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static SteerLeft getSteerLeft(GameWorld gw)
	{
		if (sl == null)
		{//singleton
			sl = new SteerLeft(gw);
		}
		return sl;
	}
	
	@Override
	public void execute()
	{
		
		
		Iterator it = gw.getGameObjCollection().iterator();
		Car thecar = new Car( Color.BLACK, 0);
		boolean carfound = false;
		while (it.hasNext())
		{
			//System.out.println("Checking for car to accelerate...");
			GameObject test =  (GameObject) it.next();
			//System.out.println(test.toString());
			if (test instanceof Car)
			{//when we find a car.
				//System.out.println("Found a car");
				thecar = (Car) test;
				carfound = true;
				break;
			}
		}
		//System.out.println("turning left...");
		if (heading + thecar.getSteeringDirection() <= thecar.getMaxHeading())
		{//Steers left if the car has traction and isn't turned the max to the left
			//System.out.println("Steering left successful");
			thecar.setSteeringDirection(thecar.getSteeringDirection() + heading);//decrease(go left)
		}
		//System.out.println(thecar.toString());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
