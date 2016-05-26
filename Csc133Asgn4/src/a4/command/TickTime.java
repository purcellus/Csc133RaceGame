package a4.command;











import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameInterface.ICollider;
import a4.gameObject.Car;
import a4.gameObject.GameObject;
import a4.gameObject.MoveableObject;
import a4.gameObject.NPCCar;
import a4.gameObject.OilSlick;
import a4.gameObject.Wall;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class TickTime extends AbstractAction implements IPlayerCommand
{//command design.  increments counter and updates variables.
	private int time;
	private GameObjCollection gobjcol = new GameObjCollection();
	private Vector <OilSlick> oslicks = new Vector();
	private int plives;
	private int pseudotime = 0;//pseudotime to swap strategies.
	private GameWorld gw;//the target
	private static TickTime tt;
	private ChangeStrat chstrat;
	private int switchstrat = 500;//the times to switch strat
	
	private TickTime(GameWorld gw)
	{
		this.gw = gw;
		plives = gw.getPlayerLives();
	}

	public static TickTime getTickTime(GameWorld gw)
	{
		if (tt == null)
		{//singleton
			//System.out.println("Making new tick command");
			tt = new TickTime(gw);
		}
		return tt;
	}
	
	
	public void execute()
	{
		gobjcol = gw.getGameObjCollection();
		time = gw.getClock();
		time++;//tick the time
		gw.setClock(time);
		//System.out.println("tick... " + time);//show time
		
		gobjcol.add(oslicks);//add all of the oil slicks
		oslicks.removeAllElements();//reset oil slick preparation, all oil slicks have been inputted
		
		Iterator gobjit = gobjcol.iterator();
		
		
		while (gobjit.hasNext())
		{//update moveable objects' stats, do this so long as there are objects
			
			GameObject checkobject = (GameObject) gobjit.next();
			
			if (checkobject instanceof Wall)
			{//if this is the outer wall
				
			}
			
			if (checkobject instanceof MoveableObject)
			{
				if (checkobject instanceof Car && !(checkobject instanceof NPCCar))
				{//if this is a player car
					//System.out.println("Found car for TickTime");
					//I wanted to make this polymorphic, but I guess I'm handling stuff like damage, so that might not be possible
					if (((Car) checkobject).getDamageLevel() >= ((Car) checkobject).getMaxDmg() || ((Car) checkobject).getFuelLevel() < 0)
					{//if you got hit too many times, or your fuel is too low
						//System.out.println("Your car has been destroyed, or no more fuel.  Game.  Over.");
						JOptionPane.showMessageDialog(null, "You died", null, 0);
						((Car)checkobject).getLoseLife().play();
						plives--;//detract number of lives
						gw.setPlayerLives(plives);
						gobjcol.clear();//reset the game object collection in game world
						gw.setGameObjCollection(gobjcol);
						gw.initLayout();//reset layout.
						gobjcol = gw.getGameObjCollection();//since we resetted the game object collection, we have to point to the new one.
						//gobjit = gobjcol.iterator();//we also have to get our new iterator
						if (plives <= 0)
						{//if the player used up their last life
							JOptionPane.showMessageDialog(null, "GAME OVER.  CLOSING", null, 0);
							//System.out.println("NO more lives.  Exiting...");
							System.exit(0);//quit the game automatically
						}
						((Car) checkobject).reset();//reset the car's variables
						
					} else 
					{
						
						if ( ((Car)checkobject).getTraction())
						{//if the car has traction.
							((Car)checkobject).setHeading(((Car) checkobject).getSteeringDirection());//the car should have private variables to set its location, based on these variables
						}
						int pseudotime = (time % gw.getClockSpeed() + 1)/gw.getClockSpeed();
						((Car) checkobject).setFuelLevel(((Car) checkobject).getFuelLevel() - 1);//always deduct fuel, but sometimes fuel is added
						//Note that time/20, the 20 is clock speed.  Fine, I'll send it here, since I have to
					}
				}
				//System.out.println("Moving Objects...");
				if (checkobject instanceof NPCCar)
				{
					pseudotime++;
					if (pseudotime / (switchstrat * gw.getNumOfNPC()) == 1)
					{//if it's time to swap strategies
						//System.out.println("Switching strats from time ticking");
						chstrat = chstrat.getChangeStrat(gw);
						chstrat.execute();//change the strats
						pseudotime = 0;//reset pseudotime
					}
				} 
									
					((MoveableObject) checkobject).move();//Birds should automatically move, or any moveable object with this method.
			}
		}
		
		//Now for collision.
		//System.out.println("Checking Collisions");
		GameObjCollection gobjcol2 = new GameObjCollection();
		gobjcol2.addAll(gobjcol);
		
		Iterator git1 = gobjcol.iterator();//have two iterators to compare with each other.
		//the other is in the loop
		
		//TODO figure out why only one gobj1 is used
		while (git1.hasNext())
		{//object to compare to 
			ICollider gobj1 = (ICollider) git1.next();

			if (gobj1 instanceof Car)
			{
				((Car) gobj1).setInOil(false);
			}
			//System.out.println("gobj1 ready");
			Iterator iter2 = gobjcol2.iterator();//make sure to put the second iterator here, to restart it.

			while (iter2.hasNext())
			{
				ICollider gobj2 = (ICollider) iter2.next();
				if (gobj1 != gobj2)
				{//if these are not the same objects
					//System.out.println("Gobj1 checking collision with gobj2");
					if (gobj1.collidesWith(gobj2))
					{//if gobj1 collides with gobj2
						//System.out.println("Collision found with " + gobj1.toString());
						gobj1.handleCollision(gobj2);//handle the collision based on the object
					}
				}	
			}
		}
		
		gobjcol.removeShock();//remove shockwaves whose lives are out.
		
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
}
