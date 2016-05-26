package a4.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameObject.Car;
import a4.gameObject.GameObject;




public class EnterOilSlick extends AbstractAction implements IPlayerCommand
{//command design.  sets it so that the car can't change velocity.
	private Car thecar;//target.
	private GameWorld gw;
	private static EnterOilSlick enos;
	
	
	private EnterOilSlick(GameWorld gw)
	{
		this.gw = gw;
	}

	public static EnterOilSlick getEnterOilSlick(GameWorld gw)
	{
		if (enos == null)
		{//singleton
			enos = new EnterOilSlick(gw);
		}
		return enos;
	}
	
	@Override
	public void execute() 
	{
		GameObjCollection gobjcol = gw.getGameObjCollection();
		Iterator git = gobjcol.iterator();
		GameObject gobj;
		while (git.hasNext())
		{
			//System.out.println("Checking car to get out of oil");
			gobj = (GameObject) git.next();
			if (gobj instanceof Car)
			{
				thecar = (Car) gobj;
				break;
			}
		}
		
		thecar.setTraction(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
