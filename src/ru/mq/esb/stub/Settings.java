package ru.mq.esb.stub;

import java.io.IOException;
import java.util.Map;

import ru.ashirobokov.app.gears.FileUtil;
import ru.ashirobokov.app.gears.LoggerTools;

public class Settings {

	private volatile static Settings _settings = null;
	private static Map<String, String> mapConFactorySettings;	
		
	private Settings() {
		
		try {
			
			mapConFactorySettings = FileUtil.fromFileToMap(".\\settings.cfg");
			
		} catch (IOException e) {
			LoggerTools.loggerError(Settings.class.getName(), "Не удалось считать и подготовить конфигурационные данные из файла .\\settings.cfg");
			e.printStackTrace();
		}

	 }

	public static Settings getInstance() {

		if (_settings == null)
	    	 synchronized (Settings.class) {
	    		 if (_settings == null)
	    			 _settings = new Settings();
	    	 }	 
	    
	    return _settings;
	 }

	public synchronized String getProperty(String key) {
	    String value = null;
	    if (mapConFactorySettings.containsKey(key))
	        value = (String) mapConFactorySettings.get(key);
	    else {
			LoggerTools.loggerError(Settings.class.getName(), "Свойство с ключем " + key + " не найдено");
	    }
	    return value;
	 }

}
