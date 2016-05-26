package a4.proxy;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;

import a4.collection.GameObjCollection;
import a4.gameInterface.IGameWorldProxy;
import a4.gameObject.Bird;
import a4.gameObject.Car;
import a4.gameObject.FixedObject;
import a4.gameObject.FuelCan;
import a4.gameObject.GameObject;
import a4.gameObject.NPCCar;
import a4.gameObject.Pylon;
import a4.gameObject.Wall;

public class ViewGameWorldProxy extends Observable implements IGameWorldProxy
{//a game world proxy for view classes and methods.
	
	private GameObjCollection gobjcol = new GameObjCollection();//collection of game objects.
	
	private int clock;
	private int playerlives;
	private int pylnum = 2;//where car should be
	private boolean sound;
	private int mapwidth, mapheight;
	
	private ViewGameWorldProxy vgworldp;//a proxy to be sent to MapView and ScoreView
	
	private Vector<FixedObject> preparefixedobjects = new Vector<FixedObject>();//oil slicks to be added after the tick, not at the oil slick add method.

	
	public ViewGameWorldProxy(int clock, int playerlives, GameObjCollection gobjcol, boolean sound, int mapwidth, int mapheight)
	{//constructor
		this.clock = clock;
		this.playerlives = playerlives;			
		this.gobjcol = gobjcol;
		this.sound = sound;
		this.mapwidth = mapwidth;
		this.mapheight = mapheight;
	}
	
	public int getPylNum()
	{
		return pylnum;
	}
	

	public GameObjCollection getGameObjCollection()
	{
		return gobjcol;
	}
	

	public int getClock()
	{
		return clock;
	}

	@Override
	public int getPlayerLives() 
	{
		return playerlives;
	}

	public int getMapWidth()
	{
		return mapwidth;
	}
	
	public int getMapHeight()
	{
		return mapheight;
	}
	public void addObserver(Observer obs) 
	{
		// TODO Auto-generated method stub
		obs.update(this,null);
	}

	@Override
	public void notifyObservers() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getSound() 
	{
		// TODO Auto-generated method stub
		return sound;
	}

	
	
	//Anything after this line, is what the proxy is for.
	@Override
	public int getPylToMake() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumOfNPC() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Pylon createPylon(int pyln) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pylon createPylon(double locx, double locy, int pyln) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pylon createFirstPylon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FuelCan createFuelCan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FuelCan createFuelCan(double locx, double locy, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bird createBird() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Car createPlayerCar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NPCCar createNPC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NPCCar createNPC(double locx, double locy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Wall createWall(boolean iswall, Float wallc, int walltype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumOfPyl() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getClockSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMapWidth(int width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMapHeight(int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPylNum(int pylnum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPylToMake(int pylnum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initLayout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGameObjCollection(GameObjCollection gobjcol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayerLives(int plives) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSound(boolean sound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNumOfPyl(int nopyl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClockSpeed(int clockspeed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClock(int clock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToCol(GameObject gobj) {
		// TODO Auto-generated method stub
		
	}
	
	
}
