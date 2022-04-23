package com.dev.nawwa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	public void sendNewPasswordEmail(String firstname, String to, String password)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom(from);
		message.setSubject("Support portal password");
		message.setText("Hello " + firstname + "\n\n Your password is " + password + "\n\n" + "The Support Team\n Neoquant Pvt Ltd.");

		mailSender.send(message);
	}
}
