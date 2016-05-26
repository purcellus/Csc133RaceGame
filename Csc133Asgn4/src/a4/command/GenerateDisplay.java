package a4.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameInterface.IGameWorldProxy;
import a4.gameObject.Car;
import a4.gameObject.GameObject;
import a4.proxy.ViewGameWorldProxy;

public class GenerateDisplay extends AbstractAction implements IPlayerCommand
{//command design.  Generates info of the game, including player lives, time, etc.
	private Vector <GameObject> gobj;//target
	private int lives;//variables to change target
	private int time;
	private String thetext;
	
	private Car thecar;
	private ViewGameWorldProxy gw;
	private static GenerateDisplay gd;
	
	private GenerateDisplay(ViewGameWorldProxy gw)
	{

		this.gw = gw;
		lives = gw.getPlayerLives();
		
	}
	
	public static GenerateDisplay getGenerateDisplay(IGameWorldProxy gw2)
	{
		if (gd == null)
		{//singleton
			gd = new GenerateDisplay( (ViewGameWorldProxy) gw2);
		}
		return gd;
	}
	
	public void setNewProxy(ViewGameWorldProxy gw)
	{
		this.gw = gw;
	}
	
	public void execute()
	{
		lives= gw.getPlayerLives();
		time = gw.getClock();
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
		//TODO Add Sound.
		thetext = "Lives= " + lives + " Time = " + time + " Pylon no.= " + thecar.getCurPylon() + " Dmg Lvl= " + thecar.getDamageLevel() + " Fuel lvl= " + thecar.getFuelLevel() + " Sound " + gw.getSound();
		//System.out.println(thetext);
		
	}

	public String getText()
	{
		return thetext;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

}
