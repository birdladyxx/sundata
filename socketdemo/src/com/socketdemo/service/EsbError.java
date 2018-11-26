package com.socketdemo.service;

import java.util.ArrayList;
import java.util.List;

public class EsbError {

	List<String> errors = new ArrayList<String>();
	
	public void add(String item) {
		errors.add(item);
	}
	
	public boolean hasErrorMessages() {
		return errors.size() > 0;
	}

	@Override
	public String toString() {
		return "EsbError [errors=" + errors.toString() + "]";
	}
	
	
}
