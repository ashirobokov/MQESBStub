package ru.mq.esb.stub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
private Factory factory;
private String tName;

public Receiver(Factory factory) {

	this.factory = factory;
}

public Factory getFactory() {
	
	return this.factory;
}

public QueueConnection getQueueConnection() {
	
	return this.con;
}

public String getThreadName() {
	
	return this.tName;
}

		public void run() {
			
			tName = Thread.currentThread().getId() + " [" + Thread.currentThread().getName() + "]";

			LoggerTools.loggerInfo(Receiver.class.getName(), "SBRF ESB эмулятор работает" + " в потоке " + tName);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    String stri=null;
			
			 try {
					

				 	mqFactory = this.factory.factory;
				 
					con = mqFactory.createQueueConnection();
					
					session = con.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
					
					Queue reqQueue = session.createQueue(this.factory.queueIn);
					
					QueueReceiver receiver = session.createReceiver(reqQueue);

					listener = new CRMCorpListener(this);
					
					receiver.setMessageListener(listener);

					con.start(); 

					System.out.println("Введите 'quit' для завершения:");
					while(true) {    
				    	stri=new String(br.readLine());
						if (stri.equals("quit")) {
							System.out.println("Завершено");
							break;
						}
					}    
				
					con.close();
					
				} catch (JMSException e) {
					Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, e);
//					e.printStackTrace();
				
				}   catch (IOException e) {
					Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, e);
//					e.printStackTrace();

				} 

			
		}
		
}
