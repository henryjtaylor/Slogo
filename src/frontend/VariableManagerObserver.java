package frontend;

import java.util.Observer;

import backend.VariableManager;
import frontend.API.ExternalViewAPI;

import java.util.Observable;

public class VariableManagerObserver implements Observer {
	private VariableManager ov = null;
	private ExternalViewAPI myView;

	public VariableManagerObserver(VariableManager vm, ExternalViewAPI view) {
		this.ov = vm;
		myView = view;
	}

	public void update(Observable obs, Object obj) {
		if (obs == ov) {
			String temp = (String) obj;
			String[] arry = temp.split(" ");
			myView.updateVar(arry[0], arry[1]);
		}
	}

}
