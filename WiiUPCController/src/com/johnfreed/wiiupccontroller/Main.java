package com.johnfreed.wiiupccontroller;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// Set up the server
		WiiUServer server = new WiiUServer();
		server.setPort(1338);
		server.setPrintDebugStatements(true);
		
		try {
			server.StartServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
