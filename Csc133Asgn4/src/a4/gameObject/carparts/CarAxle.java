package a4.gameObject.carparts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import a4.gameInterface.IDrawable;
import a4.gameInterface.ITransformer;

public class CarAxle implements IDrawable, ITransformer
{

	private AffineTransform mtrans;
	private AffineTransform mrot;
	private AffineTransform mscale;
	private int width = 5;
	private int height = 36;
	
	public CarAxle()
	{
		mtrans = new AffineTransform();
		mrot = new AffineTransform();
		mscale = new AffineTransform();
		
	}
	@Override
	public void draw(Graphics g) 
	{
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();//NOT new AffineTransform();
		
		
		g2d.transform(mtrans);
		g2d.transform(mrot);
	
		g2d.setColor(Color.gray);
		g2d.fillRect(-width/2, -height/2, width, height);
		
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
