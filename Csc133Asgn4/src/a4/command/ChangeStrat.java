package a4.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameInterface.ICarStrategy;
import a4.gameObject.GameObject;
import a4.gameObject.NPCCar;
import a4.strategy.DriveStrategy;
import a4.strategy.RamStrategy;

public class ChangeStrat extends AbstractAction implements IPlayerCommand
{//command design.  change strategies of npc cars
	private GameWorld theworld;
	private GameObjCollection gobjcol;
	private static ChangeStrat cs;
	
	private ChangeStrat(GameWorld theworld)
	{
		this.theworld = theworld;
	}

	public static ChangeStrat getChangeStrat(GameWorld gw)
	{
		if (cs == null)
		{//singleton
			cs = new ChangeStrat(gw);
		}
		return cs;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() 
	{
		// TODO Auto-generated method stub
		gobjcol = theworld.getGameObjCollection();//get the original game object collection
		//System.out.println("Changing npc car strategies");
		GameObjCollection gobjcol1 = new GameObjCollection();
		gobjcol1.addAll(gobjcol);//make a clone, to dereference
		GameObject gobj;
		Iterator git = gobjcol1.iterator();
		while (git.hasNext())
		{
			gobj = (GameObject) git.next();
			if (gobj instanceof NPCCar)
			{//if this is an npc
				//System.out.println("Found an NPCCar");
				NPCCar npc = (NPCCar) gobj;//type cast to save typing words
				if (npc.getStrat() instanceof DriveStrategy)
				{
					//System.out.println("Switching to RAM strat");
					ICarStrategy istrat = (ICarStrategy) new RamStrategy(npc, gobjcol);
					npc.setStrat(istrat);
					npc.applyStrat();
				} else //if (npc.getStrat() instanceof RamStrategy)
				{
					//System.out.println("Switching to DRIVE strat (or no strat found)");
					ICarStrategy istrat = (ICarStrategy) new DriveStrategy(npc, gobjcol);
					npc.setStrat(istrat);
					npc.applyStrat();//gotta actually do the thing
				}
			}
		}
	}
	
	
	
	
}
