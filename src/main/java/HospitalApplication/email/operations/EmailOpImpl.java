package HospitalApplication.email.operations;

import HospitalApplication.email.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by GUCIA on 2014-11-30.
 */

@Repository("emailOp")
public class EmailOpImpl implements EmailOp {

    @Autowired
    Email email;

    private Message messages[];
    private Folder inbox;
    private Store store;

    @Override
    public boolean checkEmail(Email email) {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol","imaps");
        String path = null;
        try {
            Session session = Session.getInstance(props, null);
            store = session.getStore();
            store.connect(email.getHost(), email.getLogin(), email.getPassword());
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            messages = inbox.search(new FlagTerm(new Flags(
                    Flags.Flag.SEEN), false));

            if(messages.length > 0) {
                return true;
            }
            else{
                inbox.close(false);
                store.close();
                return false;
            }

        }catch(Exception mex){
            mex.printStackTrace();
        }

        return false;
    }

    @Override
    public  ArrayList<String> downloadExcelFile() {
        ArrayList<String> paths = new ArrayList<String>();
        try {
        for (Message msg : messages) {

                msg.setFlag(Flags.Flag.SEEN, true);
                String contentType = msg.getContentType();
                String attachFiles = "";

                if (contentType.contains("multipart")) {
                    Multipart multipart = (Multipart) msg.getContent();
                    int numberOfParts = multipart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            System.out.println("jest zalacznik");
                            //zalacznik pls
                            String fileName = part.getFileName();
                            attachFiles += fileName + ", ";
                            paths.add("E:\\" + File.separator + fileName);
                            part.saveFile(paths.get(paths.size()-1));
                        }
                        if (attachFiles.length() > 1) {
                            attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                        }
                    }
                }
        }
            inbox.close(false);
            store.close();
        }catch(Exception mex){
            mex.printStackTrace();
        }
        return paths;
    }

    @Override
    public ArrayList<String> reloadDatabase() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol","imaps");
        String path = null;
        ArrayList<String> paths = new ArrayList<String>();
        try {
            Session session = Session.getInstance(props, null);
            store = session.getStore();
            store.connect(email.getHost(), email.getLogin(), email.getPassword());
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            messages = inbox.getMessages();

            //ArrayList<String> paths = new ArrayList<String>();
                for (Message msg : messages) {

                    msg.setFlag(Flags.Flag.SEEN, true);
                    String contentType = msg.getContentType();
                    String attachFiles = "";

                    if (contentType.contains("multipart")) {
                        Multipart multipart = (Multipart) msg.getContent();
                        int numberOfParts = multipart.getCount();
                        for (int partCount = 0; partCount < numberOfParts; partCount++) {
                            MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(partCount);
                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                System.out.println("jest zalacznik");
                                //zalacznik pls
                                String fileName = part.getFileName();
                                attachFiles += fileName + ", ";
                                paths.add("E:\\" + File.separator + fileName);
                                part.saveFile(paths.get(paths.size()-1));
                            }
                            if (attachFiles.length() > 1) {
                                attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                            }
                        }
                    }
                }
        }catch(Exception mex){
            mex.printStackTrace();
        }
        finally{
            try {
                inbox.close(false);
                store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return paths;
        }
    }
}
