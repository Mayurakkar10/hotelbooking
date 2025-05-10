package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public boolean sendBookingConfirmation(String email, String name, String hotelName, String roomType, int guests,
			String checkIn, String checkOut, double price) {
		try {
// Compose and send the email
			SimpleMailMessage message = new SimpleMailMessage();
			 message.setTo(email);
		        message.setSubject("Your Hotel Booking is Confirmed! 🏨");

		        String body = "Hi " + name + ",\n\n" +
		                      "Thank you for booking with us!\n\n" +
		                      "We’re happy to confirm your reservation at \"" + hotelName + "\". Here are your booking details:\n\n" +
		                      "🔹 Room Type: " + roomType + "\n" +
		                      "🔹 Number of Guests: " + guests + "\n" +
		                      "🔹 Check-In Date: " + checkIn + "\n" +
		                      "🔹 Check-Out Date: " + checkOut + "\n" +
		                      "🔹 Total Price: ₹" + price + "\n\n" +
		                      "We look forward to welcoming you and hope you have a relaxing and enjoyable stay.\n\n" +
		                      "If you have any questions, feel free to contact us.\n\n" +
		                      "Best regards,\n" +
		                      "The BooknStay Team";

		        message.setText(body);
		        mailSender.send(message);
		        System.out.println("Email sent successfully");
		        return true;
		} catch (Exception e) {
			System.err.println("Email sending failed: " + e.getMessage());
			return false;
		}
	}

}
