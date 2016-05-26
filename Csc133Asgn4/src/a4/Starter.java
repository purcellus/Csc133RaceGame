package a4;

public class Starter 
{//this class will start pretty much everything.
	
	private static Game newgame;
	private static int plives = 3; 
	
	public static void main(String[] args)
	{//the ultimate main.  where we will start our full program.
		newgame = new Game(0, plives);//start with clock, and player lives
		//the Game class will handle all of the busy work
		System.out.println("Exiting Starter");
	}

}
