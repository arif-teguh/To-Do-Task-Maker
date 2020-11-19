package id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Vibrator
import android.provider.Settings.Global.getString
import android.widget.Toast


class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
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