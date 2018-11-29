package com.socketdemo.service;

public interface SocketServiceAction {

	public static final String BIZSUM_FORMAT = "#0.00";
	public static final String RATIO_FORMAT = "#0.00####";
	
	public abstract String execute() throws Exception;
}
