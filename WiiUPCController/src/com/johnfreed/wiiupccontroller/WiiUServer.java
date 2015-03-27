package com.johnfreed.wiiupccontroller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
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
				Scanner scanner = new Scanner(socket.getInputStream());
				
				String nextLine = scanner.nextLine();
				System.out.println(nextLine);
				
				// Main page
				if (nextLine.split(" ")[1].equals("/")) {
					printDebugMessage("User connected");
					PrintWriter out = new PrintWriter(socket.getOutputStream());
					out.println("HTTP/1.1 200 OK");
					out.println("Content-Type: text/html");
					out.println();
					out.println(RetrieveIndexHTML());
					out.flush();
					out.close();
					scanner.close();
					continue;
				}
				
				// Favicon
				if (nextLine.split(" ")[1].equals("/favicon.ico")) {
					printDebugMessage("Requested favicon");
					PrintWriter out = new PrintWriter(socket.getOutputStream());
					out.println("HTTP/1.1 404 Not Found");
					out.println();
					out.flush();
					out.close();
					scanner.close();
					continue;
				}
				
				// Commands
				if (nextLine.split(" ")[0].equals("POST") && nextLine.split(" ")[1].equals("/Command"))
				{
					System.out.println("Derp");
					continue;
				}
				
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					System.out.println(line);
					if (line.equals("")) {
						line = scanner.nextLine();
						System.out.println(line);
						HashMap<String, Float> data = ParseWiiUData(line);
					}
					System.out.println(line);
				}
				
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				out.println("HTTP/1.1 200 OK");
				out.println("Connection: close");
				out.println();
				out.flush();
				out.close();
				scanner.close();
			}
			catch (Exception ex) {
				printDebugMessage("Caught exception: " + ex.getMessage());
			}
		}
	}
	
	private HashMap<String, Float> ParseWiiUData(String JSON) {
		HashMap<String, Float> data = new HashMap<String, Float>();
		
		// Replace brackets
		String jsonData = JSON.replace("{", "");
		jsonData = jsonData.replace("}", "");
		
		// Example data coming in: {"a":0,"b":1,"c":2}
		
		String[] dataPairs = jsonData.split(",");
		
		for (int idx = 0; idx < dataPairs.length; idx++) {
			// Get a single data pair ex: "a":0
			String dataPair = dataPairs[idx];
			String[] temp = dataPair.split(":");
			
			// Get the key and value
			String key = temp[0];
			Float value = Float.valueOf(temp[1]);
			
			// Put the key value pair in the HashMap
			data.put(key, value);
		}
		
		return data;
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
