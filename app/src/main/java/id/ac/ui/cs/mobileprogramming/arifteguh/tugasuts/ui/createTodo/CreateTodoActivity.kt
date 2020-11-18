package id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.ui.createTodo

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.R
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.data.db.TodoRecord
import id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.utils.Constants


import kotlinx.android.synthetic.main.activity_create_todo.*


class CreateTodoActivity : AppCompatActivity() {
    var checked = false
    var todoRecord: TodoRecord? = null

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
            val todo = TodoRecord(id = id, title = form_title.text.toString(),
                 dd = form_tanggal_dd_1.text.toString().filter { it.isLetterOrDigit() },
                 mm = form_tanggal_mm_1.text.toString().filter { it.isLetterOrDigit() },
                 yyyy = form_tanggal_yy_1.text.toString().filter { it.isLetterOrDigit() },
                 hour = form_waktu_hh_1.text.toString().filter { it.isLetterOrDigit() },
                 minute = form_waktu_minute_1.text.toString().filter { it.isLetterOrDigit() },
                 deskripsi = form_deskripsi.text.toString())

            if(checked) {
                val intentAlarm = Intent(AlarmClock.ACTION_SET_ALARM)
                intentAlarm.putExtra(
                    AlarmClock.EXTRA_HOUR,
                    (form_waktu_hh_1.text.toString().filter { it.isLetterOrDigit() }).toInt()
                )
                intentAlarm.putExtra(
                    AlarmClock.EXTRA_MINUTES,
                    (form_waktu_minute_1.text.toString().filter { it.isLetterOrDigit() }).toInt()
                )
                startActivity(intentAlarm)
            }
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
}