package com.example.studybuddy.screens

import android.annotation.SuppressLint
import android.app.*
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateFormat.getLongDateFormat
import android.text.format.DateFormat.getTimeFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentAddBinding
import com.example.studybuddy.objects.*
import com.example.studybuddy.objects.Notification
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("hh:mm a", Locale.US)
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var hourOfDay = 0
    private var minute = 0
    private var rep = 5
    private var weekTwoWeekNo = 2
    private var timeChange = false
    private lateinit var dbRef: DatabaseReference
    private val viewModel: TaskViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val rootView = binding.root

        //If you are editing a task
        val args = AddFragmentArgs.fromBundle(requireArguments())
        if (args.task != "") {
            var task = Task()
            for (tasks in viewModel.tasks.value!!)
                if (tasks.task == args.task)
                    task = tasks
            binding.enterTask.setText(task.task)
            var hour = task.time[0] % 12
            if (hour == 0) hour = 12
            val minute = if (task.time[1] < 10) { "0" } else { "" } + task.time[1]
            val afternoon = if (task.time[0] > 12) { "PM" } else { "AM" }
            binding.whattime.text = "$hour : $minute $afternoon"
            if (task.days.contains("Sunday")) { binding.sunday.isChecked = true }
            if (task.days.contains("Monday")) { binding.monday.isChecked = true }
            if (task.days.contains("Tuesday")) { binding.tuesday.isChecked = true }
            if (task.days.contains("Wednesday")) { binding.wednesday.isChecked = true }
            if (task.days.contains("Thursday")) { binding.thursday.isChecked = true }
            if (task.days.contains("Friday")) { binding.friday.isChecked = true }
            if (task.days.contains("Saturday")) { binding.saturday.isChecked = true }
            binding.seekBar.progress = if (viewModel.repeatAlarms) { task.repeat + 5 } else { 0 }
            if (task.weekly == 0) { binding.weekly.isChecked = true }
            else if (task.weekly == 1) { binding.biweekly.isChecked = true }
        }

        //time picker
        binding.whattime.setOnClickListener { TimePickerDialog(requireContext(), this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show() }

        //repeat time
        if (!viewModel.repeatAlarms) {
            binding.fivemin.isGone = true
            binding.seekBar.isGone = true
            binding.hour.isGone = true
        }

        //seekbar setup
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) { rep = binding.seekBar.progress + 5 }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) { binding.repeat.text = "$rep mins" }
        })

        //creates notification channel
        createNotificationChannel()

        //Setting values
        binding.confirm.setOnClickListener {
            if (binding.enterTask.text.toString() != "" && getWeek() != listOf<String>() && timeChange) {
                val name = binding.enterTask.text.toString()
                val days = getWeek()
                if (binding.weekly.isChecked) { weekTwoWeekNo = 0 }
                if (binding.biweekly.isChecked) { weekTwoWeekNo = 1 }

                //passes values to database
                dbRef = FirebaseDatabase.getInstance().getReference("Alarms")
                val task = Task(name, listOf(hourOfDay, minute), days, weekTwoWeekNo, rep)
                dbRef.child(if (args.task != "") { args.task } else { task.task }).setValue(task)
                viewModel.setTask(args.task, task)

                //creates scheduled notification
                scheduleNotification()

                rootView.findNavController().navigateUp()
            }
            else if (binding.enterTask.text.toString() == "") { Toast.makeText(requireContext(), "Input a Task Name", Toast.LENGTH_SHORT).show() }
            else if (getWeek() == listOf<String>()) { Toast.makeText(requireContext(), "Input day(s)", Toast.LENGTH_SHORT).show() }
            else if (!timeChange) { Toast.makeText(requireContext(), "Input a time", Toast.LENGTH_SHORT).show() }
        }

        return rootView
    }

    private fun scheduleNotification() {
        val alarmManager = activity?.getSystemService(ALARM_SERVICE) as AlarmManager

        var time: Long = 0
        val nextDay= getNextDay()
        val alarms = mutableListOf<PendingIntent>()
        while (nextDay.size != 0) {
            if (nextDay[0] != 0) { calendar.add(Calendar.DAY_OF_YEAR, nextDay[0]) }
            calendar.get(Calendar.DAY_OF_WEEK).toString()
            time = calendar.timeInMillis

            val intent = Intent(activity?.applicationContext!!, Notification::class.java)
            intent.putExtra("name", binding.enterTask.text.toString())
            intent.putExtra("time", getTimeFormat(activity?.applicationContext!!).format(Date(time)))
            intent.apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
            val pendingIntent = PendingIntent.getBroadcast(activity?.applicationContext!!, notificationID, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            alarms.add(0,pendingIntent)

            if (weekTwoWeekNo == 0) { alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 24 * 7 * 60 * 60 * 1000, pendingIntent) }
            else if (weekTwoWeekNo == 1) { alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 24 * 14 * 60 * 60 * 1000, pendingIntent) }
            else alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
            nextDay.removeAt(0)
        }
        viewModel.alarms.add(Alarms(alarms, binding.enterTask.text.toString()))
        showAlert(time, binding.enterTask.text.toString())
    }

    private fun showAlert(time: Long, title: String) {
        val date = Date(time)
        val dateFormat = getLongDateFormat(activity?.applicationContext!!)
        val timeFormat = getTimeFormat(activity?.applicationContext!!)
        AlertDialog.Builder(requireContext())
            .setTitle("Notification Scheduled")
            .setMessage("Task: $title\nAt: ${dateFormat.format(date)} ${timeFormat.format(date)}")
            .setPositiveButton("Okay") { _, _ -> }
            .show()
    }

    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager: NotificationManager = activity?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute) }
        this.hourOfDay = hourOfDay
        this.minute = minute
        displayFormattedDay(calendar.timeInMillis)
        timeChange = true
    }

    private fun displayFormattedDay(timestamp: Long) {
        binding.whattime.text = formatter.format(timestamp)
    }


    private fun getWeek(): MutableList<String> {
        val daysOfTheWeek = mutableListOf<String>()
        if (binding.sunday.isChecked) { daysOfTheWeek.add("Sunday") }
        if (binding.monday.isChecked) { daysOfTheWeek.add("Monday") }
        if (binding.tuesday.isChecked) { daysOfTheWeek.add("Tuesday") }
        if (binding.wednesday.isChecked) { daysOfTheWeek.add("Wednesday") }
        if (binding.thursday.isChecked) { daysOfTheWeek.add("Thursday") }
        if (binding.friday.isChecked) { daysOfTheWeek.add("Friday") }
        if (binding.saturday.isChecked) { daysOfTheWeek.add("Saturday") }
        return daysOfTheWeek
    }

    private fun getNextDay(): MutableList<Int> {
        val daysOfTheWeek = mutableListOf<Int>()
        if (binding.sunday.isChecked) { daysOfTheWeek.add(0); binding.sunday.isChecked = false }
        if (binding.monday.isChecked) { daysOfTheWeek.add(1); binding.monday.isChecked = false }
        if (binding.tuesday.isChecked) { daysOfTheWeek.add(2); binding.tuesday.isChecked = false }
        if (binding.wednesday.isChecked) { daysOfTheWeek.add(3); binding.wednesday.isChecked = false }
        if (binding.thursday.isChecked) { daysOfTheWeek.add(4); binding.thursday.isChecked = false }
        if (binding.friday.isChecked) { daysOfTheWeek.add(5); binding.friday.isChecked = false }
        if (binding.saturday.isChecked) { daysOfTheWeek.add(6); binding.saturday.isChecked = false }
        var day = calendar.get(Calendar.DAY_OF_WEEK) - 1
        var daysToAdvance = mutableListOf<Int>()
        while(daysOfTheWeek.size!=0){
            daysToAdvance.add(0,0)
            while(!daysOfTheWeek.contains(day)) { day = (day + 1) % 7; daysToAdvance[0]++ }
            daysOfTheWeek.removeAt(0)
        }
        return daysToAdvance
    }
}