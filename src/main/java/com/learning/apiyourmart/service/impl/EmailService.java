package com.learning.apiyourmart.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Service class that sends email
 * 
 * @author ayushsaxena
 *
 */
public class EmailService {

	/**
	 * Method to send email
	 * 
	 * @param email
	 * @param subject
	 * @param msg
	 * @return
	 */
	public boolean sendMail(String email, String subject, String msg) {

		String username = "";
		String password = "";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(msg);
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

}