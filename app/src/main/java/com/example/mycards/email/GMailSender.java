package com.example.mycards.email;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.security.Security;
import java.util.Properties;

public class GMailSender  {
    private Activity activity;

    public GMailSender(Activity activity) {
        this.activity = activity;
    }

    public void sendMessage(String recipient, String title, String body) {
        String[] recipients = { recipient };
        SendEmailAsyncTask email = new SendEmailAsyncTask();

        email.m = new Mail("sudanmart2020@gmail.com", "ynvhvqrdqwuevhog");
        email.m.set_from("sudanmart2020@gmail.com");
        email.m.setBody(body);
        email.m.set_to(recipients);
        email.m.set_subject(title);
        email.execute();
       }
    class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Mail m;


        public SendEmailAsyncTask() {}

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if (m.send()) {
                } else {
                    Log.e("Email send","Email failed to send.");
                }

                return true;
            } catch (AuthenticationFailedException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
                e.printStackTrace();
                Log.e("Authentication","Authentication failed.");
                return false;
            } catch (MessagingException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Email failed");
                e.printStackTrace();
                Log.e("MessagingException","Email failed to send.");
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception","Unexpected error occured.");
                return false;
            }
        }
    }

}
