package cn.taoys.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailMsgSend {

	private String toMail;
	private String fromMail;
	private String stmpHost;
	private boolean isDebug;
	private String fromMailPasswd;
	
	public MailMsgSend(String toMail, String fromMail, String stmpHost,
			boolean isDebug, String fromMailPasswd) {
		this.toMail = toMail;
		this.fromMail = fromMail;
		this.stmpHost = stmpHost;
		this.isDebug = isDebug;
		this.fromMailPasswd = fromMailPasswd;
	}

	public void msgSend(String subject, String msgText){
		Properties props = new Properties();
		props.put("mail.smtp.host", stmpHost);
    	props.put("mail.smtp.auth", true);
    	if (isDebug) props.put("mail.debug", String.valueOf(isDebug));
    	
    	MailAuthenticator mailAuth = new MailAuthenticator(fromMail, fromMailPasswd);
    	Session session = Session.getInstance(props, mailAuth);
    	session.setDebug(isDebug);
    	
    	try {
    	    // create a message
    	    MimeMessage msg = new MimeMessage(session);
    	    msg.setFrom(new InternetAddress(fromMail));
    	    InternetAddress[] address = {new InternetAddress(toMail)};
    	    msg.setRecipients(Message.RecipientType.TO, address);
    	    msg.setSubject(subject);
    	    msg.setSentDate(new Date());
    	    // If the desired charset is known, you can use
    	    // setText(text, charset)
    	    msg.setText(msgText);
    	    
    	    Transport.send(msg);
    	} catch (MessagingException mex) {
    	    System.out.println("\n--Exception handling in msgsendsample.java");

    	    mex.printStackTrace();
    	    System.out.println();
    	    Exception ex = mex;
    	    do {
    		if (ex instanceof SendFailedException) {
    		    SendFailedException sfex = (SendFailedException)ex;
    		    Address[] invalid = sfex.getInvalidAddresses();
    		    if (invalid != null) {
    			System.out.println("    ** Invalid Addresses");
    			for (int i = 0; i < invalid.length; i++) 
    			    System.out.println("         " + invalid[i]);
    		    }
    		    Address[] validUnsent = sfex.getValidUnsentAddresses();
    		    if (validUnsent != null) {
    			System.out.println("    ** ValidUnsent Addresses");
    			for (int i = 0; i < validUnsent.length; i++) 
    			    System.out.println("         "+validUnsent[i]);
    		    }
    		    Address[] validSent = sfex.getValidSentAddresses();
    		    if (validSent != null) {
    			System.out.println("    ** ValidSent Addresses");
    			for (int i = 0; i < validSent.length; i++) 
    			    System.out.println("         "+validSent[i]);
    		    }
    		}
    		System.out.println();
    		if (ex instanceof MessagingException)
    		    ex = ((MessagingException)ex).getNextException();
    		else
    		    ex = null;
    	    } while (ex != null);
    	}
	}

	
	public static void main(String[] args) {
		System.out.println(String.valueOf(false));
		MailMsgSend mms = new MailMsgSend("wu_qingjian@126.com", "crazywu@126.com", "smtp.126.com", false, "iwnet0214");
		mms.msgSend("test", "试试");
	}
}
