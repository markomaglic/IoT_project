package com.digi.xbee.example;

import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import com.digi.xbee.api.utils.HexUtils;

public class MyDataReceiveListener implements IDataReceiveListener {
	
	private MqttPublish client;
	
	public MyDataReceiveListener() {
		this.client = new MqttPublish();
	}
	
	@Override
	public void dataReceived(XBeeMessage xbeeMessage) {
		String reading = new String(xbeeMessage.getData());
		String pir = reading.split("#")[4].split(":")[1];
		String temp = reading.split("#")[5].split(":")[1];		
		this.client.sendPacket(temp, pir);
		System.out.println(pir + " " + temp);
	}
}
