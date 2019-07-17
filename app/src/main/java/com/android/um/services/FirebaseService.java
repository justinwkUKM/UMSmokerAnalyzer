package com.android.um.services;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.um.PresenterInjector;
import com.android.um.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService  implements FirebaseServiceContract.Service{

  FirebaseServiceContract.Presenter mPresenter;

  @Override
  public void onCreate() {
    super.onCreate();
    PresenterInjector.injectFirebaseServicePresenter(this);
  }

  @Override
  public void setPresenter(FirebaseServiceContract.Presenter presenter) {
    this.mPresenter=presenter;
  }

  @Override
  public Context getContext() {
    return null;
  }

  @Override
  public void showLoading() {

  }

  @Override
  public void hideLoading() {

  }

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {

    // TODO(developer): Handle FCM messages here.
    // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
    //Log.d(TAG, "From: " + remoteMessage.getFrom());

    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
      Log.d("firebasemessage1", "Message data payload: " + remoteMessage.getData());

      if (/* Check if data needs to be processed by long running job */ true) {
        // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
        //    scheduleJob();
      } else {
        // Handle message within 10 seconds
        //  handleNow();
      }

    }

    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
      Log.d("firebasemessage2", "Message Notification Body: " + remoteMessage.getNotification().getBody());

    }

    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
            .setContentTitle(remoteMessage.getNotification().getTitle())
            .setContentText(remoteMessage.getNotification().getBody())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(new NotificationCompat.BigTextStyle())
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(0, notificationBuilder.build());


    // Also if you intend on generating your own notifications as a result of a received FCM
    // message, here is where that should be initiated. See sendNotification method below.


  }

  @Override
  public void onDeletedMessages() {
    super.onDeletedMessages();
  }

  @Override
  public void onMessageSent(String s) {
    super.onMessageSent(s);
  }

  @Override
  public void onSendError(String s, Exception e) {
    super.onSendError(s, e);
  }


  @Override
  public void onNewToken(String s) {
    super.onNewToken(s);

    // If you want to send messages to this application instance or
    // manage this apps subscriptions on the server side, send the
    // Instance ID token to your app server.
    //sendRegistrationToServer(token);

  }
}