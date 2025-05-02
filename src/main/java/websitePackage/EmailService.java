package websitePackage;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.security.SecureRandom;
import java.util.Random;

public class EmailService{
    private static String username="faribat975@gmail.com";
    private static String password="wordpass$password";
    private static final int OTP_LENGTH=6;

    // Generate OTP
    public static String generateOTP(){
        Random random=new SecureRandom();
        StringBuilder otp=new StringBuilder();
        for (int i=0;i< OTP_LENGTH;i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    // Send Email
    public static void sendEmail(String toEmail,String subject,String message){
        Properties props=new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");

        Session session=Session.getInstance(props,
                new Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(username,password);
                    }
                });

        try {
            Message mimeMessage=new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toEmail));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            System.out.println("Email sent successfully");

        } catch (MessagingException e){
            e.printStackTrace();
        }
    }

    // Generate OTP and Send Email
    public static String generateAndSendOTP(String toEmail) {
        String otp=generateOTP();
        String subject="Your OTP Code";
        String message="Your OTP is: " + otp;
        sendEmail(toEmail,subject,message);
        return otp;
    }
}


