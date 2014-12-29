package cn.taoys.service;

public class NetworkAddressException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5379379451420052821L;
	public NetworkAddressException(){
		
	}
	public NetworkAddressException(String message){
		super(message);
	}
	public NetworkAddressException(Throwable cause){
		super(cause);
	}
	public NetworkAddressException(String message, Throwable cause){
		super(message, cause);
	}
}
