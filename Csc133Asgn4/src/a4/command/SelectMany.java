package a4.command;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameInterface.ISelectable;

public class SelectMany extends AbstractAction implements IPlayerCommand
{
	private static SelectMany selm;
	private GameWorld gw;
	private boolean clickedone = false;
	
	private SelectMany(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static SelectMany getSelectMany(GameWorld gw)
	{
		if (selm == null)
		{
			selm = new SelectMany(gw);
		}
		return selm;
	}
	
	
	public void execute(Point2D mousep)
	{
		double mx = mousep.getX();
		double my = mousep.getY();
		Point p = new Point();
		p.setLocation(mx, my);//"set" the location of the mouse point in terms of a point.
		GameObjCollection gobjcol = gw.getGameObjCollection();
		Iterator git = gobjcol.iterator();
		git = gobjcol.iterator();//reset iterator after finished

		while (git.hasNext())
		{//while there are selectable objects
			Object isel = git.next();
			if (isel instanceof ISelectable)
			{
				//System.out.println("Found a selectable to check");
				if (((ISelectable) isel).contains(p))//to be able to send a point, since point2d and point are not type castable
				{//if the mouse is in the object
					//System.out.println("Mouse in selectable");
					((ISelectable) isel).setSelected(true);
					clickedone = true;
				}
			}
		}
		//System.out.println(clickedone);

		if (clickedone == false)
		{//if no clickables were clicked
			git = gobjcol.iterator();

			while(git.hasNext())
			{//while there are ISelectable, reset their selection
				Object isel = git.next();
				if (isel instanceof ISelectable)
				{//reset selection
					((ISelectable) isel).setSelected(false);
				}
			}
		}

		clickedone = false;//reset flag for if a new thing was clicked
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
		
	}

}
