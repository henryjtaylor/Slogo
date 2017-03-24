package controller;

import java.util.Observable;
import java.util.Observer;

import backend.UserMethodManager;
import frontend.API.ExternalViewAPI;

/**
 * UserMethodObserver, if the user method manager changes in the Model, View would be noticed
 * @author Yanbo Fang
 *
 */
public class UserMethodObserver implements Observer {

	private UserMethodManager ov = null;
	private ExternalViewAPI myView;

	/**
	 * Constructor for UserMethodObserver
	 * @param UserMethodManager
	 * @param View
	 */
	public UserMethodObserver(UserMethodManager um, ExternalViewAPI view) {
		this.ov = um;
		myView = view;
	}

	public void update(Observable obs, Object obj) {
		if (obs == ov) {
			String methodName = (String) obj;
			myView.updateUMethod(methodName);
		}
	}
}
