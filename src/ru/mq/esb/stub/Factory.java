package ru.mq.esb.stub;

import javax.jms.JMSException;

import ru.ashirobokov.app.gears.LoggerTools;

import com.ibm.mq.jms.MQQueueConnectionFactory;

public class Factory {

//  ������� ����������� � MQ	
	public MQQueueConnectionFactory factory = null;
//  ��� ��������� ��������
	public String qManagerName;
//	������� �� ������� ��������� ���������	
	public String queueIn;
//	������� � ������� ���������� ���������	
	public String queueOut;
	
/*
 * 		�������� ������� ��������
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

			LoggerTools.loggerError(Factory.class.getName(), "��������� ������ ��� �������� ������� ����������� � MQ", e);
		
		}

	}

}
