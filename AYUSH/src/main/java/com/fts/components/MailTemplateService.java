package com.fts.components;


import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
@Scope("prototype")
public class MailTemplateService extends Thread
{
    private static final Logger LOG = Logger.getLogger(MailTemplateService.class);
   
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private SimpleMailMessage mailTemplate;

    @Autowired
    private Configuration freemarkerConfiguration;

    public void setMailSender(JavaMailSenderImpl mailSender)
    {
        this.mailSender = mailSender;
    }

    public void setMailTemplate(SimpleMailMessage mailTemplate)
    {
        this.mailTemplate = mailTemplate;
    }

    private String fromAddress;
    private String[] toAddresses;
    private String[] ccAddresses;
    private String subject;
    private Map<String, String> attachments;
    private HashMap<String, Object> placeholders;
    private String mailContent;

    public void setValues(String fromAddress, String[] toAddresses, String[] ccAddresses, String subject,
             String mailContent, HashMap<String, Object> placeholders, Map<String, String> attachments)
    {
        this.fromAddress = fromAddress != null ? fromAddress : mailSender.getUsername();
        this.toAddresses = toAddresses;
        this.ccAddresses = ccAddresses;
        this.subject = subject;
        this.placeholders = placeholders;
        this.attachments = attachments;
        this.mailContent = mailContent;

    }

    public void run()
    {
        StringBuffer htmlBody = getMailBody(mailContent, placeholders);
        sendMail(fromAddress, toAddresses, ccAddresses, subject, htmlBody, attachments);
    }

    /**
     * This method is used to send mail with attachments
     * 
     * @throws MailException
     */
    public void sendMail(String fromAddress, String[] toAddresses, String[] ccAddresses, String subject,
            StringBuffer content, Map<String, String> attachments) throws MailException
    {
        StringBuffer mailIds = new StringBuffer("To : ");
        for (int i = 0; i < toAddresses.length; i++)
        {
            LOG.info("MMMMMM------>"+toAddresses[i]);
        	mailIds.append("----" + toAddresses[i]);
        }
        mailIds.append("\t CC : ");
        for (int i = 0; i < ccAddresses.length; i++)
        {
            mailIds.append(ccAddresses[i]);
        }

        try
        {
        	 LOG.info("NNNNNNNN------>"+fromAddress);
        	mailTemplate.setTo(toAddresses);
            mailTemplate.setCc(ccAddresses);
            mailTemplate.setFrom(fromAddress);
            SimpleMailMessage msg = new SimpleMailMessage(this.mailTemplate);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(msg.getFrom());
            helper.setTo(msg.getTo());
            helper.setCc(msg.getCc());
            helper.setSubject(subject);
            helper.setText(content.toString(), true);
            if ((attachments != null))
            {
                addAttachment(helper, attachments);
            }
            mailSender.send(message);
        }
        catch (Exception ex)
        {
            LOG.error("Exception while sending mail..." + ex.getCause(), ex);
        }
    }

    /**
     * Used to add attachments to the mail
     * 
     * @param helper
     * @param attachment
     */
    public void addAttachment(MimeMessageHelper helper, Map<String, String> attachments)
    {
        try
        {
            for (Map.Entry<String, String> entry : attachments.entrySet())
            {
                helper.addAttachment(entry.getKey(), new ClassPathResource(entry.getValue()));
            }
        }
        catch (Exception e)
        {
            LOG.error("Exception while adding attachments to mail..." + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Used to generate html from ftl
     * 
     * @param ftlFile
     * @param mailBody
     */
    public StringBuffer getMailBody(String mailContent, HashMap<String, Object> placeholders) {
        try {
            freemarkerConfiguration.setNumberFormat("0.######");
            Template template = new Template("EMAIL_TEMPLATE", new StringReader(mailContent), freemarkerConfiguration);

            return new StringBuffer(FreeMarkerTemplateUtils.processTemplateIntoString(template, placeholders));
        } catch (Exception e) {
            LOG.error("Error while creating Mail Body of New User", e.getCause());
            LOG.error(e.getCause(), e);
        }
        return new StringBuffer("");
    }

}
