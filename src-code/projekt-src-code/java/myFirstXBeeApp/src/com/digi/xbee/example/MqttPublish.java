package com.digi.xbee.example;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublish {
	
	private MqttClient client; 
	
	public MqttPublish() {
		this.connect();
	}
	
	private void connect() {
//		String broker = "tcp://10.19.128.178:1883";
		String broker = "tcp://10.19.4.140:1883";
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			this.client = new MqttClient(broker, "HA", persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("zaruljasi");
            char [] password = {'z', 'a', 'r', 'u', 'l', 'j', 'a', 's', 'i'};
            connOpts.setPassword(password);
            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected");
		} catch (MqttException me) {
			me.printStackTrace();
		}
	}
	
	private void disconnect() {
		try {
			if (this.client != null ) {
				client.disconnect();
	            System.out.println("Disconnected");
	            System.exit(0);
			}
		
		} catch (MqttException me) {
			me.printStackTrace();
		}
	}
	
	public void sendPacket(String temp, String pir) {
		String messageTemp = "{\"state\":\"" + temp + "\"}";
		String messagePir = "{\"state\":\"" + pir + "\"}";		
		
		try {
            System.out.println("Publishing message");
            MqttMessage mqttMessage = new MqttMessage(messageTemp.getBytes());
            mqttMessage.setQos(2);
            this.client.publish("zigbee2mqtt/tempWaspmote", mqttMessage);
            System.out.println("Message published");
            
            System.out.println("Publishing message");
            mqttMessage = new MqttMessage(messagePir.getBytes());
            mqttMessage.setQos(2);
            this.client.publish("zigbee2mqtt/pirWaspmote", mqttMessage);
            System.out.println("Message published");
            
		} catch (MqttException me) {
			System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
		}		
	}
	
	

}
