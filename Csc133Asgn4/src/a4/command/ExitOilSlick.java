package a4.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameObject.Car;
import a4.gameObject.GameObject;




public class ExitOilSlick extends AbstractAction implements IPlayerCommand
{//command design.  sets it so that the car can change velocity.
	private Car thecar;//target.
	private GameWorld gw;
	private static ExitOilSlick exos;
	
	private ExitOilSlick(GameWorld gw)
	{
		this.gw = gw;
	}

	public static ExitOilSlick getExitOilSlick(GameWorld gw)
	{
		if (exos == null)
		{//singleton
			exos = new ExitOilSlick(gw);
		}
		return exos;
	}
	
	@Override
	public void execute() 
	{
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
		
		thecar.setTraction(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
