package a4.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import a4.gameInterface.ISelectable;
import a4.gameObject.GameObject;
import a4.gameObject.ShockWave;

public class GameObjCollection implements Collection
{//Iterator design.
	private Vector <GameObject> gobjcol;//the collection in question
	private int gc = 0;//counter for collection

	public GameObjCollection()
	{
		gobjcol = new Vector();
	}
	
	@Override
	public boolean add(Object e) 
	{//add game object

		gobjcol.addElement( (GameObject) e);
		return true;
	}

	public boolean add(Vector v)
	{
		gobjcol.addAll(v);
		return true;
	}


	@Override
	public boolean addAll(Collection c) 
	{//GUESS NOT IGNORE, GOTTA USE IT
		Iterator cit = c.iterator();
		while (cit.hasNext())
		{
			//System.out.println("adding elements to new collection");
			add(cit.next());
		}
		
		return true;
	}



	@Override
	public void clear() 
	{//reset game objects
		gobjcol.clear();
	}



	@Override
	public boolean contains(Object obj) 
	{//check to see if that object contains this.
		//THIS IS NOT FOR ITERATIVE PURPOSES.
		gc = 0;//counter
		while (gc < gobjcol.size())
		{
			if ( gobjcol.elementAt(gc).equals(obj) )
			{//if the object is that type
				return true;
			}
			gc++;
		}
		return false;
	}



	@Override
	public boolean containsAll(Collection c) 
	{
		System.out.println("No ContainsAll.");
		return false;
	}



	@Override
	public boolean isEmpty() 
	{
		if (gobjcol.isEmpty())
		{
			return true;
		}
		return false;
	}



	@Override
	public Iterator iterator() 
	{
		GameObjectIterator theit = new GameObjectIterator();
		return theit;
	}



	@Override
	public boolean remove(Object o) 
	{//remove an object
		//System.out.println("Removing " + o.toString());
		int gc = 0;
		while (gc < gobjcol.size())
		{
			if (gobjcol.elementAt(gc).equals(o))
			{
				gobjcol.removeElementAt(gc);
				return true;
			}
			gc++;
		}
		return false;
	}

	public void removeShock()
	{//remove shockwaves
		int gc = 0;
		while (gc < gobjcol.size())
		{
			Object gobj = gobjcol.elementAt(gc);
			if (gobj instanceof ShockWave)
			{
				if ( ((ShockWave) gobj).removeShock() )
				{//if the object is flagged for removal
					gobjcol.remove(gobj);//remove this shockwave
					gc = 0;//just in case
				}
			}
			gc++;
		}
	}
	
	public void removeSelected()
	{
		int gc = 0;
		while (gc < gobjcol.size())
		{//while there are still elements
			Object gobj = gobjcol.elementAt(gc);//make it an object to typecast as ISelectable
			if (gobj instanceof ISelectable)
			{//if the game object is a selectable
				if (((ISelectable)gobj).isSelected())
				{//if the object is selected
					gobjcol.remove(gobj);//remove the game object
					gc = 0;//reset counter, just in case something is missed.
				}
			}
			gc++;
		}
	}

	@Override
	public boolean removeAll(Collection c) 
	{
		System.out.println("No RemoveAll based on a collection.");
		return false;
	}



	@Override
	public boolean retainAll(Collection c)
	{
		System.out.println("No RetainAll.");
		return false;
	}



	@Override
	public int size() 
	{
		return gobjcol.size();
	}



	@Override
	public Object[] toArray() 
	{
		System.out.println("No ToArray.");
		return null;
	}



	@Override
	public Object[] toArray(Object[] a) 
	{
		System.out.println("No ToArrayObjectversion.");
		return null;
	}
	

	
	
	private class GameObjectIterator implements Iterator
	{//Iterator design.

		private GameObjectIterator()
		{//reset counter whenever redeclared
			gc = 0;
		}
		
		@Override
		public boolean hasNext() 
		{
			if (gobjcol.size() <= 0)
			{
				System.out.println("No objects in game collection.");
				return false;
			}
			if (gc == gobjcol.size() )
			{
				///System.out.println("reached the end of the game collection.");
				return false;
			}
			return true;
		}

		@Override
		public Object next() 
		{//increment after getting next element, starting at 0
			//this is the same thing as the getNext() method.
			GameObject retobj = gobjcol.elementAt(gc);
			gc++;
			return retobj;
		}
		
	}



	
}
