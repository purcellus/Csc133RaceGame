package a4.gameObject;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Collection;

public abstract class GameObject
{//so we can put all objects in one array.  It's also a collection.
	

	public abstract void setColor(Color c);
	abstract Color getColor();

	
}
