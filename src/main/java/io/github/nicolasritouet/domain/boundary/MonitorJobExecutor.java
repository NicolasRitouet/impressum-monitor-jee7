package io.github.nicolasritouet.domain.boundary;

import io.github.nicolasritouet.domain.control.MonitorJobRepository;
import io.github.nicolasritouet.domain.entity.MonitorJob;
import io.github.nicolasritouet.util.annotations.Property;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

@Stateless @Default
public class MonitorJobExecutor {


    @Inject
    private transient Logger logger;

    @Inject @Property
    private String email;

    @Inject @Property
    private String smtpHostServer;

    @Inject
    MonitorJobRepository monitorJobRepository;


    @Schedule(hour="*", persistent=false)
    public void startCheckJobs() {
        logger.info("Start check job: " + new Date());
        String body;
        List<MonitorJob> listMonitorJob = monitorJobRepository.getAllMonitorJobs();
        for (MonitorJob monitorJob : listMonitorJob) {
            logger.info("Checking " + monitorJob.getUrl());
            body = getBody(monitorJob.getUrl());
            if (body != null && !body.equals(monitorJob.getBody())) {
                logger.info("Change detected for url: " + monitorJob.getUrl());
                monitorJob.setLastChange(new Date());
                monitorJob.setBody(body);
                sendEmail(monitorJob);
            }
            monitorJob.setLastCheck(new Date());
            monitorJobRepository.updateMonitorJob(monitorJob);
        }
    }


    private String getBodyWithJsoup(String urlString) {
        Document doc;
        try {
            doc = Jsoup.connect(urlString).get();
            return doc.body().html();
        } catch (Exception e) {
            logger.info("Error while reading the url, ignoring ...");
            return null;
        }
    }


    protected String getBody(String urlString) {
        StringBuilder body = new StringBuilder();
        try {
            URL game = new URL(urlString);
            URLConnection connection = game.openConnection();
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                body.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body.toString();
    }

    protected void sendEmail(MonitorJob monitorJob) {
        logger.info("Send an email to " + email + " for url " + monitorJob.getUrl());
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHostServer);
        Session session = Session.getInstance(props);

        MimeMessage message = new MimeMessage(session);
        try {

            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");
            message.setFrom(new InternetAddress("email@host.com", "Yourname"));

            message.setReplyTo(InternetAddress.parse("email@host.com", false));
            message.setSubject("Change detected on " + monitorJob.getUrl(), "UTF-8");
            message.setText("We detected a change on the page " + monitorJob.getUrl(), "UTF-8");
            message.setSentDate(new Date());
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));

            Transport.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
