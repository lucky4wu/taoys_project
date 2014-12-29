package cn.taoys.service;

/** 登陆异常处理 */
public class NameOrPwdException extends Exception{
	private static final long serialVersionUID = 2017324530530634369L;
	public NameOrPwdException(){
		
	}
	public NameOrPwdException(String message){
		super(message);
	}
	public NameOrPwdException(Throwable cause){
		super(cause);
	}
	public NameOrPwdException(String message, Throwable cause){
		super(message, cause);
	}
}
