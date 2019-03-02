package com.learning.apiyourmart.utils;

import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

import com.learning.apiyourmart.dao.AuthenticationDao;
import com.learning.apiyourmart.dao.ProductInformationDao;
import com.learning.apiyourmart.service.impl.EmailService;

/**
 * Thread that always runs to send email
 * 
 * @author ayushsaxena
 *
 */
public class EmailMidnightJob implements Runnable {

	@Override
	public void run() {
		// Do your daily job here.

		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		if (hour == 0 && minutes >= 0 && minutes <= 15) {

			String message = ProductInformationDao.getProductNamesForBackgroundService();
			List<String> adminEmailList = AuthenticationDao.getAdminEmails();
			System.out.println(message);
			EmailService emailService = new EmailService();
			adminEmailList.forEach(new Consumer<String>() {

				@Override
				public void accept(String admin) {
					// TODO Auto-generated method stub
					 emailService.sendMail(admin, "Product Consideration",message );
				}
			});
			// emailService.sendMail(email, "Product Consideration",message );
		}
	}

}