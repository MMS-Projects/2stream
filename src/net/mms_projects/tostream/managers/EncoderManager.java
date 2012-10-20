package net.mms_projects.tostream.managers;

import java.util.ArrayList;
import java.util.List;

import net.mms_projects.tostream.Encoder;
import net.mms_projects.tostream.EncoderOutputListener;
import net.mms_projects.tostream.Manager;
import net.mms_projects.tostream.Settings;

public class EncoderManager extends Manager<Encoder> {

	private List<EncoderOutputListener> listeners = new ArrayList<EncoderOutputListener>();

	private Settings settings;

	public EncoderManager(Settings settings) {
		this.settings = settings;
	}

	public void addItem(Encoder item) {
		item.setDaemon(true);
		item.start();
		
		super.addItem(item.getClass().getName(), item);
	}

	public void addListener(EncoderOutputListener toAdd) {
		listeners.add(toAdd);
	}

	public List<EncoderOutputListener> getListeners() {
		return listeners;
	}

}
