package com.menezesworks.warclient;

import java.io.IOException;
import java.util.Scanner;

public class ServerListener implements Runnable
{
	Scanner inputFromServer;
	Boolean destroyed;
	Client reference; 
	public ServerListener(Client c)
	{
		this.reference = c;
		this.destroyed = false;
	}
		@Override
		public void run() 
		{
			// TODO Auto-generated method stub		
			try 
			{
				inputFromServer = new Scanner(reference.socket.getInputStream());
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			while(!destroyed) 
			{
				if (inputFromServer.hasNextLine()) 
				{
					System.out.println(inputFromServer.nextLine());
					try 
					{
						Thread.sleep(50);                 //1000 milliseconds is one second.
					} 
					catch(InterruptedException ex) 
					{
			   
					}
				}
				
			}
		}
		public void stop()
		{
			this.destroyed=true;
		}
}


