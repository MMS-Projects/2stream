package net.mms_projects.tostream;

import java.util.Collection;
import java.util.LinkedHashMap;

public class Service {
	
	public String name;
	public LinkedHashMap<String, String> servers = new LinkedHashMap<String, String>();
	private String currentServer; 
	
	public int authMethod;
	
	public static final int AUTH_METHOD_TOKEN = 1;
	public static final int AUTH_METHOD_USERNAME = 2;
	
	protected String token;
	protected String username;
	protected String password;
	
	public String[] getServerNames() {
		String[] serverNames = new String[servers.size()];
		int i = 0;
		for (String key : servers.keySet()) {
			serverNames[i] = key;
			i++;
		}
		return serverNames;
	}
	
	public String[] getServers() {
		Collection<String> collection = servers.values();
		return (String[]) collection.toArray(new String[collection.size()]);
	}
	
	public String getStreamUrl() {
		return "Not implemented";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addServer(String name, String url) {
		servers.put(name, url);
		if (servers.size() == 1) {
			try {
				setCurrentServer(name);
			} catch (Exception e) {
			}
		}
	}
	
	public String getCurrentServer() {
		return servers.get(currentServer);
	}

	public void setCurrentServer(String currentServer) throws Exception {
		if (servers.containsKey(currentServer)) {
			this.currentServer = currentServer;
		} else {
			throw new Exception("Unknown service " + currentServer + ".");
		}
	}
	
	public String getCurrentServerName() {
		return currentServer;
	}
	
	public int getCurrentServerIndex(String serverName) {
		int index = 0;
		for (String key : servers.keySet()) {
			if (key.equalsIgnoreCase(serverName)) {
				return index;
			}
			++index;
		}
		return -1;
		
	}

}
