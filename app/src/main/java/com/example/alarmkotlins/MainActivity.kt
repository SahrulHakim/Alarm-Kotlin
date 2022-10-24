package com.example.alarmkotlins

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.alarmkotlins.databinding.ActivityDestinationBinding
import com.example.alarmkotlins.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var picker :MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        binding.selectTimeBtn.setOnClickListener {

            showTimePicker()

        }
        binding.setAlarmBtn.setOnClickListener {

            setAlarm()

        }
        binding.cancelAlarmBtn.setOnClickListener {

            cancelAlarm()

        }

    }

    private fun cancelAlarm() {

        alarmManager= getSystemService(ALARM_SERVICE) as AlarmManager
        val intent =Intent(this,AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

        alarmManager.cancel(pendingIntent)
        Toast.makeText(this,"Alarm Cancelled",Toast.LENGTH_SHORT).show()

        

    }

    private fun setAlarm() {


        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

        alarmManager.setRepeating(

        AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent

        )

        Toast.makeText(this,"Alarm set Successfuly",Toast.LENGTH_SHORT).show()

    }

    @SuppressLint("SetTextI18n")
    private fun showTimePicker() {

        picker =MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show(supportFragmentManager, "sahrul")
        picker.addOnPositiveButtonClickListener {

            if (picker.hour > 12) {

                binding.selectedTime.text =
                    (String.format ("%02d", picker.hour - 12) + " : " + String.format(
                        "%02",
                        picker.minute
                    ) as String + "PM") as CharSequence?


            } else {

                String.format("%02d", picker.hour)+" : " + String. format(
                    "%02",
                    picker.minute
                ) as String + "AM"

            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0



        }
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            val name: CharSequence = "sahrulReminderChannel"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("sahrul", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java as Class<Any>
            )

            notificationManager.createNotificationChannel(channel)
        }
    }
}

private operator fun Any.plus(s: String) {

}

private fun Any.createNotificationChannel(channel: NotificationChannel) {

}

private fun Any.format(s: String, i: Int) {

}
