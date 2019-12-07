package org.roza.services.impl;

import org.roza.domain.models.services.VerificationTokenServiceModel;
import org.roza.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String username;
    private static final String EMAIL_CONFIRMATION_TEMPLATE_NAME = "users/confirmation-email";
    private static final String REGISTRATION_CONFIRM_URL = "/users/confirmregistration/";
    private static final String EMAIL_CONFIRMATION_SUBJECT = "Confirmation email";

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender,
                            TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendVerificationEmail(VerificationTokenServiceModel tokenServiceModel, String domain)
            throws MessagingException {
        Context context = new Context();

        context.setVariable("confirmationLink",
                domain + REGISTRATION_CONFIRM_URL + tokenServiceModel.getId());

        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(EMAIL_CONFIRMATION_SUBJECT);
        message.setFrom(this.username);
        message.setTo(tokenServiceModel.getUser().getEmail());

        String htmlContent = this.templateEngine.process(EMAIL_CONFIRMATION_TEMPLATE_NAME, context);
        message.setText(htmlContent, true);

        //this.javaMailSender.send(mimeMessage);
    }
}
