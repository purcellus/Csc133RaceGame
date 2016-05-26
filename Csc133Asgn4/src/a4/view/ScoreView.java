package a4.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import a4.GameWorld;
import a4.command.GenerateDisplay;
import a4.gameInterface.IGameWorldProxy;
import a4.proxy.ViewGameWorldProxy;

public class ScoreView extends JPanel implements Observer
{
	private IGameWorldProxy gw;
	private GenerateDisplay gdisp;
	private JLabel thelabel;
	private String output;
	
	public ScoreView(ViewGameWorldProxy gw)
	{
		this.gw = (ViewGameWorldProxy) gw;
		System.out.println("Starting ScoreView...");
		gdisp = gdisp.getGenerateDisplay(gw);
		thelabel = new JLabel("test");
		add(thelabel);
	}

	@Override
	public void update(Observable arg0, Object arg1) 
	{
		// TODO Auto-generated method stub
		gw = (ViewGameWorldProxy) arg0;
		gdisp.setNewProxy((ViewGameWorldProxy) gw);
		
		gdisp.execute();
		output = gdisp.getText();
		thelabel.setText(output);//output text in GUI, score view's panel (should be north)
	}
}
