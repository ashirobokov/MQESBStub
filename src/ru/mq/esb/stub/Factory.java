package ru.mq.esb.stub;

import javax.jms.JMSException;

import ru.ashirobokov.app.gears.LoggerTools;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

public class Factory {

	private static Factory _factory = null;

	private static MQQueueConnectionFactory wmqFactory = null;	
	private static String qManagerName;
	private static String queueIn;
	private static String queueOut;
	
/*
 * 		Создание фабрики очередей
 */
private Factory() {

	try {
	
		qManagerName = Settings.getInstance().getProperty("qManager");
		queueIn = Settings.getInstance().getProperty("queueIn");
		queueOut = Settings.getInstance().getProperty("queueOut");
			
		wmqFactory = new MQQueueConnectionFactory();
		
		wmqFactory.setCCSID(Integer.parseInt(Settings.getInstance().getProperty("CCSID")));

		wmqFactory.setHostName(Settings.getInstance().getProperty("host"));
		wmqFactory.setPort(Integer.parseInt(Settings.getInstance().getProperty("port")));
		wmqFactory.setQueueManager(qManagerName);
		wmqFactory.setChannel(Settings.getInstance().getProperty("channel"));

		wmqFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

		} catch (JMSException e) {

			LoggerTools.loggerError(Factory.class.getName(), "Возникает ошибка при создании фабрики подключений к MQ", e);
		
		}

}

	public static Factory getInstance() {
	    if (_factory == null)
	    	 synchronized (Factory.class) {
	    		 if (_factory == null)
	    			 _factory = new Factory();
	    	 }	 
	    return _factory;
	 }
	
	
	public synchronized MQQueueConnectionFactory getWMQFactory() {
		
	return wmqFactory;
	}
	
	
	public synchronized String getQManagerName() {
		
	return qManagerName;
	}
	
	public synchronized String getQueueIn() {
		
	return queueIn;
	}
	
	public synchronized String getQueueOut() {
		
	return queueOut;
	}

}
