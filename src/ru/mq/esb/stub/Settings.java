package ru.mq.esb.stub;

import java.io.IOException;
import java.util.Map;

import ru.ashirobokov.app.gears.FileUtil;
import ru.ashirobokov.app.gears.LoggerTools;

public class Settings {


/*	Количество потоков*/
	public int nThreads;
/* Адрес машины с MQ */
	private String host;
/* Номер порта менеджера очередей */
	private int port;

/* Имя менеджера очередей, к которому надо подключиться */
	private String qManagerName;
/* Имена очередей, с которыми планируется взаимодействие */
	private String queueIn;
	private String queueOut;
/*	Название канала для взаимодействия	*/
	private String channel;
/*	Название character set-a */	
	private int CCSID;
	
public Settings() {
	
	try {
		
		Map<String, String> mapConFactorySettings;
		
		mapConFactorySettings	= FileUtil.fromFileToMap(".\\settings.cfg");
		
		this.nThreads = Integer.parseInt(mapConFactorySettings.get("thread"));
		this.host = mapConFactorySettings.get("host");
		this.port = Integer.parseInt(mapConFactorySettings.get("port"));
		this.qManagerName = mapConFactorySettings.get("qManager");
		this.queueIn = mapConFactorySettings.get("queueIn");
		this.queueOut = mapConFactorySettings.get("queueOut");
		this.channel = mapConFactorySettings.get("channel");
		this.CCSID = Integer.parseInt(mapConFactorySettings.get("CCSID"));
		
		LoggerTools.loggerDebug(Settings.class.getName(), "Содержимое конфигурации " + mapConFactorySettings.toString());

	} catch (IOException e) {
		LoggerTools.loggerError(Settings.class.getName(), "Не удалось считать и подготовить конфигурационные данные из файла .\\settings.cfg");
		e.printStackTrace();
	}

 } 

	public String getHost() {
		
		return this.host;
	}

	public int getPort() {
		
		return this.port;
	}

	public String getQManagerName() {
		
		return this.qManagerName;
	}
	
	public String getQueueIn() {
		
		return this.queueIn;
	}

	public String getQueueOut() {
		
		return this.queueOut;
	}
	
	public String getChannel() {
		
		return this.channel;
	}

	public int getCCSID() {
		
		return this.CCSID;
	}
	
}
