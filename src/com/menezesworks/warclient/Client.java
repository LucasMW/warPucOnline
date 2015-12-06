package com.menezesworks.warclient;

import java.net.*; import java.io.*; import java.util.*;

public class Client 
{

	int socketNumber;
	String ipLocalhost;
	public Socket socket;
	public String name; // same name other player will see
	ServerListener serverEar; //thread that listen the server
	PrintStream sendStream; // where data will be sent to the server
	public Client(int socketNumber)
	{
		this.socketNumber = socketNumber;
		this.ipLocalhost = "127.0.0.1";
	}
	public void connect(String ip, String identifierName) throws UnknownHostException, IOException 
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




