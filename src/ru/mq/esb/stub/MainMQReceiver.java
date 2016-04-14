package ru.mq.esb.stub;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import ru.ashirobokov.app.gears.LoggerTools;

public class MainMQReceiver {
	
public static Settings settings;
public static Factory factory;

	public static void main(String[] args) {

			LoggerTools.loggerInfo(MainMQReceiver.class.getName(), "MQ Stub started");

			settings = new Settings();

			factory = new Factory(settings);

			int numThreads = settings.nThreads;

			Executor executor = Executors.newFixedThreadPool(numThreads);

			for (int i = 0; i < numThreads; i++) {
	    	
	    	   		executor.execute(new Receiver(factory));
	       
	       }

	}	

}
