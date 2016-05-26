package a4.strategy;

import java.util.Iterator;
import java.util.Random;

import a4.collection.GameObjCollection;
import a4.gameInterface.ICarStrategy;
import a4.gameObject.Car;
import a4.gameObject.GameObject;
import a4.gameObject.NPCCar;

public class RamStrategy implements ICarStrategy
{//npc will ram into the car, or go to the car's current location

	private NPCCar npcCar;
	private GameObjCollection gobjcol;//need to get pylon's location

	public RamStrategy(NPCCar thecar, GameObjCollection gobjcol)
	{
		npcCar = thecar;//holds the NPCCar to be able to move
		this.gobjcol = gobjcol;
	}

	public void apply() 
	{
		// TODO Auto-generated method stub
		int carx = npcCar.getX();//car's location
		int cary = npcCar.getY();

		Iterator git = gobjcol.iterator();


		while (git.hasNext())
		{//while there are elements, look for the pylon the npc needs to go to.
			//System.out.println("Looking for pylon");
			GameObject gobj = (GameObject) git.next();
			if (gobj instanceof Car && !(gobj instanceof NPCCar) )
			{
				//System.out.println("Car for npc found");

				npcCar.setCar((Car)gobj);//set the NPC's car
				carx = (((Car)gobj).getX());//since this is a square, add half the width to get the center.
				cary = (((Car)gobj).getY());
				npcCar.setTargx(carx);
				npcCar.setTargy(cary);
				Random rand = new Random();
				npcCar.setTackleFrom(rand.nextDouble()*2 - 1);
				npcCar.setSpeed((int)Math.abs(npcCar.getTackleFrom()) + 2);//set the new speed of the NPC
				break;//get out of the loop, we found our thing.
			}
		}
		
		
		
		
	}
	


}

