package a4.command;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameInterface.ISelectable;

public class Select extends AbstractAction implements IPlayerCommand
{
	
	private GameWorld g12world;
	private static Select sel;
	
	private Select(GameWorld gw)
	{
		g12world = gw;
	}
	
	public static Select getSelect(GameWorld gw)
	{
		if (sel == null)
		{
			sel = new Select(gw);
		}
		return sel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	public void execute(Point mousep)
	{
		GameObjCollection gobjcol = g12world.getGameObjCollection();
		Iterator git = gobjcol.iterator();

		while(git.hasNext())
		{//while there are ISelectable, reset their selection
			Object isel = git.next();
			if (isel instanceof ISelectable)
			{//reset selection
				((ISelectable) isel).setSelected(false);
			}
		}


		git = gobjcol.iterator();//reset iterator after finished
		while (git.hasNext())
		{//while there are selectable objects
			Object isel = git.next();
			if (isel instanceof ISelectable)
			{
				//System.out.println("Found a selectable to check");
				

				if (((ISelectable) isel).contains(mousep))
				{//if the mouse is in the object
					//System.out.println("Mouse in selectable");
					((ISelectable) isel).setSelected(true);
					break;//make sure ONLY ONE object is selected
				}
			}
		}
	}
	
	
	@Override
	public void execute() 
	{
		// TODO Auto-generated method stub
		
		
		
	}//I have this as a command, well not really using this one ):

	
}
