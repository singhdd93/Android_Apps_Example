package com.db.alternatinglistview;

import java.util.HashMap;

public class Car extends HashMap<String, String> {
	private static final long serialVersionUID = 12872473L;
	public String make;
	public String model;

	public static String KEY_MAKE = "make";
	public static String KEY_MODEL = "model";

	public Car(String make, String model) {
		this.make = make;
		this.model = model;
	}

	@Override
	public String get(Object k) {
		String key = (String) k;
		if (KEY_MAKE.equals(key))
			return make;
		else if (KEY_MODEL.equals(key))
			return model;
		return null;
	}
}
