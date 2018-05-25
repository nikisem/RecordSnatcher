package fi.nikisem.recordsnatcher;

import com.sun.mail.smtp.SMTPMessage;

import java.time.*;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import static java.time.LocalDateTime.now;


public class Mailer {


    private String started;
    private ArrayList<Item> prevList = new ArrayList<>();

    public void runMailer() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ZoneOffset offset = OffsetDateTime.now().getOffset();
                String offsetString = offset.toString();
                LocalDateTime now = now(ZoneId.of("UTC"));
                OffsetDateTime timeUtc = now.atOffset(ZoneOffset.UTC);
                OffsetDateTime offsetTime = timeUtc.withOffsetSameInstant(offset);
                now = offsetTime.toLocalDateTime();
                LocalDateTime timeframe = now.minusMinutes(30);
                started = now.toString();
                ArrayList<Item> results = getResults();
                System.out.println("Results: " + results.size());

                if (results.size() > 0) {

                    sendMail(results);

                }

            }

        };

        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 600 * 1000;

        timer.scheduleAtFixedRate(task, delay,
                intevalPeriod);

    }


    public ArrayList<Item> getResults() {

        String[] keywords = {"KEYWORD 1", "KEYWORD 2", "KEYWORD 3", "ETC..."};
        String keyword = "MAIN KEYWORD";

        ZoneOffset offset = OffsetDateTime.now().getOffset();
        String offsetString = offset.toString();
        LocalDateTime now = now(ZoneId.of("UTC"));
        OffsetDateTime timeUtc = now.atOffset(ZoneOffset.UTC);
        OffsetDateTime offsetTime = timeUtc.withOffsetSameInstant(offset);
        now = offsetTime.toLocalDateTime();
        LocalDateTime timeframe = now.minusHours(5);

        ArrayList<Item> list = new ArrayList();

        ArrayList<Item> eBay = new EBay().fetchData(keyword, offsetString);

        for (int i = 0; i < keywords.length; i++) {

            for (int j = 0; j < eBay.size(); j++) {

                if (eBay.get(j).getTimeStamp().isAfter(timeframe) && eBay.get(j).getTitle().toLowerCase().contains(keywords[i])) {

                    list.add(eBay.get(j));
                    System.out.println("added");

                    for (int k = 0; k < prevList.size(); k++) {

                        if (prevList.get(k).getItemID().equals(eBay.get(j).getItemID())) {
                            list.remove(eBay.get(j));
                            System.out.println("removed");
                        }

                    }

                }

            }

        }

        ArrayList<Item> huuto = new Huuto().fetchData(keyword, offsetString);

        for (int i = 0; i < keywords.length; i++) {

            for (int j = 0; j < huuto.size(); j++) {

                if (huuto.get(j).getTimeStamp().isAfter(timeframe) && huuto.get(j).getTitle().toLowerCase().contains(keywords[i])) {

                    list.add(huuto.get(j));
                    System.out.println("added");

                    for (int k = 0; k < prevList.size(); k++) {

                        if (prevList.get(k).getTitle().equals(huuto.get(j).getTitle())) {
                            list.remove(huuto.get(j));
                            System.out.println("removed");
                        }

                    }

                }

            }

        }

        Collections.sort(list, new ItemComparator());

        prevList.addAll(list);
        System.out.println("PREVLIST: " + prevList.size());

        for (Iterator<Item> it = prevList.iterator(); it.hasNext(); ) {

            if (it.next().getTimeStamp().isBefore(timeframe)) {

                it.remove();
                System.out.println("Removed expired item");

            }

        }

        return list;

    }


    public void sendMail(ArrayList<Item> results) {

        String mailSubject;
        if (results.size() == 1) {

            mailSubject = "1 item found";

        } else {

            mailSubject = results.size() + " items found";

        }

        String mailText = "";

        for (int i = 0; i < results.size(); i++) {

            mailText += results.get(i).getTimeStampString() + "\n" + results.get(i).getTitle() + "\n" + results.get(i).getLink() + "\n" + "\n";

        }

        mailText = mailText + "\n" + "\n" + started;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.emailaddress.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "805");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("app@emailaddress.com", "password");
            }
        });

        try {

            SMTPMessage message = new SMTPMessage(session);
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("user@emailaddress.com"));

            message.setSubject(mailSubject);
            message.setText(mailText);
            message.setNotifyOptions(SMTPMessage.NOTIFY_SUCCESS);
            int returnOption = message.getReturnOption();
            Transport.send(message);

            System.out.println("Mail sent.");

        } catch (MessagingException e) {

            System.out.println("Mail NOT sent.");
            throw new RuntimeException(e);

        }

    }

}