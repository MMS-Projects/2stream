package net.mms_projects.tostream;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.mms_projects.tostream.streaming_services.TwitchTv;

public class ServiceManager {

	public LinkedHashMap<String, Service> services = new LinkedHashMap<String, Service>();
	private String currentService; 
	
	public ServiceManager() {
		addService(new TwitchTv());
	}
	
	public void addService(Service service) {
		services.put(service.name, service);
		if (services.size() == 1) {
			try {
				setCurrentService(service.name);
			} catch (Exception e) {
			}
		}
	}
	
	public String[] getServiceNames() {
		String[] serviceNames = new String[services.size()];
		int i = 0;
		for (String key : services.keySet()) {
			serviceNames[i] = key;
			i++;
		}
		return serviceNames;
	}

	public Service getCurrentService() {
		return services.get(currentService);
	}

	public void setCurrentService(String currentService) throws Exception {
		if (services.containsKey(currentService)) {
			this.currentService = currentService;
		} else {
			throw new Exception("Unknown service " + currentService + ".");
		}
	}

}
