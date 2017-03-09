package backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import commands.Command;
import commands.UserMethodCommand;

public class UserMethodManager extends Observable implements java.io.Serializable{

	private HashMap<String, UserMethod> myMethodMap;

	public UserMethodManager() {
		myMethodMap = new HashMap<String, UserMethod>();
	}

	public void add(String key, UserMethod method) {
		myMethodMap.put(key, method);
		setChanged();
		notifyObservers(key);
	}

	public UserMethod getUserMethod(String key) {
		return myMethodMap.get(key);
	}

	/**
	 * Check if the method manager contains already contains the method
	 * 
	 * @param key
	 * @return
	 */
	public boolean contains(String key) {
		return myMethodMap.containsKey(key);
	}
}
