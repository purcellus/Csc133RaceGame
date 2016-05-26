package a4.strategy;
import java.util.Iterator;
import java.util.Random;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameInterface.ICarStrategy;
import a4.gameObject.Car;
import a4.gameObject.GameObject;
import a4.gameObject.NPCCar;
import a4.gameObject.Pylon;

public class DriveStrategy implements ICarStrategy
{//this is the car's driving strategy:  drive to the next pylon

	private NPCCar npcCar;
	private GameObjCollection gobjcol;//need to get pylon's location
	
	public DriveStrategy(NPCCar thecar, GameObjCollection gobjcol)
	{
		npcCar = thecar;//holds the NPCCar to be able to move
		this.gobjcol = gobjcol;
	}

	public void apply() 
	{
		// TODO Auto-generated method stub
		int npcx = npcCar.getX();
		int npcy = npcCar.getY();

		int pyltogo = npcCar.getCurPylon();//adds to one here, the car "aims" to the new pylon.
		int pylx = 10;//pylon's location
		int pyly = 10;
		
		Iterator git = gobjcol.iterator();
		
		while (git.hasNext())
		{//while there are elements, look for the pylon the npc needs to go to.
			//System.out.println("Looking for pylon");
			GameObject gobj = (GameObject) git.next();
			if (gobj instanceof Pylon )
			{
				//System.out.println("Pylon for npc found, checking sequence number");
				if (((Pylon)gobj).getSequenceNumber() == pyltogo)
				{//if this is the pylon the car should go to
					//System.out.println("Found pylon" + pyltogo + " and number.");
					Pylon pyl = (Pylon)gobj;
					npcCar.setPylon(pyl);//to use for constant translations in Affine transformations
					pylx = pyl.getX();
					pyly = pyl.getY();
					npcCar.setTargx(pylx);
					npcCar.setTargy(pyly);
					Random rand = new Random();//make a new random number
					
					npcCar.setTackleFrom(rand.nextDouble()*2 - 1);//makes a bee line to pylons
					npcCar.setSpeed((int)Math.abs(npcCar.getTackleFrom()) + 2);//set the new speed of the NPC
					break;//get out of the loop, we found our thing.
				}
			}
			
		}
		
	}
}
