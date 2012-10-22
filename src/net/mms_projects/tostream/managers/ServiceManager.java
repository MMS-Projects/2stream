package net.mms_projects.tostream.managers;

import java.util.LinkedHashMap;

import net.mms_projects.tostream.Manager;
import net.mms_projects.tostream.Service;
import net.mms_projects.tostream.streaming_services.Bambuser;
import net.mms_projects.tostream.streaming_services.TwitchTv;


public class ServiceManager extends Manager<Service> {

	public ServiceManager() {
		addItem(new TwitchTv());
		addItem(new Bambuser());
	}

	public void addItem(Service item) {
		super.addItem(item.name, item);
	}

}
