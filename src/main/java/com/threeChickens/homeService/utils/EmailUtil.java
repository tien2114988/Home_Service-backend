package com.threeChickens.homeService.utils;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender mailSender;

//    @Autowired
//    private ExcelUtil excelUtil;

    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    public EmailUtil(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String email, String subject, String text, boolean sendTable) {
        if (sendTable) {
            sendHtmlEmail(email, subject, text);
        } else {
            sendSimpleEmail(email, subject, text);
        }
    }

    private void sendSimpleEmail(String email, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailMessage.setFrom(from);
        mailSender.send(mailMessage);
    }

    private void sendHtmlEmail(String email, String subject, String htmlContent) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setText(htmlContent, true); // true indicates HTML content
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle the exception, maybe log it
            e.printStackTrace();
        }
    }

//    @Async
//    public void sendExcelEmail(String email, String subject, String text, List<SalaryAdvanceRequest> reqs) {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setTo(email);
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setFrom(from);
//            mimeMessageHelper.setText(text, true); // true indicates HTML content
//
//            // Generate the Excel file
//            byte[] excelData = excelUtil.createExcelFile(reqs);
//
//            // Add the Excel file as an attachment
//            DataSource dataSource = new ByteArrayDataSource(excelData, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            mimeMessageHelper.addAttachment("SalaryAdvanceLog.xlsx", dataSource);
//
//            mailSender.send(mimeMessage);
//        } catch (MessagingException | IOException e) {
//            // Handle the exception, maybe log it
//            e.printStackTrace();
//        }
//    }
}
