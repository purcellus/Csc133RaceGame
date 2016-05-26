package a4.gameInterface;

import java.awt.geom.Point2D;
import java.util.Observer;

import a4.collection.GameObjCollection;
import a4.gameObject.Bird;
import a4.gameObject.Car;
import a4.gameObject.FuelCan;
import a4.gameObject.GameObject;
import a4.gameObject.NPCCar;
import a4.gameObject.Pylon;
import a4.gameObject.Wall;

public interface IGameWorldProxy 
{//ALL game world proxies must have these methods.
	int getPylNum();
	int getPylToMake();
	GameObjCollection getGameObjCollection();
	int getClock();
	int getPlayerLives();
	int getNumOfNPC();
	boolean getSound();
	Pylon createPylon(int pyln);
	Pylon createPylon(double locx, double locy, int pyln );
	Pylon createFirstPylon();
	FuelCan createFuelCan();
	FuelCan createFuelCan(double locx, double locy, int size);
	Bird createBird();
	Car createPlayerCar();
	NPCCar createNPC();
	NPCCar createNPC(double locx, double locy);
	Wall createWall(boolean iswall, Point2D.Float wallc, int walltype);
	int getNumOfPyl();
	int getClockSpeed(); 
	
	int getMapWidth();
	void setMapWidth(int width);
	int getMapHeight();
	void setMapHeight(int height);
	void setPylNum(int pylnum);
	void setPylToMake(int pylnum);
	void initLayout();
	void setGameObjCollection(GameObjCollection gobjcol);
	void setPlayerLives(int plives);
	void setSound(boolean sound);
	void setNumOfPyl(int nopyl);
	void setClockSpeed(int clockspeed);  
	
	void addObserver(Observer obs);
	void notifyObservers();
	void setClock(int clock);
	
	void addToCol(GameObject gobj);

	
}
