//package com.example.mycards.messages;
//
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//
//import androidx.core.app.NotificationCompat;
//
//
//import com.fooda.mycards.R;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//public class MessagingService extends FirebaseMessagingService {
//    Context context;
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
////        super.onMessageReceived(remoteMessage);
//
//        if (remoteMessage.getData().size() > 0) {
//            String title = remoteMessage.getNotification().getTitle();
//            String message = remoteMessage.getNotification().getBody();
//
//            Intent Rate_App = new Intent(Intent.ACTION_VIEW);
//            Rate_App.setData( Uri.parse("https://play.google.com/store/apps/developer?id=youraddress"));
//
//            Rate_App.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(Rate_App);
//
//            PendingIntent notification = PendingIntent.getActivity(this, 0, Rate_App, PendingIntent.FLAG_ONE_SHOT);
//
//
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//            builder.setSmallIcon( R.mipmap.ic_launcher);
//            builder.setContentTitle(title);
//            builder.setContentText(message);
//
//            builder.setContentIntent(notification);
//            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
//            builder.setAutoCancel(true);
//
//            NotificationManager mm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            mm.cancel(1);
//            mm.notify(1, builder.build());
//        }
//    }
//}
