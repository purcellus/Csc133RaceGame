package a4.gameObject;

import java.awt.Color;
import java.awt.Point;//I might not use this,
import java.awt.geom.Point2D;

public abstract class FixedObject extends GameObject
{
	public abstract Color getColor();//get color of an object
	public abstract void setColor(Color c);//set color of an object(can override code if we don't want to do that)
}
