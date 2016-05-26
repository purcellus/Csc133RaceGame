package a4.gameInterface;

public interface ICollider 
{//for collision detection

	public boolean collidesWith(ICollider otherobject);
	public void handleCollision(ICollider otherobject);
	public int getShape();//gets the shape of an object, 0 = circle, 1 = square
	public int getX();//all objects have a 'center', or rather, a corner location.
	public int getY();//For circles, this is their top left corner subtracted by their their radius
					  //Same thing for squares.
	//note that getX() and getY() return the topleft corner, NOT THE CENTER.  CENTER IS SOMEWHAT USELESS FOR COLLISIONS IN SQUARES
	//public int getCenterX();//for circles, if I decide to collide with circles
	//public int getCenterY();
	public int getWidth();//all objects also have a distance from corner to center, or vice versa.
	public int getHeight();
}
