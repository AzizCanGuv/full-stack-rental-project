package com.comp3102.backend.core.constant;

import com.comp3102.backend.jobApplication.JobApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailConstants {

    public static String bodyExecutor(String toEmail, String uniqueEmail) {

        return "<b>Hey dear " + toEmail + ",</b>,<br><br><i>Here is your password change link</i><br><a href=\"http://frontend-camolug-deployment.s3-website-us-east-1.amazonaws.com/change-password/" + uniqueEmail + "\">Click To Change Password</a>\n";
    }

    public static String emailSubject = "Eski Camolug Otomotiv Passoword Change Request";
    public static String RESERVATION_APPROVED_SUBJECT = "Eski Camolug Otomotiv Reservation Approved!";

    public static String reservationApproved(String userName, String brandName, LocalDateTime startDate, LocalDateTime endDate, Double price) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>Car Reservation Confirmation</title>\n" +
                "  <style>\n" +
                "    /* Reset default styles for better cross-browser compatibility */\n" +
                "    body, p, h1, h2, h3, h4, h5, h6, ul, ol, li {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      background-color: #f5f5f5;\n" +
                "      color: #333333;\n" +
                "      line-height: 1.6;\n" +
                "    }\n" +
                "\n" +
                "    .container {\n" +
                "      max-width: 600px;\n" +
                "      margin: 0 auto;\n" +
                "      padding: 20px;\n" +
                "      background-color: #ffffff;\n" +
                "      border-radius: 6px;\n" +
                "      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n" +
                "    }\n" +
                "\n" +
                "    h1 {\n" +
                "      font-size: 24px;\n" +
                "      font-weight: bold;\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      margin-bottom: 10px;\n" +
                "    }\n" +
                "\n" +
                "    table {\n" +
                "      width: 100%;\n" +
                "      border-collapse: collapse;\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "\n" +
                "    table td, table th {\n" +
                "      padding: 8px;\n" +
                "      border: 1px solid #dddddd;\n" +
                "      text-align: left;\n" +
                "    }\n" +
                "\n" +
                "    table th {\n" +
                "      background-color: #f9f9f9;\n" +
                "      font-weight: bold;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"container\">\n" +
                "    <h1>Car Reservation Approved</h1>\n" +
                "    <p>Dear " + userName + ",</p>\n" +
                "    <p>We are pleased to inform you that your car reservation has been approved. Below are the details:</p>\n" +
                "\n" +
                "    <table>\n" +
                "      <tr>\n" +
                "        <th>Car</th>\n" +
                "        <th>Date Range</th>\n" +
                "        <th>Total Price</th>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td>" + brandName + "</td>\n" +
                "        <td>" + startDate.format(format) + " - " + endDate.format(format) + "</td>\n" +
                "        <td>$" + price + "</td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "\n" +
                "    <p>Please note that the car should be picked up and returned on time. Late returns may incur additional charges.</p>\n" +
                "    <p>Thank you for choosing our car rental service. Should you have any questions or need further assistance, please don't hesitate to contact us.</p>\n" +
                "    <p>Best regards,</p>\n" +
                "    <p>Eski Camoluk Otomotiv</p>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    public static String jobApplicationEmailBodyParser(JobApplication jobApplication) {
        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; padding: 20px; background-color: #f3f4f6; }" +
                "h1 { color: #3b82f6; }" +
                "p { margin-bottom: 10px; color: #333333; }" +
                ".job-title { font-size: 24px; font-weight: bold; margin-bottom: 10px; color: #3b82f6; }" +
                ".job-info p { margin-bottom: 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div>" +
                "<h1>Job Application</h1>" +
                "<div class=\"job-title\">" + jobApplication.getFirstName() + " " + jobApplication.getLastName() + "</div>" +
                "<div class=\"job-info\">" +
                "<p><strong>School Graduated:</strong> " + jobApplication.getSchool() + "</p>" +
                "<p><strong>Contact Email:</strong> " + jobApplication.getEmail() + "</p>" +
                "<p><strong>CV:</strong> " + jobApplication.getJobApplicationFileId() + "</p>" +
                "<p><strong>Birt Date:</strong> " + jobApplication.getBirthDate() + "</p>" +
                "<p><strong>Submission Date:</strong> " + jobApplication.getPostedAt() + "</p>" +
                "<p><strong>GPA:</strong> " + jobApplication.getGpa() + "</p>" +
                "<p><strong>Phone Number:</strong> " + jobApplication.getPhoneNumber() + "</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }




}
