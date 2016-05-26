package a4.command;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.gameObject.Car;
import a4.gameObject.GameObject;

public class SteerRight extends AbstractAction implements IPlayerCommand
{//command design.  Refer to SteerLeft for similar reasoning
	private Car thecar;
	private int heading = -5;
	private GameWorld gw;
	private static SteerRight sr;
	
	private SteerRight(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static SteerRight getSteerRight(GameWorld gw)
	{
		if (sr == null)
		{//singleton
			sr = new SteerRight(gw);
		}
		return sr;
	}
	
	@Override
	public void execute() 
	{
		//System.out.println("turning right...");
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
		if (heading + thecar.getSteeringDirection() >= -thecar.getMaxHeading())
		{//Steers left if the car has traction and isn't turned the max to the left
			//System.out.println("Steering right successful");
			thecar.setSteeringDirection(thecar.getSteeringDirection() + heading);//increase(go right)
		} else 
		{
			System.out.println("max right turning");
		}
		//System.out.println(thecar.toString());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
