package a4.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameObject.Car;
import a4.gameObject.FuelCan;
import a4.gameObject.GameObject;

public class CollideWithFuelCan extends AbstractAction implements IPlayerCommand
{//command design.  Do this if the car collides with a fuel can:  add fuel based on the fuel's size.
	private Car thecar;//target.
	private FuelCan thecan;//to be added to the car
	private GameWorld gw;
	private static CollideWithFuelCan cwfc;
	private int MAXFUEL = 50;//max fuel a car can take
	
	private CollideWithFuelCan(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static CollideWithFuelCan getCollideWithFuelCan(GameWorld gw)
	{
		if (cwfc == null)
		{//singleton
			cwfc = new CollideWithFuelCan(gw);
		}
		return cwfc;
	}
	
	public void execute()
	{
		GameObjCollection gobjcol = gw.getGameObjCollection();
		Iterator git = gobjcol.iterator();
		GameObject gobj;
		while (git.hasNext())
		{//get a car
			gobj = (GameObject) git.next();
			if (gobj instanceof Car)
			{//If a car has been found.
				thecar = (Car) gobj;
				break;
			}
		}
		MAXFUEL = thecar.getMaxFuel();
		git = gobjcol.iterator();//reset iterator to find fuel cans
		while (git.hasNext())
		{//get a fuelcan
			gobj = (GameObject) git.next();
			if (gobj instanceof FuelCan)
			{
				//System.out.println("Fuel found");
				thecan = (FuelCan) gobj;
				break;
			}
		}
		//System.out.println("adding fuel...");
		int addfuel = thecar.getFuelLevel() + thecan.getFuel();
		if (addfuel <= MAXFUEL)
		{//if the fuel added would be less than the maximum fuel the car can have.
			thecar.setFuelLevel(addfuel);//add fuel to the car's fuel level
		} else
		{
			thecar.setFuelLevel(MAXFUEL);//set the fuel to the maximum, leftover is gone.
		}
		//thecan.handleCollision(thecar);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
	

