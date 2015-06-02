package com.vjia.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

	private static String brokerURL = "tcp:102.168.1.102:61616";
	private static transient ConnectionFactory factory;
	private transient Connection connection;
	private transient Session session;

	private static String queueName = "DEMO";

	public Consumer() throws JMSException {
		factory = new ActiveMQConnectionFactory(brokerURL);
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

	/**
	 * CONSUMER THE MESSAGE A-SYNC WAY; MAINLY BECAUSE USE MESSAGE-LISTENER
	 * @param args
	 * @throws JMSException
	 */
	public static void main(String[] args) throws JMSException {
		Consumer consumer = new Consumer();
		Destination destination = consumer.getSession().createQueue(queueName);
		MessageConsumer messageConsumer = consumer.getSession().createConsumer(
				destination);
		messageConsumer.setMessageListener(new AsyncListener());

	/*	boolean syncMessage = true;
		while (syncMessage) {
			Message message = messageConsumer.receive();
			System.out.println("Message is :"
					+ ((ObjectMessage) message).getObject());
			continue;
		}*/

		boolean testAsync = true;
		while (testAsync) {
			System.out.println("test async message !");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * CONSUMER THE MESSAGE SYNC WAY
	 * @param args
	 * @throws JMSException
	 */
	/*
	public static void main(String[] args) throws JMSException {
		Consumer consumer = new Consumer();
		Destination destination = consumer.getSession().createQueue(queueName);
		MessageConsumer messageConsumer = consumer.getSession().createConsumer(
				destination);
//		messageConsumer.setMessageListener(new AsyncListener());

		boolean syncMessage = true;
		while (syncMessage) {
			Message message = messageConsumer.receive();
			System.out.println("Message is :"
					+ ((ObjectMessage) message).getObject());
			continue;
		}

		boolean testAsync = true;
		while (testAsync) {
			System.out.println("test async message !");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
*/

	private Session getSession() {
		// TODO Auto-generated method stub
		return session;
	}
}
