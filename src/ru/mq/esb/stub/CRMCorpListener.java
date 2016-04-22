package ru.mq.esb.stub;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
//import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import ru.ashirobokov.app.gears.LoggerTools;

public class CRMCorpListener implements MessageListener {
	
	private Receiver receiver;
	private static final String reqFile = ".\\samples\\CreateLegalLeadApplicationWithClientRs.xml";
	

public CRMCorpListener(Receiver receiver) {
	
	this.receiver = receiver;

}
	
	
	@Override
	public void onMessage(Message message) {

		try {
			
			QueueSession session = this.receiver.getQueueConnection().createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(Factory.getInstance().getQueueOut());
			QueueSender sender = session.createSender(queue); 

			TextMessage reqMessage = (TextMessage) message;
			InputStream msgStream = new ByteArrayInputStream(reqMessage.getText().getBytes("UTF-8"));
			
			SAXBuilder reqBuilder = new SAXBuilder();
			
			Document request = reqBuilder.build(msgStream).getDocument();
			Element reqRoot = request.getRootElement();
			String method = reqRoot.getChild("Method").getText();
			
			LoggerTools.loggerDebug(CRMCorpListener.class.getName(),"Method " + method);
			
			if ("SitetoCRMCreateApplication".equals(method)) {

					String reqUid = reqRoot.getChild("RqUID").getText();
					String reqTm = reqRoot.getChild("RqTm").getText();
					String reqSpName = reqRoot.getChild("SPName").getText();
					String reqSystemId = reqRoot.getChild("SystemId").getText();
					
					LoggerTools.loggerDebug(CRMCorpListener.class.getName(), "Данные из запроса [" + reqUid + "," + 
																				reqTm + "," +reqSpName + "," + reqSystemId + "]" );
					
					String dateString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new java.util.Date());
					
					SAXBuilder resBuilder = new SAXBuilder();
					Document response = resBuilder.build(new File(reqFile)).getDocument();

					Element resRoot = response.getRootElement();
					resRoot.getChild("RqUID").setText(reqUid);
					resRoot.getChild("RqTm").setText(dateString);
					resRoot.getChild("SPName").setText(reqSpName);
					resRoot.getChild("SystemId").setText(reqSystemId);

					XMLOutputter xmOut=new XMLOutputter();
					String responseString = xmOut.outputString(response);

					LoggerTools.loggerDebug(CRMCorpListener.class.getName(), "respFile : " + responseString);
					
					TextMessage resMessage = session.createTextMessage(responseString);
					resMessage.setJMSCorrelationID(reqMessage.getJMSMessageID());
					sender.send(resMessage);					

					LoggerTools.loggerDebug(CRMCorpListener.class.getName(), "Сообщение обработано потоком " + this.receiver.getThreadName());
			}
			else {
		
					LoggerTools.loggerDebug(CRMCorpListener.class.getName(), 
											"Метод " + method + " не обрабатывается CRMCorpListener-ом;"
														+ " Сообщение-запрос : [" + reqMessage.getText() +"]" );
			
			}

		} catch (JMSException e) {
			Logger.getLogger(CRMCorpListener.class.getName()).log(Level.SEVERE, null, e);
//			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			Logger.getLogger(CRMCorpListener.class.getName()).log(Level.SEVERE, null, e);
		} catch (JDOMException e) {
			Logger.getLogger(CRMCorpListener.class.getName()).log(Level.SEVERE, null, e);
		} catch (IOException e) {
			Logger.getLogger(CRMCorpListener.class.getName()).log(Level.SEVERE, null, e);
		} 
	
	}

}
