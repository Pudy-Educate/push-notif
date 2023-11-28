package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val channelId = "TEST_NOTIF"
    private val notifId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listener()
    }

    private fun listener(){
        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        with(binding){
            btnNotif.setOnClickListener {
                val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
                else {
                    0
                }

//                val intent = Intent(applicationContext,NotifReceiver::class.java).putExtra("MESSAGE", "Baca selengkapnya ...")
                val intent = Intent(this@MainActivity,MainActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(
                    applicationContext,
                    0,
                    intent,
                    flag
                )
                val builder = NotificationCompat.Builder(applicationContext, channelId)
                    .setSmallIcon(R.drawable.baseline_notifications_24)
                    .setContentTitle("Notifku")
                    .setContentText("Hello World!")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(0, "Baca Notif", pendingIntent)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notifChannel = NotificationChannel(
                        channelId, // Id channel
                        "Contoh Notif", // Nama channel notifikasi
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    with(notifManager) {
                        createNotificationChannel(notifChannel)
                        notify(notifId, builder.build())
                    }
                }
                else {
                    notifManager.notify(notifId, builder.build())
                }

            }
            btnUpdate.setOnClickListener {
                val notifImage = BitmapFactory.decodeResource(resources,
                    R.drawable.image)
                val builder = NotificationCompat.Builder(this@MainActivity, channelId)
                    .setSmallIcon(R.drawable.baseline_notifications_24)
                    .setContentTitle("Notifku")
                    .setContentText("Ini update notifikasi")
                    .setStyle(
                        NotificationCompat.BigPictureStyle()
                            .bigPicture(notifImage)
                    )
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                notifManager.notify(notifId, builder.build())
            }

        }
    }
}