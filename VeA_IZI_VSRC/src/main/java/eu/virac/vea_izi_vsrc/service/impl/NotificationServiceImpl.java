package eu.virac.vea_izi_vsrc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import eu.virac.vea_izi_vsrc.service.INotificationService;

@Service
public class NotificationServiceImpl implements INotificationService {

	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

	private final JavaMailSender mailSender;

	@Value("${app.mail.from}")
	private String from;

	public NotificationServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendNotification(String to, String subject, String body) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(from);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);

			mailSender.send(message);
			log.info("[EMAIL] Sent to {} | subject: {}", to, subject);
		} catch (Exception e) {
			log.error("[EMAIL] Failed to send to {}: {}", to, e.getMessage());
		}
	}
}
