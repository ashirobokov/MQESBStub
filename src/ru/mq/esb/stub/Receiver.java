package ru.mq.esb.stub;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;

import ru.ashirobokov.app.gears.LoggerTools;

import com.ibm.mq.jms.MQQueueConnectionFactory;

public class Receiver implements Runnable {

private MQQueueConnectionFactory mqFactory;	
private QueueConnection con;
private MessageListener listener;
private QueueSession session;
private String tName;


public QueueConnection getQueueConnection() {
	
	return this.con;
}

public String getThreadName() {
	
	return this.tName;
}

		public void run() {
			
			tName = Thread.currentThread().getId() + " [" + Thread.currentThread().getName() + "]";

			LoggerTools.loggerInfo(Receiver.class.getName(), "SBRF ESB эмулятор работает" + " в потоке " + tName);
			
			
			 try {
					

				 	mqFactory = Factory.getInstance().getWMQFactory();
				 
					con = mqFactory.createQueueConnection();
					session = con.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
					Queue reqQueue = session.createQueue(Factory.getInstance().getQueueIn());
					QueueReceiver receiver = session.createReceiver(reqQueue);
					listener = new CRMCorpListener(this);
					receiver.setMessageListener(listener);

					con.start(); 

					System.out.println("Введите 'quit' для завершения потока " + tName +  " :");

					Scanner scn = new Scanner(System.in);
					while(!scn.nextLine().equals("quit")) {}
					
					scn.close();
					con.close();
					
					System.out.println("Поток " + tName + " остановлен");
					
				} catch (JMSException e) {
					Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, e);
//					e.printStackTrace();
				
				}  
			
		}
		
}
