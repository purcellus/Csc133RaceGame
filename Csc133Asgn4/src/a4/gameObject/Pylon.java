package a4.gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.Collection;
import java.util.Iterator;

import a4.gameInterface.ICollider;
import a4.gameInterface.IDrawable;
import a4.gameInterface.ISelectable;
import a4.gameInterface.ITransformer;



public class Pylon extends FixedObject implements IDrawable, ICollider, ISelectable, ITransformer
{
	private int radius;
	private int seqnum;//the number that the pylon should be hit(the first pylon is where the car will start)
	//removed location, don't need
	private Color pyloncolor = Color.ORANGE;//the pylon's color is fixed
	private boolean isclicked;//if the object is selected
	private AffineTransform mtrans, mrot, mscale;
	
	public Pylon( Color c, int snum)
	{//constructor
		setSequenceNumber(snum);
		pyloncolor = c;
		radius = 20;
		isclicked = false;
		mtrans = new AffineTransform();
		mrot = new AffineTransform();
		mscale = new AffineTransform();
		
		mscale.scale(1, -1);//now, because this is a circle, I can do this to ensure the string is right side up.
	}
	
	public String toString()
	{
		return "Pylon: loc = " /*no loc*/ + " pylon color =  [" + pyloncolor.getRed() + ", " + pyloncolor.getGreen() + ", " + pyloncolor.getBlue() + "] radius = " + radius + " Seqnum = " + seqnum;//write a string on command prompt for debugging
		
	}
	
	public Point2D.Float getLocation() 
	{
		//return pyloncenter;
		return null;
	}



	public Color getColor() 
	{
		return pyloncolor;
	}
	
	public void setColor(Color c)
	{//this method exists here only because of inheritance of abstraction.
		System.out.println("Cannot change pylon color after instantiated");
	}
	
	public int getSequenceNumber()
	{
		return seqnum;
	}
	
	private void setSequenceNumber(int snum)
	{//only the Pylon class will set the sequence number;  no other class should call this method.
		seqnum = snum;
	}
	
	public int getRadius()
	{
		return radius;
	}
	
	private void setRadius(int rad)
	{//only the Pylon class will set the radius;  no other class should call this method.
		radius = rad;
	}



	@Override
	public void draw(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();

		if (isclicked)
		{//if the object is selected
			g.setColor(Color.CYAN);
		} else
		{//if the object isn't selected
			g.setColor(pyloncolor);
		}
		
		g2d.transform(mtrans);
		g2d.transform(mrot);
		g2d.transform(mscale);
		
		g.fillOval(-radius, -radius, radius*2, radius*2);//OH FUCK CIRCLES DRAW BASED ON TOP LEFT.
		//MAKE SURE TO FILL OVALS TOP LEFT RELATIVE TO THE TRUE CENTER
		//also take into consideration diameter, not radius (top left to bottom right drawn)
		//draw double the size
		//EDIT:  I changed it to 0,0, since we are doing it in terms of AT
		
		g.setColor(Color.BLACK);//since this is just text, I'll have it a constant color.
		//g.drawRect((int)pyloncenter.getX()-radius, (int)pyloncenter.getY()-radius, radius*2, radius*2);
		
		//rotate(Math.toRadians(180));
		//g2d.transform(mrot);//this is for the string
		g2d.drawString("" + seqnum, 0, 0);//I have it at the radius for AT
		
		g2d.setTransform(saveAT);
		//resetTransform();
	}

	@Override
	public boolean collidesWith(ICollider otherobject) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleCollision(ICollider otherobject) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getShape() 
	{//circle
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getX() 
	{
		// TODO Auto-generated method stub
		//return (int) pyloncenter.getX() - radius;
		return (int) mtrans.getTranslateX();
	}

	@Override
	public int getY() 
	{
		// TODO Auto-generated method stub
		//return (int) pyloncenter.getY() - radius;
		return (int) mtrans.getTranslateY();
	}

	@Override
	public int getWidth()
	{
		// TODO Auto-generated method stub
		return radius;
	}

	@Override
	public int getHeight() 
	{
		// TODO Auto-generated method stub
		return radius;
	}

	@Override
	public void setSelected(boolean yesno) 
	{
		// TODO Auto-generated method stub
		isclicked = yesno;
	}

	@Override
	public boolean isSelected() 
	{
		// TODO Auto-generated method stub
		return isclicked;
	}

	@Override
	public boolean contains(Point p) 
	{
		//System.out.println("Testing pylon selection");
		

		
	
		
		/*System.out.println(p.getX());
		System.out.println("pylon " + seqnum + " " + getX());
		System.out.println(p.getY());
		System.out.println("pylon " + seqnum + " " + getY());*/

		double centerx = getX() - p.getX();
		double centery = getY() - p.getY();
		double centerdist = (centerx * centerx) + (centery * centery);//distance between two centers
		//D^2 = (x - x1)^2 + (y - y1)^2
		//System.out.println(centerdist + " <? " + radius * radius);
		
		if ( centerdist <= radius * radius )
		{//if the centerdistance is less than the radius (if the mouse is in the circle
			//System.out.println("   PYLON SELECTED");
			return true;
		}
		//D <= (Radiusobj1 + radobj2), radius in this case is just the pylon, point has no radius
		//System.out.println("Pylon not selected");
		
		return false;
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
		// TODO Auto-generated method stub
		mtrans.translate(dx, dy);
	}

	@Override
	public void rotate(double radian)
	{
		// TODO Auto-generated method stub
		mrot.rotate(radian);
	}

	@Override
	public void scale(double sx, double sy)
	{
		// TODO Auto-generated method stub
		mscale.scale(sx, sy);
	}

	


}
