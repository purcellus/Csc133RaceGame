package a4.gameInterface;

import java.awt.Graphics;
import java.awt.Point;

public interface ISelectable
{

	public void setSelected(boolean yesno);//mark an object's selected status
	public boolean isSelected();//see if an object is selected
	public boolean contains(Point p);//see if mouse is in an object
	public void draw(Graphics g);//draw the object that knows about drawing.  Dependent on isSelected.
	
	
	
}
