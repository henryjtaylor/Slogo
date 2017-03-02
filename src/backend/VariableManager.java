package backend;

import java.util.HashMap;
import java.util.Observable;

public class VariableManager extends Observable{
	
	private HashMap<String, Variable> myVariableMap;

	public VariableManager() {
		myVariableMap = new HashMap<String, Variable>();
	}
	

	public void addVariable(Variable var) {
		// if already existed, just update the variable
		if (myVariableMap.containsKey(var.getVariableName())) {
			myVariableMap.get(var.getVariableName()).setValue(var.getValue());
		} else {
			myVariableMap.put(var.getVariableName(), var);
		}
		setChanged();
		notifyObservers(var.getVariableName() + " " + var.getValue());
	}
	


	
	public Variable get(String key) {
		Variable value = myVariableMap.get(key);
		while (myVariableMap.containsKey(value)) {
			value = myVariableMap.get(value);
		}
		return value;
	}

	
	public int size(){
		return myVariableMap.size();
	}

}
