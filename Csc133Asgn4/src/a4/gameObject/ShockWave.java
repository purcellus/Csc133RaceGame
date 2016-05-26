package a4.gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Random;

import a4.gameInterface.ICollider;
import a4.gameInterface.IDrawable;
import a4.gameInterface.ITransformer;

public class ShockWave extends MoveableObject implements IDrawable, ITransformer, ICollider
{
	private int life;//the amount of time the ShockWave will live.
	private int oldheading, heading;//the direction the shockwave will move.
	private int speed;//the speed of the shockwave.
	private int maxpoint = 256;//the maximum size of the bezier curve, width and height wise
	private AffineTransform mtrans, mrot, mscale;
	private int maxlevel = 6;//this is based on Log2(screensize)
	private Color color;//the color of the bezier curve.
	private Point bezpoints[] = new Point[4];//four points for the bezier curve.
	//private Point R[] = new Point[4];
	//private Point S[] = new Point[4];
	
	public ShockWave()
	{
		mtrans = new AffineTransform();
		mrot = new AffineTransform();
		mscale = new AffineTransform();
		Random rand = new Random();
		speed = rand.nextInt(2) + 1;
		oldheading = 0;
		heading = rand.nextInt(360);//heading is done in degrees
		int c = 0;
		while (c < bezpoints.length)
		{//counter to randomize each point's location
			//System.out.println("making random bezpoints");
			bezpoints[c] = new Point(rand.nextInt(maxpoint), rand.nextInt(maxpoint));
			c++;
		}
		life = 200;
		color = color.BLACK;
	}
	
	public ShockWave(int transx, int transy)
	{//a constructor that will have positions to translate to
		mtrans = new AffineTransform();
		mrot = new AffineTransform();
		mscale = new AffineTransform();
		Random rand = new Random();
		speed = rand.nextInt(2) + 1;
		oldheading = 0;
		heading = rand.nextInt(360);//heading is done in degrees
		int c = 0;
		while (c < bezpoints.length)
		{//counter to randomize each point's location
			//System.out.println("making random bezpoints");
			bezpoints[c] = new Point(rand.nextInt(maxpoint) - maxpoint/2, rand.nextInt(maxpoint) - maxpoint/2);
			c++;
		}
		translate(transx, transy);
		life = 200;
		color = color.BLACK;

	}
	
	public ShockWave(int transx, int transy, double angle)
	{//maybe have for car boosts
		mtrans = new AffineTransform();
		mrot = new AffineTransform();
		mscale = new AffineTransform();
		Random rand = new Random();
		speed = rand.nextInt(2) + 1;
		oldheading = 0;
		heading = rand.nextInt(360);//heading is done in degrees
		int c = 0;
		while (c < bezpoints.length)
		{//counter to randomize each point's location
			//System.out.println("making random bezpoints");
			bezpoints[c] = new Point(rand.nextInt(maxpoint) - maxpoint/2, rand.nextInt(maxpoint) - maxpoint/2);
			c++;
		}
		translate(transx, transy);
		life = 200;
		maxlevel = 4;//these ones will be small
		color = color.yellow;

	}
	
	
	public int getLife()
	{//how much life the ShockWave has
		return life;
	}

	public boolean removeShock()
	{//removes shockwaves
		return life < 0;//return true if the life is zero or less.  set a flag to be removed.
	}
	
	
	@Override
	public void draw(Graphics g)
	{
		//System.out.println("Drawing Shockwave");
		life--;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(mtrans);
		g2d.transform(mscale);
		g2d.transform(mrot);
		
		//g2d.fillRect(0, 0, 20, 20);
		
		//g2d.setColor(Color.black);
		//Random rand = new Random();//this code is to remake the shockwave constantly.
		/*int c = 0;
		while (c < bezpoints.length)
		{//counter to randomize each point's location
			//System.out.println("making random bezpoints");
			bezpoints[c] = new Point(rand.nextInt(maxpoint) - maxpoint/2, rand.nextInt(maxpoint) - maxpoint/2);
			c++;
		}*/
		
		//this code is to remake only the end of the shockwave
		//bezpoints[3] = new Point(rand.nextInt(maxpoint) - maxpoint/2, rand.nextInt(maxpoint) - maxpoint/2);

		
		//TODO figure out how to draw the convex for the bezier curve
		g2d.setColor(Color.RED);
		g2d.drawLine((int)bezpoints[0].getX(), (int)bezpoints[0].getY(), (int)bezpoints[1].getX(), (int)bezpoints[1].getY());
		g2d.setColor(Color.YELLOW);
		g2d.drawLine((int)bezpoints[1].getX(), (int)bezpoints[1].getY(), (int)bezpoints[2].getX(), (int)bezpoints[2].getY());
		g2d.setColor(Color.GREEN);
		g2d.drawLine((int)bezpoints[2].getX(), (int)bezpoints[2].getY(), (int)bezpoints [3].getX(), (int)bezpoints[3].getY());
		g2d.setColor(Color.BLUE);
		g2d.drawLine((int)bezpoints[3].getX(), (int)bezpoints[3].getY(), (int)bezpoints[0].getX(), (int)bezpoints[3].getY());

		g2d.setColor(Color.BLACK);
		
		//draw the bezier curve recursively
		/*int bc = 0;
		while (bc < bezpoints.length)
		{
			System.out.println(bezpoints[bc]);
		
			bc++;
		}*/
		drawBezierCurve(bezpoints,1,g2d);
		
		
		g2d.setTransform(saveAT);
	}
	
	public void drawBezierCurve(Point ControlPointVector[], int level, Graphics2D g2d)
	{//make sure the [] is placed AFTER the variable name, not after the variable type	
		//TODO figure out how to make multiple lines for the DrawLine
		
		Point R[] = new Point[4];
		Point S[] = new Point[4];//to be used for the bezier halves
		
		int pointc = 0;
		while (pointc < 4)
		{//initialize each individual point
			R[pointc] = new Point(0,0);
			S[pointc] = new Point(0,0);
			pointc++;
		}
		
		if (straightEnough(ControlPointVector) || level >= maxlevel)
		{//if the line looks straight enough
			System.out.println("Got to Base Case");
			g2d.drawLine((int)ControlPointVector[0].getX(), (int)ControlPointVector[0].getY(), (int)ControlPointVector[3].getX(), (int)ControlPointVector[3].getY());//draw a line to these points.
		} else
		{
			System.out.println("Recursion time " + level);
			subdivideCurve(ControlPointVector, R, S);
		
			
			
			System.out.println(R[0].toString());
			System.out.println(S[0].toString());
			//TODO figure out how to output R and S from subdivideCurve.
			drawBezierCurve (R, level+1, g2d);//draw the left half
			drawBezierCurve (S, level+1, g2d);//draw the right half
			
			 
		}
		
		
		
	}
	
	public void subdivideCurve(Point Q[], Point R[], Point S[])
	{//this will subdivide the bezier curve in half, to be able to draw it recursively.

		R[0] = Q[0];
		R[1].setLocation( (Q[0].getX() + Q[1].getX()) / 2.0  ,  (Q[0].getY() + Q[1].getY()) / 2.0);//I have to do this, since + isn't overloaded to polymorphically deal with points
		R[2].setLocation( (R[1].getX()/2.0)   + ((Q[1].getX() + Q[2].getX())  /4) , (R[1].getY()/2.0) + ((Q[1].getY()   + Q[2].getY())  /4));
		S[3] = Q[3];
		S[2].setLocation( (Q[2].getX() + Q[3].getX()) / 2.0  ,  (Q[2].getY() + Q[3].getY()) / 2.0);//I have to do this, since + isn't overloaded to polymorphically deal with points
		S[1].setLocation( (S[2].getX()/2.0)   + ((Q[1].getX() + Q[2].getX())  /4) , (S[2].getY()/2.0)   + ((Q[1].getY() + Q[2].getY())  /4));
		R[3].setLocation( (R[2].getX() + S[1].getX()) /2.0    ,  (R[2].getY() + S[1].getY()) /2.0);
		S[0] = R[3];
	
	}
	
	
	
	public boolean straightEnough(Point Q[])
	{//sees if part of a bezier curve is straight enough, based on the original four points.
		double d1 = lengthOf(Q[0],Q[1]) + lengthOf(Q[1],Q[2]) + lengthOf(Q[2],Q[3]);
		
		double d2 = lengthOf(Q[0],Q[3]);
		
		if (Math.abs(d1 - d2) < 0.001)
		{//if the distance difference is close enough
			return true;
		} else
		{
			return false;
		}
		
		//return false;
	}
	
	public double lengthOf(Point p1, Point p2)
	{
		return ((p2.getY() - p1.getY()) / (p2.getX() - p1.getX()));
		//return 1;
	}

	@Override
	public void resetTransform()
	{
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
	public void move()
	{
		translate(speed*Math.cos(heading), speed*Math.sin(heading));
		
	}

	@Override
	public Color getColor() 
	{
		return null;
	}

	@Override
	public void setColor(Color c) 
	{
		
	}

	
	//these can be ignored
	@Override
	public boolean collidesWith(ICollider otherobject) 
	{
		return false;
	}

	@Override
	public void handleCollision(ICollider otherobject) 
	{
		
	}

	@Override
	public int getShape()
	{
		return 0;
	}

	@Override
	public int getX() 
	{
		return 0;
	}

	@Override
	public int getY()
	{
		return 0;
	}

	@Override
	public int getWidth() 
	{
		return 0;
	}

	@Override
	public int getHeight() 
	{
		return 0;
	}

}
