package id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.notifbg;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import java.util.Calendar;

import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.MyBroadcastReceiver;
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.R;
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.ui.createTodo.CreateTodoActivity;
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.ui.todoList.TodoListActivity;
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.utils.GlobalVar;

import static java.lang.Integer.parseInt;

public class NotificationIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public NotificationIntentService() {
        super(NotificationIntentService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context ) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();

            if (ACTION_START.equals(action)) {
                processStartNotification();
            }
            if (ACTION_DELETE.equals(action)) {
                processDeleteNotification(intent);
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    private void processStartNotification() {
        // Do something. For example, fetch fresh data from backend to create a rich notification?
        Log.i(getClass().getSimpleName(), "Process start Notif");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("To-Do-Task")
                .setContentText("it s sTask Time Up")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        Intent intent = new Intent(this , MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID,
                intent, 0);

        Long time = ((GlobalVar) this.getApplication()).getSomeVariable();
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

        /**
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                new Intent(this, TodoListActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent2);
        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));


        createNotificationChannel();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        **/

    }



    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "To-Do-Task";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}