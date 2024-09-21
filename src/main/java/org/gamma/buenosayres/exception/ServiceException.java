package org.gamma.buenosayres.exception;

public class ServiceException extends Exception {

	private final int code;
	public ServiceException(String message, int code)
	{
		super(message);
		this.code = code;
	}
	public int getCode()
	{
		return code;
	}
}
