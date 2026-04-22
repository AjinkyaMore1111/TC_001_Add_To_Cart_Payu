package Utilities;

import java.io.File;
import java.util.List;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailUtils {

    public static void sendExcelReport(String excelPath, String reportPath, String status) {

        final String senderEmail    = "ajinkyaqa591@gmail.com";
        final String appPassword    = "swdr dgvg nghr qvgd";
        final String recipientEmail = "ajinkyaqa591@gmail.com";

        File excelFile  = new File(excelPath);
        File reportFile = (reportPath != null) ? new File(reportPath) : null;

        if (!excelFile.exists()) {
            System.out.println("Excel file not found, skipping email: " + excelPath);
            return;
        }

        Properties prop = new Properties();
        prop.put("mail.smtp.auth",              "true");
        prop.put("mail.smtp.host",              "smtp.gmail.com");
        prop.put("mail.smtp.port",              "465");
        prop.put("mail.smtp.ssl.enable",        "true");
        prop.put("mail.smtp.ssl.trust",         "smtp.gmail.com");
        prop.put("mail.smtp.connectiontimeout", "10000");
        prop.put("mail.smtp.timeout",           "10000");
        prop.put("mail.smtp.writetimeout",      "10000");

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        try {
            String subject = "Luxepolis Automation Test Product - " + status;

            String emailBody =
                "Hi Team,\n\n"
                + "Please find the Luxepolis Automation Test Product sheet and Test Case Report attached.\n\n"
                + "Test Status : " + status + "\n\n"
                + "Regards,\n"
                + "QA Team";

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(emailBody);

            // Attachment 1: Excel file
            MimeBodyPart excelPart = new MimeBodyPart();
            excelPart.attachFile(excelFile);

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(excelPart);

            // Attachment 2: HTML report (attach only if path was set and file exists)
            if (reportFile != null && reportFile.exists()) {
                MimeBodyPart reportPart = new MimeBodyPart();
                reportPart.attachFile(reportFile);
                multipart.addBodyPart(reportPart);
            } else {
                System.out.println("Report not attached — path is " + (reportPath == null ? "null (ExtentReport not initialized)" : "not found: " + reportPath));
            }
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully with attachments. Status: " + status);

        } catch (Exception e) {
            System.out.println("Warning: Could not send Excel email — " + e.getMessage());
        }
    }

    public static void sendTestReport(
            String reportPath,
            int passCount,
            int failCount,
            int skipCount,
            List<String> failedTests) {

        final String senderEmail    = "ajinkyaqa591@gmail.com";
        final String appPassword    = "uycn pykh qewk tvdx";
        final String recipientEmail = "ajinkyaqa591@gmail.com";

        // SMTP Properties — SSL on port 465
        Properties prop = new Properties();
        prop.put("mail.smtp.auth",            "true");
        prop.put("mail.smtp.host",            "smtp.gmail.com");
        prop.put("mail.smtp.port",            "465");
        prop.put("mail.smtp.ssl.enable",      "true");
        prop.put("mail.smtp.ssl.trust",       "smtp.gmail.com");
        prop.put("mail.smtp.connectiontimeout", "10000"); // 10 seconds
        prop.put("mail.smtp.timeout",           "10000"); // 10 seconds
        prop.put("mail.smtp.writetimeout",      "10000"); // 10 seconds

        // Check report exists before even connecting
        File reportFile = new File(reportPath);
        if (!reportFile.exists()) {
            System.out.println("Report not found, skipping email: " + reportPath);
            return;
        }

        // Create Session
        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        try {
            // Set subject based on pass/fail
            String subject = failCount > 0
                ? "Luxepolis Test Report - " + failCount + " Test(s) FAILED"
                : "Luxepolis Test Report - All Tests PASSED";

            // Build failure details for email body
            StringBuilder failDetails = new StringBuilder();
            if (failCount > 0) {
                failDetails.append("Failed Test Cases:\n");
                failDetails.append("-------------------------\n");
                for (String failed : failedTests) {
                    failDetails.append(failed).append("\n");
                }
                failDetails.append("-------------------------\n\n");
            }

            // Email body with summary
            String emailBody =
                "Hi Team,\n\n"
                + "Please find the Luxepolis Test Execution Report below:\n\n"
                + "Test Summary:\n"
                + "-------------------------\n"
                + "Passed  : " + passCount + "\n"
                + "Failed  : " + failCount + "\n"
                + "Skipped : " + skipCount + "\n"
                + "-------------------------\n\n"
                + failDetails.toString()
                + "Please find the detailed report attached.\n\n"
                + "Regards,\n"
                + "QA Team";

            // Create Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipientEmail)
            );
            message.setSubject(subject);

            // Email Body Part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(emailBody);

            // Attachment Part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(reportFile);

            // Combine
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            // Send
            Transport.send(message);
            System.out.println("Email Sent Successfully: " + subject);

        } catch (Exception e) {
            System.out.println("Warning: Could not send email report — " + e.getMessage());
            System.out.println("(Check network/firewall or verify Gmail App Password)");
        }
    }

}
