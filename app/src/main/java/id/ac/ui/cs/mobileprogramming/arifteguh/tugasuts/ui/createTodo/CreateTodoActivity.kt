package id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.ui.createTodo


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.R
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.data.db.TodoRecord
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.notifbg.NotificationEventReceiver
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.utils.Constants
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.utils.GlobalVar
import kotlinx.android.synthetic.main.activity_create_todo.*
import java.util.*


class CreateTodoActivity : AppCompatActivity() {
    var checked = false
    var todoRecord: TodoRecord? = null
    var year = 2000
    var month = 1
    var day = 1
    var hour = 1
    var minute = 1
    var notificationId = 2
    var builder = NotificationCompat.Builder(this, notificationId.toString())
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("To-Do-Task")
        .setContentText("Task Created")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_todo)

        //Prepopulate existing title and content from intent
        val intent = intent
        if (intent != null && intent.hasExtra(Constants.INTENT_OBJECT)) {
            val todoRecord: TodoRecord = intent.getParcelableExtra(Constants.INTENT_OBJECT)
            this.todoRecord = todoRecord
            prePopulateData(todoRecord)
        }


        button_view_1.setOnClickListener{
            onBackPressed()
        }
        button_save_1.setOnClickListener{
            saveTodo()
        }
        alarm_checkbox.setOnClickListener{
            checked = !checked
        }

        title = if (todoRecord != null) getString(R.string.viewOrEditTodo) else getString(R.string.createTodo)
    }

    private fun prePopulateData(todoRecord: TodoRecord) {
        form_title.setText(todoRecord.title)
        form_tanggal_dd_1.setText(todoRecord.dd)
        form_tanggal_mm_1.setText(todoRecord.mm)
        form_tanggal_yy_1.setText(todoRecord.yyyy)
        form_waktu_hh_1.setText(todoRecord.hour)
        form_waktu_minute_1.setText(todoRecord.minute)
        form_deskripsi.setText(todoRecord.deskripsi)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflate = menuInflater
        menuInflate.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_todo -> {
                saveTodo()
            }
        }
        return true
    }



    /**
     * Sends the updated information back to calling Activity
     * */
    private fun saveTodo() {
        if (validateFields()) {
            val id = if (todoRecord != null) todoRecord?.id else null
            day = form_tanggal_dd_1.text.toString().filter { it.isLetterOrDigit() }.toInt()
            month = form_tanggal_mm_1.text.toString().filter { it.isLetterOrDigit() }.toInt()
            year = form_tanggal_yy_1.text.toString().filter { it.isLetterOrDigit() }.toInt()
            hour = form_waktu_hh_1.text.toString().filter { it.isLetterOrDigit() }.toInt()
            minute = form_waktu_minute_1.text.toString().filter { it.isLetterOrDigit() }.toInt()
            val todo = TodoRecord(id = id, title = form_title.text.toString(),
                 dd = day.toString(),
                 mm = month.toString(),
                 yyyy = year.toString(),
                 hour = hour.toString(),
                 minute = minute.toString(),
                 deskripsi = form_deskripsi.text.toString())

            if(checked) {
                val intentAlarm = Intent(AlarmClock.ACTION_SET_ALARM)
                intentAlarm.putExtra(
                    AlarmClock.EXTRA_HOUR, (hour)
                )
                intentAlarm.putExtra(
                    AlarmClock.EXTRA_MINUTES, (minute)
                )
                startActivity(intentAlarm)
            }
            startAlert()
            val intent = Intent()
            intent.putExtra(Constants.INTENT_OBJECT, todo)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    /**
     * Validation of EditText
     * */
    private fun validateFields(): Boolean {
        if (form_title.text.isEmpty()) {
            textView.error = getString(R.string.pleaseEnterTitle)
            form_title.requestFocus()
            return false
        }
        if (form_tanggal_dd_1.text.isEmpty() || (form_tanggal_dd_1.text.toString().filter { it.isLetterOrDigit() }).toInt() > 31 ) {
            textView2.error = getString(R.string.pleaseEnterContent)
            form_tanggal_dd_1.requestFocus()
            return false
        }
        if (form_tanggal_mm_1.text.isEmpty() || (form_tanggal_mm_1.text.toString().filter { it.isLetterOrDigit() }).toInt() > 12 ) {
            textView2.error = getString(R.string.pleaseEnterContent)
            form_tanggal_mm_1.requestFocus()
            return false
        }
        if (form_tanggal_yy_1.text.isEmpty() || (form_tanggal_yy_1.text.toString().filter { it.isLetterOrDigit() }).toInt() > 2100 || (form_tanggal_yy_1.text.toString().filter { it.isLetterOrDigit() }).toInt() < 2000 ) {
            textView2.error = getString(R.string.pleaseEnterContent)
            form_tanggal_yy_1.requestFocus()
            return false
        }
        if (form_waktu_minute_1.text.isEmpty() || (form_waktu_minute_1.text.toString().filter { it.isLetterOrDigit() }).toInt() > 59 ) {
            textView6.error = getString(R.string.pleaseEnterContent)
            form_waktu_minute_1.requestFocus()
            return false
        }
        if (form_waktu_hh_1.text.isEmpty() || (form_waktu_hh_1.text.toString().filter { it.isLetterOrDigit() }).toInt() > 23 ) {
            textView6.error = getString(R.string.pleaseEnterContent)
            form_waktu_hh_1.requestFocus()
            return false
        }
        if (form_deskripsi.text.isEmpty()) {
            textView5.error = getString(R.string.pleaseEnterContent)
            form_deskripsi.requestFocus()
            return false
        }
        return true
    }

    fun startAlert() {
        val cal: Calendar = Calendar.getInstance()
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        //val i = (form_waktu_minute_1.text.toString().filter { it.isLetterOrDigit() }).toInt()
        /**
        val intent = Intent(this, MyBroadcastReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            this.applicationContext, 234324243, intent, 0
        )

        val alarmManager =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, cal.timeInMillis] = pendingIntent


        val alarmManager2: AlarmManager = this.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent2 = Intent(this.applicationContext, MyIntentService::class.java)
        intent2.action = MyIntentService.ACTION_SEND_TEST_MESSAGE
        intent2.putExtra(MyIntentService.EXTRA_MESSAGE, "Test alarm")
        val pendingIntent2 = PendingIntent.getService(this.applicationContext, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager2.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent2)
        **/
        createNotificationChannel()
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }

        NotificationEventReceiver.setupAlarm(applicationContext , cal)
        val notif = Intent(this, NotificationEventReceiver::class.java)
        notif.setAction("ACTION_START_NOTIFICATION_SERVICE")
        (this.application as GlobalVar).setSomeVariable(cal.timeInMillis)
        sendBroadcast(notif)



    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "To-Do-Task"
            val descriptionText = form_title.text
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(notificationId.toString(), name, importance).apply {
                description = descriptionText.toString()
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}