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

public class CollideWithPylon extends AbstractAction implements IPlayerCommand
{//command design.  Increment pylon counter if the current pylon was supposed to be hit.
	private int pylnum;//the pylon number that was hit
	private GameWorld gw;
	private Car thecar;
	private static CollideWithPylon cwp;
	
	private CollideWithPylon(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static CollideWithPylon getCollideWithPylon(GameWorld gw)
	{
		if (cwp == null)
		{//singleton
			cwp = new CollideWithPylon(gw);
		}
		return cwp;
	}

	@Override
	public void execute() 
	{
		thecar = new Car( Color.BLACK, 0);
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
		
		//TODO change this.
		pylnum = thecar.getCurPylon();
		if (thecar.getCurPylon() == pylnum)
		{//if this is the pylon the car should hit next
			System.out.println(thecar.getCurPylon() + " has been hit successfully.");
			if (thecar.getCurPylon() < thecar.getMaxPylons())
			{//if the car hasn't passed the last pylon
				thecar.setCurPylon(pylnum + 1);//increment pylon counter
			} else 
			{//if the car has passed the last pylon
				thecar.setCurPylon(1);
			}
			
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
		
}
	
