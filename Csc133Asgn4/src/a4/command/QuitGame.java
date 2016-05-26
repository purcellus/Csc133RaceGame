package a4.command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;


public class QuitGame extends AbstractAction implements IPlayerCommand
{//command design.  quits the game
	//no targets
	private static QuitGame qg;
	
	private QuitGame()
	{
		//nothing
	}
	
	public static QuitGame getQuitGame()
	{
		if (qg == null)
		{//singleton
			qg = new QuitGame();
		}
		return qg;
	}
	
	
	public void execute()
	{
		
		System.out.println("exiting...");
		int result = JOptionPane.showConfirmDialog(null, "Are you Sure You want to exit?", "confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
}
