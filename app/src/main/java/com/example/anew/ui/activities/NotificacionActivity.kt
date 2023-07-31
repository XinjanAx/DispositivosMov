package com.example.anew.ui.activities

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.anew.R
import com.example.anew.databinding.ActivityNotificacionBinding

class NotificacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNotificacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotificacion.setOnClickListener{
            createNotificationChannel()
            sendNorificacion()
        }
    }


    val CHANNEL = "Notificaciones"


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Variedades"
            val descriptionText = "notificaciones simple de variedades"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }



    @SuppressLint("MissingPermission")
    fun sendNorificacion(){

        val intent = Intent(this, CameraActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )


        val noti = NotificationCompat.Builder(this, CHANNEL)
        //cuerpo de Notificacion
        noti.setContentTitle("Titulo")
        noti.setContentText("No te olvides de parpadear ni de tomar awita <3")
        noti.setSmallIcon(R.drawable.logo2)
        //prioridad de la notificacion
        noti.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        noti.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line...")
        )
        //aqui se toca la notificacion.
        noti.setContentIntent(pendingIntent)
        noti.setAutoCancel(true)


        //eniar la notificacion
        with(NotificationManagerCompat.from(this)){
            notify(1,noti.build())
        }

    }
}