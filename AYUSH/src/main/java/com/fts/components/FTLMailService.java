package com.fts.components;

import java.util.HashMap;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author rabindranath.s
 */
@Service
@Scope("prototype")
public class FTLMailService extends Thread
{
    private static final Logger LOG = Logger.getLogger(FTLMailService.class);
    /**
     * mailSender instanceOf JavaMailSenderImpl
     */
    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * mailTemplate instanceOf SimpleMailMessage
     */
    @Autowired
    private SimpleMailMessage mailTemplate;
    @Autowired
    private Configuration freemarkerConfig;

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
    private HashMap<String, Object> data;
    private String ftlFile;

    public void setValues(String fromAddress, String[] toAddresses, String[] ccAddresses, String subject, String ftlFile, HashMap<String, Object> data, Map<String, String> attachments)
    {
        this.fromAddress = fromAddress != null ? fromAddress : mailSender.getUsername();
        this.toAddresses = toAddresses;
        this.ccAddresses = ccAddresses;
        this.subject = subject;
        this.data = data;
        this.ftlFile = ftlFile;
        this.attachments = attachments;
    }

    public void run()
    {
        StringBuffer mailBody = getMailBody(ftlFile, data);
        mailBody.append(getMailFooter("footer.ftl", data));
        if (mailBody != null && !"".equals(mailBody.toString()))
        {
            sendMail(fromAddress, toAddresses, ccAddresses, subject, mailBody, attachments);
        }
    }

    /**
     * This method is used to send mail with attachments
     * 
     * @throws MailException
     */
    private void sendMail(String fromAddress, String[] toAddresses, String[] ccAddresses, String subject, StringBuffer mailBody, Map<String, String> attachments) throws MailException
    {
        StringBuffer mailIds = new StringBuffer("To : ");
        for (int i = 0; i < toAddresses.length; i++)
        {
            mailIds.append("----" + toAddresses[i]);
            LOG.info(toAddresses[i]);
        }

        LOG.debug("CC Address ==>>");
        mailIds.append("\t CC : ");
        for (int i = 0; i < ccAddresses.length; i++)
        {
            mailIds.append(ccAddresses[i]);
            LOG.info(ccAddresses[i]);
        }

        try
        {
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
            helper.setText(mailBody.toString(), true);
            if ((attachments != null))
            {
                addAttachment(helper, attachments);
            }
            mailSender.send(message);
        }
        catch (Exception e)
        {
            LOG.error("Exception while sending mail..." + e.getMessage(), e);
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
                helper.addAttachment(entry.getKey(), new FileDataSource(entry.getValue()));
                // helper.addAttachment(entry.getKey(), new ClassPathResource(entry.getValue()));
            }
        }
        catch (Exception e)
        {
            LOG.error("Exception while adding attachments to mail..." + e.getMessage(), e);
        }
    }

    /**
     * Used to generate html from ftl
     * 
     * @param ftlFile
     * @param mailBody
     */
    public StringBuffer getMailBody(String ftlFile, HashMap<String, Object> data)
    {
        try
        {
            freemarkerConfig.setNumberFormat("0.######");
            Template template = freemarkerConfig.getTemplate(ftlFile);
            return new StringBuffer(FreeMarkerTemplateUtils.processTemplateIntoString(template, data));
        }
        catch (Exception e)
        {
            LOG.error("Error while parsing ftl...", e.getCause());
            LOG.error(e.getCause(), e);
        }
        return new StringBuffer("");
    }

    public StringBuffer getMailFooter(String ftlFile, HashMap<String, Object> data)
    {
        StringBuffer footer = new StringBuffer();
        try
        {
            freemarkerConfig.setNumberFormat("0.######");
            Template template = freemarkerConfig.getTemplate(ftlFile);
            return new StringBuffer(FreeMarkerTemplateUtils.processTemplateIntoString(template, data));
        }
        catch (Exception e)
        {
            LOG.error("Error while parsing ftl...", e.getCause());
            LOG.error(e.getCause(), e);
        }
        return footer;
    }

}
