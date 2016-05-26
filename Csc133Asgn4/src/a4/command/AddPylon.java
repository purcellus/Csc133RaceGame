package a4.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameObject.Car;
import a4.gameObject.GameObject;

public class AddPylon extends AbstractAction implements IPlayerCommand
{

	private static AddPylon ap;
	private GameWorld gw;
	
	private AddPylon(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static AddPylon getAddPylon(GameWorld gw)
	{
		if (ap == null)
		{
			ap = new AddPylon(gw);
		}
		return ap;
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
		Iterator git = gobjcol.iterator();


		gw.addToCol(gw.createPylon(gw.getNumOfPyl()+1));
		gw.setNumOfPyl(gw.getNumOfPyl() + 1);

		git = gobjcol.iterator();
		while (git.hasNext())
		{
			GameObject gobj = (GameObject) git.next();
			if (gobj instanceof Car)
			{
				System.out.println("Car found in standard execute.  adding num of pylons");

					((Car)gobj).setMaxPylons(gw.getNumOfPyl());//increase max pylon number
			}
		}
	}
	
	
	public void execute(double mousex, double mousey, int pylnum)
	{
		// TODO Auto-generated method stub
		GameObjCollection gobjcol = gw.getGameObjCollection();
		Iterator git = gobjcol.iterator();
		
		
		
		
		gw.addToCol(gw.createPylon(mousex, mousey, pylnum));
		//gw.setNumOfPyl(gw.getNumOfPyl() + 1);
		
		git = gobjcol.iterator();
		while (git.hasNext())
		{
			GameObject gobj = (GameObject) git.next();
			if (gobj instanceof Car)
			{
				System.out.println("Car found.  adding num of pylons");
				if (pylnum > ((Car)gobj).getMaxPylons())
				{//if the pylon we inserted is greater than the maximum number
					((Car)gobj).setMaxPylons(pylnum);//increase max pylon number
				}
			}
		}
	}
}
