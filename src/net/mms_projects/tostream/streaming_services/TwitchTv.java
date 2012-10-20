package net.mms_projects.tostream.streaming_services;

import net.mms_projects.tostream.Service;

public class TwitchTv extends Service {

	public TwitchTv() {
		name = "Twitch.tv";
		authMethod = AUTH_METHOD_TOKEN;

		addServer("Global Load Balancing Service", "live.justin.tv");
		addServer("Blub", "live.blub.tv");
	}

	@Override
	public String getStreamUrl() {
		return "rtmp://" + getCurrentServer() + "/app/" + getToken();
	}

}
