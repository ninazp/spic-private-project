package com.jeeplus.modules.fea.designcal;

import java.util.Map;

public class PubReport extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	private static PubReport instance; 
	
	private Map<String,Object> retmap;
	
    public static PubReport getInstance(String projectId) {  
    	if(null==instance||null==projectId){
    		instance = new PubReport();
    	}
    	return instance;
    }
    
	public Map<String, Object> getRetmap() {
		return retmap;
	}
	public void setRetmap(Map<String, Object> retmap) {
		this.retmap = retmap;
	}
}
