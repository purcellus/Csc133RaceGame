package a4.gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import a4.GameWorld;
import a4.gameInterface.ICollider;
import a4.gameInterface.IDrawable;

public class Wall extends FixedObject implements ICollider, IDrawable
{//Basically, a wall.  Will resize on the borders, but might also be in the game

	private int width;//four of the walls will be borders, being outside the game map.
	private int height;
	private Point2D.Float center;
	private Color thecolor;
	private GameWorld gw;//holds gameworld, to get map width and map height
	private boolean border;
	private int walltype;//one thru four are border walls, others don't matter.
	
	
	
	public Wall(boolean border, Point2D.Float wcenter, int walltype)
	{
		System.out.println("Making wall");
		this.border = border;
		this.center = wcenter;
		this.walltype = walltype;
		if (walltype == 1 || walltype == 3)
		{
			width = 1000;//set length of wall to map size
			height = 10;
		} else if (walltype == 2 || walltype == 4)
		{
			height = 800;//set height to map size
			width = 10;
		} else
		{//default walls (Like Blocks)
			width = 50;
			height = 50;
		}
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

	public boolean isBorder()
	{//if this is a border wall
		return border;
	}
	
	@Override
	public int getShape()
	{
		// TODO Auto-generated method stub
		return 1;//is a square
	}

	@Override
	public int getX()
	{
		// TODO Auto-generated method stub
		return (int) center.getX() - width;
	}

	@Override
	public int getY()
	{
		// TODO Auto-generated method stub
		return (int) center.getY() - height;
	}

	@Override
	public int getWidth() 
	{
		// TODO Auto-generated method stub
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}
	@Override
	public int getHeight() 
	{
		// TODO Auto-generated method stub
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}



	@Override
	public Color getColor() 
	{
		// TODO Auto-generated method stub
		return thecolor;
	}

	@Override
	public void setColor(Color c) 
	{
		// TODO Auto-generated method stub
		thecolor = c;
	}


	@Override
	public void draw(Graphics g) 
	{
		// TODO Auto-generated method stub
		g.setColor(Color.GRAY);
		g.fillRect(getX(), getY(), width, height);
		g.setColor(Color.BLACK);
		g.drawString("Wall " + walltype, (int)center.getX(), (int)center.getY());
	}
	

}
