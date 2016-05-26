package a4.gameObject;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.Collection;
import java.util.Iterator;

public abstract class MoveableObject extends GameObject
{
	public abstract void move();//TODO move the object (bird's heading is always 0) is this setlocation???
	public abstract Color getColor();//get the color of the Object
	public abstract void setColor(Color c);
	

}
