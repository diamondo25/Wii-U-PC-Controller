package com.johnfreed.wiiupccontroller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class WiiUServer {
	
	private int mPort = 1337;
	private boolean mPrintDebugStatements = false;
	private PCController mController;
	
	public WiiUServer(PCController controller) {
		this.mController = controller;
	}
	
	public void StartServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(mPort);
		System.out.println("Wii U PC Controller Server running on port " + mPort);
		
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				Scanner scanner = new Scanner(socket.getInputStream());
				
				String nextLine = scanner.nextLine();
				
				// Main page
				if (nextLine.split(" ")[1].equals("/")) {
					printDebugMessage("User connected");
					out.println("HTTP/1.1 200 OK");
					out.println("Content-Type: text/html");
					out.println();
					out.println(RetrieveIndexHTML());
					out.flush();
					out.close();
					scanner.close();
				}
				
				// Favicon
				if (nextLine.split(" ")[1].equals("/favicon.ico")) {
					printDebugMessage("Requested favicon");
					out.println("HTTP/1.1 404 Not Found");
					out.println();
					out.flush();
					out.close();
					scanner.close();
				}
				
				// Commands
				if (nextLine.split(" ")[0].equals("POST") && nextLine.split(" ")[1].equals("/Command"))
				{
					printDebugMessage("Received Command");
					while (scanner.hasNextLine())
					{
						String line = scanner.next();
						printDebugMessage(line);
					}
				}
				
				//printDebugMessage(nextLine);
			}
			catch (Exception ex) {
				printDebugMessage("Caught exception: " + ex.getMessage());
			}
		}
	}
	
	private String RetrieveIndexHTML() throws IOException {
		String stringToReturn = "";
		
		String file = "resources/index.html";
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String line;
		while ((line = br.readLine()) != null)
		{
			stringToReturn += line + '\r';
		}
		
		return stringToReturn;
	}
	
	private void printDebugMessage(Object message) {
		if (mPrintDebugStatements)
		{
			System.out.println(message);
		}
	}
	
	// Getters and setters
	public int getPort() {
		return mPort;
	}

	public void setPort(int port) {
		this.mPort = port;
	}
	
	public boolean getPrintDebugStatements() {
		return mPrintDebugStatements;
	}
	
	public void setPrintDebugStatements(boolean printDebugStatements) {
		this.mPrintDebugStatements = printDebugStatements;
	}
}
