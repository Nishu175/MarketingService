package org.example.marketingservice.dispatchers;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.example.marketingservice.models.DispatchResponse;
import org.example.marketingservice.callbacks.CallBackProcessor;
import org.example.marketingservice.models.enums.Status;
import org.example.marketingservice.models.providers.EmailProvider;
import org.example.marketingservice.models.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SendbirdClient {

    @Autowired
    private CallBackProcessor callBackProcessor;

    private static final int BATCH_SIZE = 10;

    public void send(List<String> recipients, List<String> ccList, List<String> bccList, Message message, EmailProvider provider,Long campaignId)  {
        try {
            // remove duplicate emails
            Set<String> recipientSet = new HashSet<>(recipients);
            List<String> finalRecipients = new ArrayList<>();
            for (String recipient : recipientSet) {
                finalRecipients.add(recipient);
                if (finalRecipients.size() == BATCH_SIZE) {
                    batchSend(finalRecipients, ccList, bccList, message, provider, campaignId);
                    finalRecipients.clear();
                }
            }

            if (!finalRecipients.isEmpty()) {
                batchSend(finalRecipients, ccList, bccList, message, provider, campaignId);
            }

            DispatchResponse dispatchResponse = new DispatchResponse();
            dispatchResponse.setCampaignId(campaignId);
            dispatchResponse.setStatus(Status.FINISHED);
            callBackProcessor.submitDispatchResponse(dispatchResponse);
        }catch (Exception e) {
            // currently marking this as failed but we can ret-ry only.
            e.printStackTrace();
            System.out.println("send failed for campaign " + campaignId );
            DispatchResponse dispatchResponse = new DispatchResponse();
            dispatchResponse.setCampaignId(campaignId);
            dispatchResponse.setStatus(Status.FAILED);
            dispatchResponse.setDispatchSummary("Send failed for campaign " + campaignId);
            callBackProcessor.submitDispatchResponse(dispatchResponse);
        }
    }

    private void batchSend(List<String> recipients,List<String> ccList,List<String> bccList, Message message, EmailProvider provider,Long campaignId) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", provider.getHost());
        properties.put("mail.smtp.port", provider.getPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(provider.getUserName(), provider.getPassword());
            }
        };

        Session session = Session.getInstance(properties, auth);

        // Create a new email message
        MimeMessage msg = new MimeMessage(session);

        if (ccList != null && !ccList.isEmpty()) {
            msg.setRecipients(jakarta.mail.Message.RecipientType.CC, InternetAddress.parse(String.join(",", ccList)));
        }

        if (bccList != null && !bccList.isEmpty()) {
            msg.setRecipients(jakarta.mail.Message.RecipientType.BCC, InternetAddress.parse(String.join(",", bccList)));
        }

        msg.setFrom(new InternetAddress(provider.getUserName()));
        InternetAddress[] toAddresses = new InternetAddress[recipients.size()];
        for (int i = 0; i < recipients.size(); i++) {
            toAddresses[i] = new InternetAddress(recipients.get(i));
        }
        msg.setRecipients(jakarta.mail.Message.RecipientType.TO, toAddresses);
        msg.setSubject(message.getSubject());
        msg.setSentDate(new java.util.Date());
        msg.setText(message.getBody());

        // Send the email
        //Transport.send(msg);

        System.out.println("Sending message " + message.toString() + " to " + recipients + " campaignId " + campaignId);


    }
}
