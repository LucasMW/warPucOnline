package com.menezesworks.warclient;

import java.net.*; import java.io.*; import java.util.*;

public class Client 
{

	int socketNumber;
	String ipLocalhost;
	public Socket socket;
	public Client(int socketNumber)
	{
		this.socketNumber = socketNumber;
		this.ipLocalhost = "127.0.0.1";
	}
	public void connect(String ip) throws UnknownHostException, IOException 
	{
		if(ip == null)
			ip = this.ipLocalhost;
		this.socket = new Socket(ip, this.socketNumber);
		System.out.println("O cliente se conectou ao servidor!");
		ServerListener serverEar = new ServerListener(this);
		Thread t = new Thread(serverEar);
		Scanner teclado = new Scanner(System.in);
		PrintStream saida = new PrintStream(socket.getOutputStream());
		String msg = teclado.nextLine(); 
		t.start();
		while (msg.compareTo("###")!=0) 
		{
			saida.println(msg); msg = teclado.nextLine();
		}
		serverEar.stop();
		saida.close();
		teclado.close();
		socket.close();

		System.out.println("O cliente terminou de executar!");

		}

	public Readable getInputStream() {
		// TODO Auto-generated method stub
		return null;
	}
}




