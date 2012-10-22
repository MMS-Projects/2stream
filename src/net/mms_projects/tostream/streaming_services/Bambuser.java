package net.mms_projects.tostream.streaming_services;

import net.mms_projects.tostream.Service;

public class Bambuser extends Service {

	public Bambuser() {
		name = "Bambuser";
		authMethod = AUTH_METHOD_TOKEN;
		
		addServer("Streaming server", "live.fme.bambuser.com/b-fme");
	}
	
	@Override
	public String getStreamUrl() {
		return "rtmp://" + getCurrentServer() + "/" + getToken();
	}
	
}
