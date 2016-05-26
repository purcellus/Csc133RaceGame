package a4.command;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.AbstractAction;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.gameObject.GameObject;

public class ChangeColors extends AbstractAction implements IPlayerCommand
{//command design.  Tries to change object colors.
	
	private GameObjCollection gobj;//target.
	private GameWorld gw;//target
	private static ChangeColors cc;
	
	private ChangeColors(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public static ChangeColors getChangeColors(GameWorld gw)
	{
		if (cc == null)
		{//singleton
			cc = new ChangeColors(gw);
		}
		return cc;
	}

	@Override
	public void execute()
	{//please note that the rancom colorization is not consistent:  NOT ALL FUEL CANS WILL BE THE SAME RANDOM COLOR.
		Random rand = new Random();
		float red = rand.nextFloat();
		float green = rand.nextFloat();
		float blue = rand.nextFloat();
		Color rcolor = new Color(red, green, blue);//make random color
		gobj = gw.getGameObjCollection();
		Iterator gobjit = gobj.iterator();//make iterator
		System.out.println("Changing colors...");
		while (gobjit.hasNext())
		{//Iterator to go through all Game Objects
			System.out.println("Going through game objects via iteration.");
			((GameObject) gobjit.next()).setColor(rcolor);
			red = rand.nextFloat();
			green = rand.nextFloat();
			blue = rand.nextFloat();
			rcolor = new Color(red, green, blue);//make  more random color
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
