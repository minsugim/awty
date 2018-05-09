package edu.minsugim.uw.ischool.awty

import android.app.AlarmManager
import android.app.PendingIntent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*
import android.content.pm.PackageManager
import android.content.ComponentName
import com.valdesekamdem.library.mdtoast.MDToast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val message : EditText = findViewById(R.id.message)
        val number : EditText = findViewById(R.id.number)
        val nagInterval : EditText = findViewById(R.id.nag_interval)
        val button : Button = findViewById(R.id.button)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val receiver = ComponentName(applicationContext, AlarmReceiver::class.java)
        val pm = applicationContext.packageManager

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        button.setOnClickListener {
            when (button.text.toString()) {
                "Start" -> {
                    if (message.text.isNotEmpty() && number.text.isNotEmpty() && nagInterval.text.isNotEmpty()) {
                        button.text = getString(R.string.stop)
                        val interval = nagInterval.text.toString().toLong()
                        val intent = Intent(this, AlarmReceiver::class.java)
                        intent.putExtra("number", number.text.toString())
                        intent.putExtra("message", message.text.toString())
                        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT)
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().timeInMillis, 1000 * 60 * interval, pendingIntent)
                        pm.setComponentEnabledSetting(receiver,
                                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                PackageManager.DONT_KILL_APP)
                    } else {
                        var error = ""
                        if (message.text.isEmpty()) {
                            error += "Please type a message to send"
                        }
                        if (number.text.isEmpty()) {
                            if (error.isNotEmpty()) error += "\n"
                            error += "Please enter a number to nag"
                        }
                        if (nagInterval.text.isEmpty()) {
                            if (error.isNotEmpty()) error += "\n"
                            error += "Please enter a nagging interval"
                        }
                        MDToast.makeText(this, error, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show()
                    }
                }
                "Stop" -> {
                    button.text = getString(R.string.start)
                    pm.setComponentEnabledSetting(receiver,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP)
                }
            }
        }
    }
}


