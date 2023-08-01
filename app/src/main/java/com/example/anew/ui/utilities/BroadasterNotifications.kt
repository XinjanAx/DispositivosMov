package com.example.anew.ui.utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.anew.R
import com.example.anew.ui.activities.CameraActivity


class BroadasterNotifications : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent?) {



        val CHANNEL = "Notificaciones"

        val intent = Intent(context, CameraActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )


        val noti = NotificationCompat.Builder(context, CHANNEL)
        //cuerpo de Notificacion
        noti.setContentTitle("Alarm Alarm....")
        noti.setContentText("No te olvides de parpadear ni de tomar awita <3")
        noti.setSmallIcon(R.drawable.logo2)
        //prioridad de la notificacion
        noti.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        noti.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("No te olvides de parpadear ni de tomar awita <3")
        )
        noti.setContentIntent(pendingIntent)
        noti.setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            1,noti.build()
        )

    }

}