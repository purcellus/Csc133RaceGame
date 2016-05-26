package a4;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class Sound 
{//sounds for the game
	private AudioClip myclip;
	private static boolean soundon;//only one instance of this should occur
	
	public Sound(String filename)
	{
		
		try
		{
			File file = new File(filename);
			if (file.exists())
			{
				myclip = Applet.newAudioClip(file.toURI().toURL());
			} else
			{
				throw new RuntimeException("Sound:  File not found ");
			}
		} catch (MalformedURLException e)
		{
			
		}
		soundon = true;
	}
	
	public void play()
	{
		//System.out.println("Playing sound");
		if (soundon)
		{
			myclip.play();
		}
	}
	
	public void loop()
	{
		if (soundon)
		{
			myclip.loop();
		}	}
	
	public void stop()
	{
		if (soundon)
		{
			myclip.stop();
		}	
	}
	
	public void setSound(boolean thesound)
	{
		soundon = thesound;
		
	}
	
}
