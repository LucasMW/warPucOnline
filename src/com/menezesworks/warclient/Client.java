package com.menezesworks.warclient;

import java.net.*; import java.awt.Color;
import java.io.*; import java.util.*;
import java.util.regex.Pattern;

import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.WarLogic;
import org.puc.rio.inf1636.hglm.war.model.Player;

public class Client 
{

	int socketNumber;
	String ipLocalhost;
	public Socket socket;
	public String name; // same name other player will see
	ServerListener serverEar; //thread that listen the server
	PrintStream sendStream; // where data will be sent to the server
	List<Player> players = new ArrayList<Player>();
	public Client(int socketNumber)
	{
		this.socketNumber = socketNumber;
		this.ipLocalhost = "127.0.0.1";
	}
	public void connect(String ip, String identifierName, int colorNum) throws UnknownHostException, IOException 
	{
		if(ip == null)
			ip = this.ipLocalhost;
		this.socket = new Socket(ip, this.socketNumber);
		System.out.println("Connected to server!");
		this.serverEar = new ServerListener(this);
		Thread t = new Thread(serverEar);
		this.sendStream = new PrintStream(socket.getOutputStream()); 
		t.start();
		this.name = identifierName;
		sendStream.println(identifierName); // first data received by server from this client is it's player name
		this.sendMessageToServer(String.format("%d",colorNum));
		System.out.println("IOWHDOHIOIFhoihewofh"); 
		}
	//must be connected
	public void sendMessageToServer(String msg)
	{
		sendStream.println(msg);
	}
	public void receivedMessageFromServer(String msg)
	{
		if( msg.contains("players connected") )
		{
			char[] array = msg.toCharArray();
			System.out.println(msg);
			int playersReady = Integer.parseInt(msg.substring("players connected ".length()) );
			System.out.printf("players ready : %d /n ",playersReady);
			
			if(playersReady >= WarLogic.MIN_PLAYERS)
			{
				//tell it can start the game
				WarGame.getInstance().enoughPlayersToStartMultiplayers(playersReady);
			}
			
		} else if( msg.contains("player list") && players.isEmpty() )
		{
			
			String[] parts = msg.split(";");
			for (int i = 1; i < parts.length; i++)
			{
				String part = parts[i];
				String[] playerParts = part.split(Pattern.quote(":"));
				String name = playerParts[0];
				Color color = new Color(Integer.parseInt(playerParts[1]));
				Player player = new Player(name, color);
				if(!players.contains(player))
				{
					players.add(player);
				}
			}
			
		}
		else if( msg.contains("become leader"))
		{
			System.out.println("we have a leader");
			WarGame.getInstance().startGame(players);
		}
		System.out.printf("received msg: %s \n ",msg);
	}
	
	public void disconnect() throws IOException
	{
		serverEar.stop();
		this.sendStream.close();
		socket.close();
	}

	public Readable getInputStream() {
		// TODO Auto-generated method stub
		return null;
	}
}




