package com.digi.xbee.example;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;


public class MainApp {
		
	private static final String PORT = "COM6";
	private static final int BAUD_RATE = 115200;

	public static void main(String[] args) {
		
		XBeeDevice myDevice = new XBeeDevice(PORT, BAUD_RATE);
		
		try {
			myDevice.open();
			
			myDevice.addDataListener(new MyDataReceiveListener());
			
			System.out.println("\n>> Waiting for data...");
			
		} catch (XBeeException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
