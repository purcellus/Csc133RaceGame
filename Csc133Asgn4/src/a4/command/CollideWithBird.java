package a4.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameObject.Car;
import a4.gameObject.GameObject;
import a4.gameObject.ShockWave;

public class CollideWithBird extends AbstractAction implements IPlayerCommand
{//command design.  damage car when hit by a bird.  Similar to being hit by another car.
	private Car thecar;//target to receive damage.  
	private static CollideWithBird cwb;
	private GameWorld gw;
	
	private CollideWithBird(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static CollideWithBird getCollideWithBird(GameWorld gw)
	{
		if (cwb == null)
		{//singleton
			cwb = new CollideWithBird(gw);
		}
		return cwb;
	}
	
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
		
		//System.out.println("Car has collided with a bird.  take a damage.");
		//System.out.println("car has been hit " + thecar.getDamageLevel() + " time(s)");
		thecar.setDamageLevel(thecar.getDamageLevel() + 1);
		
		gw.addToCol(new ShockWave(thecar.getX(), thecar.getY()));
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
