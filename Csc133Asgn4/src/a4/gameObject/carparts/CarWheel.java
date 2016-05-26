package a4.gameObject.carparts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import a4.gameInterface.IDrawable;
import a4.gameInterface.ITransformer;

public class CarWheel implements IDrawable, ITransformer
{

	private AffineTransform mtrans;
	private AffineTransform mrot;
	private AffineTransform mscale;
	private int width = 15;
	private int height = 10;
	private Color c;
	private boolean outline;//determines if the "wheels" will be outlined
	
	public CarWheel()
	{
		mtrans = new AffineTransform();
		mrot = new AffineTransform();
		mscale = new AffineTransform();
		c = Color.gray;
		outline = false;
	}
	
	public CarWheel(Color c)
	{
		this.c = c;
		mtrans = new AffineTransform();
		mrot = new AffineTransform();
		mscale = new AffineTransform();
		outline = true;
		width = width*2;
		height = height/2;
	}
	
	@Override
	public void draw(Graphics g)
	{
		// TODO Auto-generated method stub
		//For some reason, these will draw, independent of time.
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		g2d.transform(mtrans);
		g2d.transform(mrot);
		
		g2d.setColor(c);
		if (outline)
		{
			g2d.drawRect(-width/2,-height/2,width,height);
		} else
		{
			g2d.fillRoundRect(-width/2, -height/2, width, height, 2, 2);//the last parst are the arc width, and arc length
		}
		g2d.setTransform(saveAT);
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
	public void rotate(double degree) 
	{
		// TODO Auto-generated method stub
		mrot.rotate(Math.toRadians(degree));
	}
	@Override
	public void scale(double sx, double sy)
	{
		// TODO Auto-generated method stub
		mscale.scale(sx, sy);
	}
}
