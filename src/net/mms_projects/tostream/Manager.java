package net.mms_projects.tostream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class Manager<T> {
	
	private LinkedHashMap<String, T> items = new LinkedHashMap<String, T>();
	private String currentItem;

	public void addItem(String name, T item) {
		items.put(name, item);
		if (items.size() == 1) {
			try {
				setCurrentItem(name);
			} catch (Exception e) {
			}
		}
	}

	public String getItemName(int index) {
		int searchIndex = 0;
		for (String key : items.keySet()) {
			if (searchIndex == index) {
				return key;
			}
			index++;
		}
		return null;
	}
	
	public T getItem(int index) {
		return getItem(getItemName(index));
	}
	
	public T getItem(String itemName) {
		return items.get(itemName);
	}
	
	public T getItem() {
		return getCurrentItem();
	}
	
	public T getCurrentItem() {
		return items.get(currentItem);
	}

	public String[] getItemNames() {
		String[] itemNames = new String[items.size()];
		int i = 0;
		for (String key : items.keySet()) {
			itemNames[i] = key;
			i++;
		}
		return itemNames;
	}

	public void setCurrentItem(String currentItem) throws Exception {
		if (items.containsKey(currentItem)) {
			this.currentItem = currentItem;
		} else {
			throw new Exception("Unknown item " + currentItem + ".");
		}
	}

	public Collection<T> getItems() {
		return items.values();
	}
	
	public int getItemIndex(String itemName) {
		int index = 0;
		for (String key : items.keySet()) {
			if (key.equalsIgnoreCase(itemName)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
}