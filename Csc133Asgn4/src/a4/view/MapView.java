package a4.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import a4.GameWorld;
import a4.collection.GameObjCollection;
import a4.command.ShowMap;
import a4.gameInterface.IDrawable;
import a4.gameInterface.IGameWorldProxy;
import a4.gameInterface.ITransformer;
import a4.gameObject.Car;
import a4.gameObject.Pylon;
import a4.proxy.ViewGameWorldProxy;

public class MapView extends JPanel implements Observer, MouseWheelListener //, MouseMotionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double wintop = 600;//These values just look nice.
	private double winbot = 0;
	private double winleft = 0;
	private double winright = 800;
	
	//private double olddragx;//For mouse listener, and panning the screen
	//private double olddragy;
	
	private ShowMap maps;
	private IGameWorldProxy theworld;
	private JTextArea maptxt;
	private int repaintcount = 0;//number of times the screen repaints
	private Graphics graph;
	private GameObjCollection gobjcol;
	private AffineTransform worldToND, ndToScreen, theVTM, inverseVTM;
	private boolean isPaused;
	
	public MapView(IGameWorldProxy theworld)
	{
		//olddragx = 0;
		//olddragy = 0;
		
		this.theworld = theworld;
		this.addMouseWheelListener(this);// I have to attach a listener to the component.
		//this.addMouseMotionListener(this);//you do need these, assuming you use them
		//maptop = theworld.getMapHeight();
		//mapright = theworld.getMapWidth();
		//System.out.println("Starting mapview");
		//maps = maps.getShowMap(theworld);
		
		//maptxt = new JTextArea();
		//maptxt.setFocusable(false);
		
		//maptxt.append(maps.getMaps());
		//add(maptxt, BorderLayout.CENTER);
		setVisible(true);
		graph = this.getGraphics();

	}

	@Override
	public void update(Observable arg0, Object arg1) 
	{
		// TODO Auto-generated method stub
		
		//maptxt.append(maps.getMaps());//Were used for Assignment two.
		//maptxt.replaceRange(maps.getMaps(), 0, maptxt.getText().length());
		//add(maptxt, BorderLayout.CENTER);
		//maps.execute();
		
		theworld = (IGameWorldProxy) arg0;//Set gameworldproxy as Observable (which is a game proxy)
		repaint();

	}
	
	public void paintComponent(Graphics g)
	{
		
		repaintcount++;
		super.paintComponent(g);//I assume I have to do this.
		//if you don't, you get trippy results
		gobjcol = theworld.getGameObjCollection();
		Iterator git = gobjcol.iterator();
		//Here, I have to do the transformation of the whole map
		Graphics2D g2d = (Graphics2D) g;//I can do this, one to one ratio
		AffineTransform saveAT = g2d.getTransform();

		g2d.drawString("Count " + repaintcount, 200, 200);//We are assuming that this is the halfway point, even if the screen changes size.

		//g2d.translate(0, getHeight());//flip everything in terms of the whole map, everything below this line of code, anyways
		//g2d.scale(1, -1);//flip everything in terms of the individual object
		//Note that these will also flip strings.
		//This will be solved, via the VTM.  So I commented it, just to show how to do it without the VTM.
		

		
	
		
		
		worldToND = buildWorldToNDXform( winright - winleft, wintop - winbot, winleft, winbot);//get the pseudo window size
		
		ndToScreen = buildNDToScreenXform(this.getWidth(), this.getHeight());//get the actual panel's width and height.
		
		theVTM = (AffineTransform) ndToScreen.clone();//copy this bad boy.

		
		theVTM.concatenate(worldToND);

		g2d.transform(theVTM);
		
		try//Hopefully the order in when to invert the VTM doesn't matter, in terms of doing it before or after transformations.
		//of course the order matters for matrix order.
		//Guess what, order of when to put this DOES MATTER. no surprise there.
		{
			inverseVTM = theVTM.createInverse();//this will not affect theVTM.
		} catch (NoninvertibleTransformException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IDrawable drawobj = (IDrawable) new Car(Color.BLACK, 0);//objects to draw,starts with a dummy var.
		while (git.hasNext())
		{//while there are still objects to draw.
				//Something to note:  Things like to draw at the top left corner.
				//To be taken into consideration.
				drawobj = (IDrawable) git.next();
				
				drawobj.draw(g2d);

		}
		
		
		
		g2d.setColor(Color.BLACK);//for text

		//this drawstring might mess up, might not.
		
		g2d.setTransform(saveAT);//DON'T FORGET TO REVERT SOME OF THOSE CHANGES.
		
	
	}
	
	public AffineTransform getInverseVTM()
	{
		return inverseVTM;
	}
	


	
	public AffineTransform buildWorldToNDXform(double winwidth, double winheight, double winleft, double winbot)
	{//What this should do, is create A Normalized Device screen, where all points are between 0,0 and 1,1
		
		AffineTransform theAT = new AffineTransform();
		
		theAT.scale( 1 / winwidth  , 1 / winheight);
		theAT.translate(-winleft, -winbot);//make sure to translate first to 0,0 so that when it scales, it scales relative to that.


		
		return theAT;
	}
	
	public AffineTransform buildNDToScreenXform(double screenwidth, double screenheight)
	{//this will flip the screen properly.
		//this should also resize everything in the panel, should the window size change.
		
		AffineTransform theAT = new AffineTransform();
		
		theAT.translate(0, screenheight);
		theAT.scale(screenwidth, -screenheight);//turn this to 1,-1 for nice effects
		
		
		return theAT;
	}
	
	public void zoomIn()
	{//this will zoom in the screen, based on mouse wheel.  Probably involves scaling.
		//implement scrollwheelListener
		double h = wintop - winbot;
		double w = winright - winleft;
		winleft += w*0.05;
		winright -= w*0.05;
		winbot += h*0.05;
		wintop -= h*0.05;
		
		
		this.repaint();
	}
	
	public void zoomOut()
	{//this will zoom out the screen, based on mouse wheel.  Probably involves scaling.
		//implement scrollwheelListener
		double h = wintop - winbot;
		double w = winright - winleft;
		winleft -= w*0.05;
		winright += w*0.05;
		winbot -= h*0.05;
		wintop += h*0.05;
		
		
		this.repaint();
	}
	
	public void pan(double mousedragx, double mousedragy)
	{//this will translate the screen, based on mouse drags
		winleft += mousedragx;
		winright += mousedragx;
		wintop += mousedragy;
		winbot += mousedragy;
		
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) 
	{
		// TODO Auto-generated method stub
		if (!isPaused)
		{
			int numclicks = arg0.getWheelRotation();//see how manytimes the wheel rotated
			if (numclicks < 0)
			{
				zoomIn();
			} else if (numclicks > 0)
			{
				zoomOut();
			}
			repaint();
		}
	}

	/*@Override
	public void mouseDragged(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		//System.out.println("Mouse was Dragged");
		double dragx = arg0.getX();//This is always positive.  FIX THIS
		double dragy = arg0.getY();//this is relative to the point.
		
		if (!isPaused)
		{
			pan(dragx - olddragx,dragy - olddragy);
			repaint();
		}
		olddragx = dragx;
		olddragy = dragy;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		//System.out.println("Mouse was moved");
	}*/

	public void setPaused(boolean isPaused) 
	{
		// TODO Auto-generated method stub
		this.isPaused= isPaused;
	}


	
}
