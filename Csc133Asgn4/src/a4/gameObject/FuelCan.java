package a4.gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import a4.gameInterface.ICollider;
import a4.gameInterface.IDrawable;
import a4.gameInterface.ISelectable;
import a4.gameInterface.ITransformer;

public class FuelCan extends FixedObject implements IDrawable, ICollider, ISelectable, ITransformer
{
	private int size;
	private int fuel;
	private Color fcolor;//fuel color
	private int width = 30;
	private int height = 30;
	private boolean isclicked = false;
	private AffineTransform mtrans, mrot, mscale;
	
	public FuelCan( Color c, int size)
	{
		setSize(size);
		setColor(c);
		setFuel(size);
		mtrans = new AffineTransform();
		mrot = new AffineTransform();
		mscale = new AffineTransform();
		mscale.scale(1,-1);
	}
	


	public String toString()
	{
		
		return "Fuelcan: loc = " + " Size =  " + size + "Color = " + fcolor.getRed() + " " + fcolor.getGreen() + " " + fcolor.getBlue() + " Size = " + size;
	}
	
	
	
	public Point2D.Float getLocation() 
	{
		//return fcloc;
		return null;
	}

	public void setLocation(Point2D.Float p) 
	{
		//fcloc = p;
	}

	public Color getColor() 
	{
		return null;
	}
	
	public void setColor(Color c) 
	{
		// TODO Auto-generated method stub
		fcolor = c;
	}
	
	public int getSize()
	{
		return size;
	}
	
	private void setSize(int size)
	{//I want only the fuel can to set the size, nothing else.
		this.size = size;
	}
	
	public int getFuel()
	{
		return fuel;
	}
	
	private void setFuel(int size)
	{//the Fuel Can's Fuel is directly related to the size of the Fuel Can.
		fuel = size;
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
		{//default color.
			g.setColor(fcolor);
		}
		
		g2d.transform(mtrans);
		g2d.transform(mrot);
		g2d.transform(mscale);
		
		g2d.fillRect(-width/2, -height/2, width, height);
		g2d.setColor(Color.RED);
		g2d.drawString("" + size, 0, 0 );
		
		g2d.setTransform(saveAT);
	}

	@Override
	public boolean collidesWith(ICollider otherobject) 
	{
		// TODO Auto-generated method stub

		int objshape = otherobject.getShape();
		int objx = otherobject.getX();
		int objy = otherobject.getY();
		int objwid = otherobject.getWidth();
		int objhei = otherobject.getHeight();

		// since every object is ICollider, I can do this
		if (objshape == 0) 
		{// circle
			//System.out.println("Found circle");
			if (getX() + width < objx || getX() > objx + objwid*2)
			{// if the x distances
				//System.out.println("No horiz overlap");	// don't overlap
				return false;
			} else if (getY() + height < objy || getY() > objy + objhei*2)
			{// if the y distances
				//System.out.println("No vert overlap");	// don't overlap
				return false;
			}
			//System.out.println("	COLLISION DETECTED");
			return true;
		} else if (objshape == 1) // if a square
		{//note that the square returns a centerx and centery value, so those have to be changed
			//System.out.println("Found square");
			if (getX() + width < objx || getX() > objx  + objwid) 
			{// if the x distances
				//System.out.println("No horiz overlap");	// don't overlap
				return false;
			} else if (getY() + height < objy || getY() > objy +  objhei) 
			{// if the y distances
				//System.out.println("No vert overlap");	// don't overlap
				return false;
			}
			//System.out.println("	COLLISION DETECTED");
			return true;
		}
		return false;
	}

	@Override
	public void handleCollision(ICollider otherobject) 
	{
		// TODO Auto-generated method stub
		if (otherobject instanceof Car)
		{//if the fuel can was hit by ANY CAR, making NPC's mean if they happen to run over it.
			//basically, I'm going to put the fuel can in a new location
			//System.out.println("Hit by Car.  Changing Location.");
			Random rand = new Random();
			//setLocation(new Point2D.Float(rand.nextInt(500),rand.nextInt(500)));
			//now, use translations, rather than that stuff
			translate(-getX(), -getY());//reset the location of the Fuel can
			translate(rand.nextInt(500), rand.nextInt(500));//based on origin
			//((Car)otherobject).setFuelLevel(((Car)otherobject).getFuelLevel() + size);
		}
	}

	@Override
	public int getShape() 
	{
		return 1;//square
	}

	@Override
	public int getX() 
	{
		// TODO Auto-generated method stub
		//return (int) fcloc.getX() - (width/2);
		return (int) mtrans.getTranslateX() - width/2;
	}

	@Override
	public int getY() 
	{
		// TODO Auto-generated method stub
		//return (int) fcloc.getY() - (height/2);
		return (int) mtrans.getTranslateY() - height/2;
	}

	@Override
	public int getWidth() 
	{
		// TODO Auto-generated method stub
		return width;//size that its drawn on the screen
	}

	@Override
	public int getHeight() 
	{
		// TODO Auto-generated method stub
		return height;
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
		// TODO Auto-generated method 
		//System.out.println("X values: " + p.getX() + " in? " + (int)getX() + " " + (int)(getX() + width));
		if (p.getX() < (double) getX() + width && p.getX() > getX())
		{//if the point is between the horizontal edges
			
			//System.out.println("Y values: "  + p.getY() + " in? " + (int)getY() + " " + (int)(getY() + height));
			if (p.getY() < getY() + height && p.getY() > getY())
			{//if the point is between the horizontal edges
				//System.out.println("    OBJECT CONTAINS POINT");
				return true;
			}
			//System.out.println("No Vert Match");
		} else
		{
			//System.out.println("No Horiz Match");
			return false;
		}
		return false;
	}



	@Override
	public void resetTransform() {
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
