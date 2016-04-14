package ru.mq.esb.stub;

import javax.jms.JMSException;

import ru.ashirobokov.app.gears.LoggerTools;

import com.ibm.mq.jms.MQQueueConnectionFactory;

public class Factory {

//  Фабрика подключения к MQ	
	public MQQueueConnectionFactory factory = null;
//  Имя менеджера очередей
	public String qManagerName;
//	Очередь из которой принимаем сообщения	
	public String queueIn;
//	Очередь в которую отправляем сообщения	
	public String queueOut;
	
/*
 * 		Создание фабрики очередей
 */
public Factory(Settings settings) {

	try {
	
			qManagerName = new String(settings.getQManagerName());
			queueIn = new String(settings.getQueueIn());
			queueOut = new String(settings.getQueueOut());
			
			factory = new MQQueueConnectionFactory();
			
			factory.setCCSID(settings.getCCSID());
	
			factory.setHostName(settings.getHost());
			factory.setPort(settings.getPort());
			factory.setQueueManager(qManagerName);
			factory.setChannel(settings.getChannel());

	//	factory.setTransportType(WMQConstants. WMQ_CM_CLIENT);

		} catch (JMSException e) {

			LoggerTools.loggerError(Factory.class.getName(), "Возникает ошибка при создании фабрики подключений к MQ", e);
		
		}

	}

}
