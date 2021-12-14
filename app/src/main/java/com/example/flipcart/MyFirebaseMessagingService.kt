package com.example.flipcart

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.flipcart.activity.Shopping_Activity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notificaton_channel"
const val channelName = "com.example.flipcart"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService:FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        //generate the notification
        //attach the notification created with the custom layout
        //show notification

        if(remoteMessage.getNotification() != null){
            generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
        }
        super.onMessageReceived(remoteMessage)
    }

    fun getRemoteView(title: String,message: String):RemoteViews {
        val remoteViews = RemoteViews("com.example.flipcart",R.layout.notification)
        remoteViews.setTextViewText(R.id.title,title)
        remoteViews.setTextViewText(R.id.message,message)
        remoteViews.setImageViewResource(R.id.applogo,R.drawable.applogo)

        return remoteViews
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun generateNotification(title:String, message:String){
        val intent = Intent(this,Shopping_Activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)

        //channelId, chanelname
        var builder:NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.applogo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0,builder.build())

    }
}