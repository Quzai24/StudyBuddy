package com.example.studybuddy.screens

import android.app.*
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentCompleteBinding
import com.example.studybuddy.objects.*
import com.example.studybuddy.objects.Notification
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class CompleteFragment : Fragment() {
    private val calendar = Calendar.getInstance()
    private var _binding: FragmentCompleteBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbRef: DatabaseReference
    private val viewModel: TaskViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCompleteBinding.inflate(inflater, container, false)
        dbRef = FirebaseDatabase.getInstance().getReference("Achievements")
        val args = CompleteFragmentArgs.fromBundle(requireArguments())
        binding.complete.setOnClickListener{
            viewModel.achievmentGet(true, 1)
            dbRef.child("Complete Tasks").setValue(viewModel.completeTasks)
            dbRef.child("Achieve").setValue(viewModel.achievements.value)
            startActivity(Intent(activity?.applicationContext!!, MainActivity::class.java))
        }
        binding.incomplete.setOnClickListener { startActivity(Intent(activity?.applicationContext!!, MainActivity::class.java)) }
        binding.repeatalarm.setOnClickListener {
            for(i in viewModel.tasks.value!!){
                val days = mutableListOf<String>()
                for(j in i.days)
                    days.add(j.uppercase())
                if(viewModel.isIn(args.name)) {
                    createNotificationChannel()
                    scheduleNotification(i.task, i.repeat)
                    break
                }
            }
            binding.root.findNavController().navigateUp()
        }
        if(!viewModel.repeatAlarms)
            binding.repeatalarm.isGone = true
        return binding.root
    }
    private fun scheduleNotification(task : String,num : Int) {
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        calendar.get(Calendar.DAY_OF_WEEK).toString()
        val time = calendar.timeInMillis + num * 60 * 1000

        val intent = Intent(activity?.applicationContext!!, Notification::class.java)
        intent.putExtra("name", task)
        intent.putExtra("time", DateFormat.getTimeFormat(activity?.applicationContext!!).format(Date(time)))
        val pendingIntent = PendingIntent.getBroadcast(activity?.applicationContext!!, notificationID, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
        showAlert(time, task)
    }

    private fun showAlert(time: Long, title: String) {
        val date = Date(time)
        val dateFormat = DateFormat.getLongDateFormat(activity?.applicationContext!!)
        val timeFormat = DateFormat.getTimeFormat(activity?.applicationContext!!)
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
        val notificationManager: NotificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}