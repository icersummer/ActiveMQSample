package com.vjia.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

//import org.apache.activemq.leveldb.replicated.MasterLevelDBStore.Session;
//import org.apache.activemq.leveldb.replicated.SlaveLevelDBStore.Session;

public class Producer {

	private static String brokerURL = "tcp:127.0.0.1:61616";
	private static transient ConnectionFactory factory;
	private transient Connection connection;
	private transient Session session;
	private transient MessageProducer producer;
	private static String queueName = "DEMO";
	private static int total;
	private static int id = 1000000;

	public Producer() throws JMSException {
		factory = new ActiveMQConnectionFactory(brokerURL);
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		producer = session.createProducer(null);
	}

	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

	public static void main(String[] args) throws JMSException {
		Producer mProducer = new Producer();
		while (total < 1000) {
			mProducer.sendMessage();
			System.out.println("Sent the '" + total + "' times DEMO message");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		mProducer.close();
	}

	public void sendMessage() throws JMSException {
		// TODO Auto-generated method stub
		Destination destination = session.createQueue(queueName);
//		session.createTopic("TEST.TOO");
		Message message = session.createObjectMessage(queueName + id++);
		producer.send(destination, message);
	}

}
