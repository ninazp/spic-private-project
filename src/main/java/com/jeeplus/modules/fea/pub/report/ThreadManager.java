package com.jeeplus.modules.fea.pub.report;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager{
	private static ExecutorService instanceservice = Executors.newFixedThreadPool(3);
	
	public static synchronized ExecutorService getinstanceservice(){
		if(null==instanceservice) {
			return Executors.newFixedThreadPool(3);
		}
		return instanceservice;
	}
}
