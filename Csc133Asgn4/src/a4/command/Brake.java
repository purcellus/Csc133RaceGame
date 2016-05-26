package a4.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameObject.Car;
import a4.gameObject.GameObject;

public class Brake extends AbstractAction implements IPlayerCommand
{//Command design.  decrease the player's speed, unless they are zero or don't have traction
	private Car thecar;//the target
	private int speed = -1;//only used to update the car's speed
	private static Brake br;
	private GameWorld gw;
	
	private Brake(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static Brake getBrake(GameWorld gw)
	{//singleton
		if (br == null)
		{//singleton
			//System.out.println("Making New Brake Command");
			br = new Brake(gw);
		}
		return br;
	}
	public void execute()
	{//this will slow down the player's car, down to 0.
		GameObjCollection gobjcol = gw.getGameObjCollection();
		Iterator git = gobjcol.iterator();
		GameObject gobj;
		while (git.hasNext())
		{
			gobj = (GameObject) git.next();
			if (gobj instanceof Car)
			{
				thecar = (Car) gobj;
				break;
			}
			
		}
		
		
		if (thecar.getTraction() && speed + thecar.getSpeed() >= 0)
		{//if the car can be controlled and isn't at the lowest speed possible.
			//System.out.println("brake successful");
			thecar.setSpeed(thecar.getSpeed() + speed);//decrease the speed
		} else
		{
			System.out.println("No traction, or min speed");
		}
		//System.out.println(thecar.toString());

	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	

}
