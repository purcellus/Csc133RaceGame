package a4.gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import a4.gameInterface.ICollider;
import a4.gameInterface.IDrawable;
import a4.gameInterface.ITransformer;

public class Bird extends MoveableObject implements IDrawable, ICollider, ITransformer
{

	private int heading;
	private int speed;
	private int size;
	private Color bcolor;
	private AffineTransform mtrans, mrot, mscale;
	
	public Bird(int speed)
	{//all birds will be PINK
		Random rand = new Random();
		heading = rand.nextInt(50) - 100;//bird will randomly pick a direction, never to sway.  Might make a circle though...
		size = 15;//constant size
		bcolor = Color.DARK_GRAY;
		this.speed = speed + 1;//speed + 1;//birds must always move.
		mtrans = new AffineTransform();
		mrot = new AffineTransform();
		mscale = new AffineTransform();
	}

	public Point2D.Float getLocation()
	{
		//return bloc;
		return null;
	}
	
	public void setLocation(Point2D.Float p) 
	{
		//bloc = p;
		
	}

	
	public void move() 
	{
		//bloc.setLocation(bloc.getX() + speed*Math.cos(heading) , bloc.getY() + speed*Math.sin(heading));//should be one direction
		translate(speed*Math.cos(heading), speed*Math.sin(heading));
	}

	public Color getColor() 
	{
		return bcolor;
	}

	public void setColor(Color c) 
	{//done because inheritance of abstract method, and I don't want to access color variable
		System.out.println("Cannot change bird's color after instantiation");
	}
	
	public String toString()
	{
		return "Bird: loc = "  + " color: " + bcolor.getRed() + " " + bcolor.getGreen() + " " + bcolor.getBlue();
	}

	@Override
	public void draw(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		g2d.transform(mtrans);
		g2d.transform(mrot);
		g2d.transform(mscale);
		
		g.setColor(bcolor);
		g.drawOval(0, 0, size * 2, size * 2);
		
		g2d.setTransform(saveAT);
	}

	@Override
	public boolean collidesWith(ICollider otherobject) 
	{
		/*int objshape = otherobject.getShape();
		int objx = otherobject.getX();
		int objy = otherobject.getY();
		int objwid = otherobject.getWidth();
		int objhei = otherobject.getHeight();
		
		GameObject gobj = (GameObject) otherobject;
		//since every object is ICollider, I can do this
		double centerx = (bloc.getX() - gobj.getLocation().getX());
		double centery = (bloc.getY() - gobj.getLocation().getY());
		double centerdist = (centerx * centerx) + (centery * centery);
		//D^2 = (x - x1)^2 + (y - y1)^2
		
		//D <= (Radiusobj1 + radobj2)
		if (objshape == 0)
		{//circle
			if (centerdist <= (size + objwid) * (size + objhei))
			{//if the distance between the centers is less than the sum of the radii
				//System.out.println("COLLISION DETECTED");
				return true;
			}
		} else if (objshape == 1)
		{//square
			//TODO check edges of square in using (size + objwid)
			if (centerdist <= (size + objwid) * (size + objhei))
			{//if the distance between the centers is less than the sum of the radii
				//System.out.println("COLLISION DETECTED");
				return true;
			}
		}*/
		return false;
	}

	@Override
	public void handleCollision(ICollider otherobject) 
	{
		//
		if (otherobject instanceof Wall)
		{
			heading = -heading;//flip the heading.
		}
	}

	public int getShape()
	{
		return 0;//is a circle
	}
	
	public int getSize()
	{
		return size;
	}

	@Override
	public int getX() 
	{
		// TODO Auto-generated method stub
		return (int) mtrans.getTranslateX();
	}

	@Override
	public int getY()
	{
		// TODO Auto-generated method stub
		return (int) mtrans.getTranslateY();
	}

	@Override
	public void resetTransform() 
	{
		// TODO Auto-generated method stub
		mtrans.setToIdentity();
		mrot.setToIdentity();
		mscale.setToIdentity();
	}

	@Override
	public void translate(double dx, double dy) 
	{
		mtrans.translate(dx, dy);
	}

	@Override
	public void rotate(double radian) 
	{
		mrot.rotate(radian);
	}

	@Override
	public void scale(double sx, double sy) 
	{
		mscale.scale(sx, sy);
	}

	@Override
	public int getWidth() 
	{
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public int getHeight()
	{
		// TODO Auto-generated method stub
		return size;
	}

	

	
}
