package a4.command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.Game;

public class Pause extends AbstractAction implements IPlayerCommand
{
	private static Pause pas;
	private Game thegame;//unlike the world, this is the target.
	private Pause(Game thegame)
	{
		this.thegame = thegame;
	}
	
	public static Pause getPause(Game thegame)
	{
		if (pas == null)
		{
			pas = new Pause(thegame);
		}
		return pas;
	}
	
	
	@Override
	public void execute() 
	{
		// TODO Auto-generated method stub
		if (thegame.getTheTick().isRunning())
		{	
			thegame.getTheTick().stop();
		} else
		{
			thegame.getTheTick().start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

}
