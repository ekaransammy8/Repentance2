package www.digitalexperts.church_traker.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import www.digitalexperts.church_traker.Index;
import www.digitalexperts.church_traker.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    final String Tag=this.getClass().getName();
    private String type="";
    private String mt="";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
    /*    super.onMessageReceived(remoteMessage);*/

        Log.d(Tag, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(Tag, "Message data payload: " + remoteMessage.getData());
            type="json";
            sendNotification(remoteMessage.getData().toString());
           /* if ( true) {
                scheduleJob();
            } else {
                handleNow();
            }*/

        }
        if (remoteMessage.getNotification() != null) {
            Log.d(Tag, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            type="message";
            mt=remoteMessage.getNotification().getTitle();
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        Log.d(Tag, "Refreshed token: " + s);
        sendRegistrationToServer(s);
    }

    /* private void scheduleJob() {
            // [START dispatch_job]
            OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .build();
            WorkManager.getInstance().beginWith(work).enqueue();
            // [END dispatch_job]
        }*/
   /*private void handleNow() {
       Log.d(Tag, "Short lived task is done.");
   }*/
   private void sendRegistrationToServer(String token) {
      /*TODO: Implement this method to send token to your app server.*/
       Log.d(Tag, "Toserver: " + token);
   }
    private void sendNotification(String messageBody) {
       String id="";
       String message="";
       String title="";
            if(type.equals("json")){
                try {
                    JSONObject jsonObject = new JSONObject(messageBody);
                    id=jsonObject.getString("id");
                    message=jsonObject.getString("message");
                    title=jsonObject.getString("title");
                    mt=title;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else if(type.equals("message")){
                message=messageBody;

            }


        Intent intent = new Intent(this, Index.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Bitmap myicon= BitmapFactory.decodeResource(getResources(), R.drawable.dove);
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.dove)
                        .setContentTitle(mt)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message)
                                .setBigContentTitle(title)
                        )
                        .setAutoCancel(true)
                        .setLargeIcon(myicon)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
