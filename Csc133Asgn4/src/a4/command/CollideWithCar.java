package a4.command;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameObject.Car;
import a4.gameObject.GameObject;
import a4.gameObject.NPCCar;
import a4.gameObject.ShockWave;

public class CollideWithCar extends AbstractAction implements IPlayerCommand
{//command design.  
	private GameWorld gw;//target
	private static CollideWithCar cwc;
	
	private CollideWithCar(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static CollideWithCar getCollideWithCar(GameWorld gw)
	{
		if (cwc == null)
		{//singleton
			cwc = new CollideWithCar(gw);
		}
		return cwc;
	}
	
	public void execute()
	{
		GameObjCollection gobjcol = gw.getGameObjCollection();
		Car thecar = new Car( Color.BLACK, 0);;
		Iterator git = gobjcol.iterator();
		GameObject gobj;
		while (git.hasNext())
		{//look for a car in the list, while the list still has another object.
			gobj = (GameObject) git.next();
			if (gobj instanceof Car && !(gobj instanceof NPCCar))
			{
				//System.out.println("Found a car");
				thecar = (Car) gobj;
				break;
			}
		}
		
		//System.out.println("Car has collided with another, imaginary car.  take a damage.");
		thecar.setDamageLevel(thecar.getDamageLevel() + 2);
		thecar.setMaxSpeed(thecar.getMaxSpeed() - 1);
		//System.out.println("car has been hit " + thecar.getDamageLevel() + " time(s)");

		gobjcol.add(new ShockWave(thecar.getX(), thecar.getY()));
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
}
