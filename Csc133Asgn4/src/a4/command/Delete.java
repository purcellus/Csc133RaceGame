package a4.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameInterface.ISelectable;
import a4.gameObject.Car;
import a4.gameObject.GameObject;
import a4.gameObject.Pylon;

public class Delete extends AbstractAction implements IPlayerCommand
{
	private static Delete thed;
	private GameWorld gw;
	private Car thecar;
	
	private Delete(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static Delete getDelete(GameWorld gw)
	{
		if (thed == null)
		{
			thed = new Delete(gw);
		}
		return thed;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void execute() 
	{
		// TODO Auto-generated method stub
		GameObjCollection gobjcol = gw.getGameObjCollection();
		gobjcol.removeSelected();//method that removes selected objects
		
		Iterator git = gobjcol.iterator();//we have to iterate to find the largest pylon number.
		while (git.hasNext())
		{//look for a car
			GameObject gobj = (GameObject) git.next();
			if (gobj instanceof Car)
			{//TODO this assumes the car is near the beginning of the collection
				thecar = (Car) gobj;
				thecar.setMaxPylons(1);//I reset the max pylon number, to find the new max pylon number
				break;
			}
		}
		
		
		git = gobjcol.iterator();
		while (git.hasNext())
		{//while there are elements, look for a pylon
			GameObject gobj = (GameObject) git.next();
			if (gobj instanceof Pylon)
			{//if it is a pylon, look to see its number.  if the number is greater than the car's max pylon, change it.
				if (((Pylon) gobj).getSequenceNumber() > thecar.getMaxPylons())
				{//if the pylon number is greater than the car's max number
					thecar.setMaxPylons(((Pylon)gobj).getSequenceNumber());
				}
			}
		}
		
		
	}

}
