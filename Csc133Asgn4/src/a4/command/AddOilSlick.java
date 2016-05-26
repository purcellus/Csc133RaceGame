package a4.command;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Random;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameObject.Car;
import a4.gameObject.GameObject;
import a4.gameObject.NPCCar;
import a4.gameObject.OilSlick;

public class AddOilSlick extends AbstractAction implements IPlayerCommand
{//command design.  Add an oil slick to the gameworld.
	private GameWorld gworld;//target
	private static AddOilSlick addos;
	
	private AddOilSlick(GameWorld gworld)
	{
		this.gworld = gworld;
	}
	
	public static AddOilSlick getAddOilSlick(GameWorld gw)
	{
		if (addos == null)
		{//singleton
			addos = new AddOilSlick(gw);
		}
		return addos;
	}
	
	public void execute()
	{
		
		

		GameObjCollection gobjcol = gworld.getGameObjCollection();
		gobjcol.add(createOilSlick());//add oil slick to the game object collection immediately.
		
	}


	public OilSlick createOilSlick()
	{//creates an oil slick
		GameObjCollection gobjcol = gworld.getGameObjCollection();
		Iterator git = gobjcol.iterator();
		Car thecar = new Car(null, 0);
		while (git.hasNext())
		{//while there are elements, look for a player car
			GameObject gobj = (GameObject) git.next();
			if (gobj instanceof Car && !(gobj instanceof NPCCar))
			{//if a player car is found
				thecar = (Car)gobj;
			}
		}
		
		Random rand = new Random();
		//int x = rand.nextInt(500);//x and y values to make a new point
		//int y = rand.nextInt(500);//Don't need these when it generates at the car.
		Point2D.Float loc = new Point2D.Float(thecar.getX(),thecar.getY());//make new point to put into oil slick
		int sx = rand.nextInt(100);//Now have size of oil slick
		int sy = rand.nextInt(100);
		
		OilSlick addol = new OilSlick(sx, sy, Color.GREEN);//make a new oil slick
		addol.translate(thecar.getX() + rand.nextInt(40) - 20, thecar.getY() + rand.nextInt(40) - 20);
		return addol;

	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
