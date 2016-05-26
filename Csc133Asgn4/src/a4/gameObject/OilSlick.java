package a4.gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;

import a4.gameInterface.ICollider;
import a4.gameInterface.IDrawable;
import a4.gameInterface.ITransformer;

public class OilSlick extends FixedObject implements IDrawable, ICollider, ITransformer
{
	private int height;
	private int width;//size of the oil slick
	private int area;//width*length
	private Color oscolor;
	private AffineTransform mtrans, mrot, mscale;
	
	public OilSlick(int width, int length, Color c)
	{//constructor
		this.height = width;
		this.width = length;
		setArea(width, length);
		setColor(c);
		mtrans = new AffineTransform();
		mrot = new AffineTransform();
		mscale = new AffineTransform();

	}
	
	public String toString()
	{
		return "OilSlick: loc = "  + " color " + oscolor.getRed() + " " + oscolor.getGreen() + " " + oscolor.getBlue() + " width = " + height + " length = " + width;
	}
	
	public Point2D.Float getLocation() 
	{
		//return osloc;
		return null;
	}

	protected void setLocation(Point2D.Float p) 
	{
		//osloc = p;
	}

	public Color getColor() 
	{
		return oscolor;
	}
	
	public void setColor(Color c) 
	{
		oscolor = c;
	}

	public int getArea()
	{
		return area;
	}
	
	private void setArea(int wid, int len)
	{
		area = len*wid;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	private void setWidth(int wid)
	{
		width = wid;
	}
	

	@Override
	public void draw(Graphics g)
	{//make sure to draw a little more upper left, to put the center in the proper place.
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(mtrans);
		g2d.transform(mrot);
		g2d.transform(mscale);
		
		
		g.setColor(oscolor);
		g.fillOval(0 , 0, width, height);
		
		g2d.setTransform(saveAT);
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
	{
		// TODO Auto-generated method stub
		return 0;//circle
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
	public int getHeight() 
	{
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public void resetTransform() {
		// TODO Auto-generated method stub
		
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

	
	
}
