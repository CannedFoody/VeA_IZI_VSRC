package eu.virac.vea_izi_vsrc.service;

public interface INotificationService {

	void sendNotification(String to, String subject, String body);
}
