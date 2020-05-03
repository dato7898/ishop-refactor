package net.devstudy.ishop.service;

import net.devstudy.ishop.entity.Order;

public interface NotificationService {
	void sendNewOrderCreatedNotification(String notificationAddress, Order order);
}
