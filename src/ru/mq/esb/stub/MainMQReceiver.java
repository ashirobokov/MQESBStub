package ru.mq.esb.stub;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.ashirobokov.app.gears.LoggerTools;

/*
 *
 * 	MQ Stub 
 * 	@author ashirobokov
 * 
 */
public class MainMQReceiver {
	
public static Factory factory;

	public static void main(String[] args) {

			LoggerTools.loggerInfo(MainMQReceiver.class.getName(), "MQ Stub started");

			int numThreads = Integer.parseInt(Settings.getInstance().getProperty("thread"));

			Executor executor = Executors.newFixedThreadPool(numThreads);

			for (int i = 0; i < numThreads; i++) {
	    	
	    	   		executor.execute(new Receiver());
	       
	       }

	}	

}
