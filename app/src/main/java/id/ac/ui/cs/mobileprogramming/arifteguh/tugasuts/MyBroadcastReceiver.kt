package id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Vibrator
import android.provider.Settings.Global.getString
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.ui.todoList.TodoListActivity
import kotlinx.android.synthetic.main.activity_create_todo.*


class MyBroadcastReceiver : BroadcastReceiver() {

    var notificationId = 1

    override fun onReceive(context: Context, intent: Intent) {


        var builder = NotificationCompat.Builder(context, notificationId.toString())
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("To-Do-Task")
            .setContentText("Task Time Up")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)



        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }


        Toast.makeText(
            context, "Task !!",
            Toast.LENGTH_LONG
        ).show()
        // Vibrate the mobile phone
        val vibrator =
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(2000)
    }


}