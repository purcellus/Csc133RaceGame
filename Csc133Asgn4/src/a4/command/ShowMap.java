package a4.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameInterface.IGameWorldProxy;
import a4.gameObject.GameObject;

public class ShowMap extends AbstractAction implements IPlayerCommand
{//command design.  show locations of objects
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameObjCollection  gobj;//target
	private String themap;//for giving to mapview
	private Iterator gobjit;
	private IGameWorldProxy gw;
	private static ShowMap sm;
	
	public ShowMap(IGameWorldProxy theworld)
	{
		this.gw = theworld;
		this.gobj = theworld.getGameObjCollection();
		gobjit = gobj.iterator();
	}
	
	public static ShowMap getShowMap(IGameWorldProxy theworld)
	{
		if (sm == null)
		{//singleton
			sm = new ShowMap(theworld);
		}
		return sm;
	}
	
	@Override
	public void execute() 
	{
		System.out.println("Showing object locations from ShowMap:");
		GameObject theobj;
		gobjit = gobj.iterator();//to reset the counter, so to speak.
		themap = "";//reset string.
		
		while(gobjit.hasNext())
		{//show all fixed objects' locations, using... Polymorphism.
			theobj = (GameObject) gobjit.next();
			themap += "\n" + theobj.toString();
		}
		System.out.println(themap);//show the string representation of the object, will change later, I hope

	}

	public String getMaps()
	{
		return themap;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

}
