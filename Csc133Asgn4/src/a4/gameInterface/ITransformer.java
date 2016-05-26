package a4.gameInterface;

import java.awt.geom.AffineTransform;

public interface ITransformer 
{//no, not an autobot, all transformers will have these methods, to use polymorphically.
	void resetTransform();
	void translate(double dx, double dy);
	void rotate(double radian);
	void scale(double sx, double sy);
	
}
