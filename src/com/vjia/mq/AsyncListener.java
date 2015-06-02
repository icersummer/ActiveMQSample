package com.vjia.mq;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class AsyncListener implements MessageListener {

	public AsyncListener() {

	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			// do something here
			System.out
					.println("Received from MessageListener, the message is : " +((ObjectMessage)message).getObject());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
