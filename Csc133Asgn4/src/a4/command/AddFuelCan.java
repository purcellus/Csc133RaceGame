package a4.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameObject.Car;
import a4.gameObject.GameObject;

public class AddFuelCan extends AbstractAction implements IPlayerCommand
{
	private static AddFuelCan afc;
	private GameWorld gw;
	
	private AddFuelCan(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static AddFuelCan getAddFuelCan(GameWorld gw)
	{
		if (afc == null)
		{
			afc = new AddFuelCan(gw);
		}
		return afc;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void execute(double mousex, double mousey, int size)
	{
		gw.addToCol(gw.createFuelCan(mousex, mousey, size));
	}

	@Override
	public void execute()
	{
		gw.addToCol(gw.createFuelCan());

		
	}
}
