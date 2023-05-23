package com.example.studybuddy.screens

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TimePicker
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.studybuddy.objects.Task
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("hh:mm a", Locale.US)
    private var _binding : FragmentAddBinding? = null
    private val binding get() =_binding!!
    private var hourOfDay = 0
    private var minute = 0
    private var rep = 5
    private lateinit var dbRef : DatabaseReference
    private val viewModel: TaskViewModel by activityViewModels()
    private var replace = -1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val args = AddFragmentArgs.fromBundle(requireArguments())
        if(args.task != "") {
            var task = Task()
            var index = 0
            for (tasks in viewModel.tasks.value!!)
                if (tasks.task == args.task) {
                    task = tasks
                    replace = index
                    index++
                }
            binding.enterTask.setText(task.task)
            var hour = task.time[0]%12
            if(hour==0) hour = 12
            var minute = if(task.time[1]<10){"0"}else{""} + task.time[1]
            var afternoon  = if(task.time[0]>12){"PM"}else{"AM"}
            binding.whattime.text = "$hour : $minute $afternoon"
            if(task.days.contains("Sunday")){binding.sunday.isChecked = true}
            if(task.days.contains("Monday")){binding.monday.isChecked = true}
            if(task.days.contains("Tuesday")){binding.tuesday.isChecked = true}
            if(task.days.contains("Wednesday")){binding.wednesday.isChecked = true}
            if(task.days.contains("Thursday")){binding.thursday.isChecked = true}
            if(task.days.contains("Friday")){binding.friday.isChecked = true}
            if(task.days.contains("Saturday")){binding.saturday.isChecked = true}
            binding.seekBar.progress = if(viewModel.repeatAlarms){task.repeat + 5}else{0}
            if(task.weekly == 0){binding.weekly.isChecked = true}
            else if(task.weekly == 1){binding.biweekly.isChecked = true}
        }
        //time picker
        binding.whattime.setOnClickListener { TimePickerDialog(requireContext(), this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show() }
        //repeat time
        if(!viewModel.repeatAlarms) {
            binding.fivemin.isGone = true
            binding.seekBar.isGone = true
            binding.hour.isGone = true
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rep = binding.seekBar.progress+5 }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {binding.repeat.text="$rep mins"}
        })
        //Setting values
        binding.confirm.setOnClickListener{
            val name = binding.enterTask.text.toString()
            val days = getWeek()
            var weekTwoWeekNo = 2
            if(binding.weekly.isChecked){weekTwoWeekNo=0}
            if(binding.biweekly.isChecked){weekTwoWeekNo=1}

            dbRef = FirebaseDatabase.getInstance().getReference("Alarms")
            val task = Task(name, listOf(hourOfDay,minute),days,weekTwoWeekNo,false,rep)
            dbRef.child(args.task).setValue(task)
            if(replace>=0) {viewModel.setTask(replace,task)}

            rootView.findNavController().navigateUp()
        }

        return rootView
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY,hourOfDay)
            set(Calendar.MINUTE,minute)
        }
        this.hourOfDay = hourOfDay
        this.minute = minute
        displayFormattedDay(calendar.timeInMillis)
    }
    private fun displayFormattedDay(timestamp:Long){
        binding.whattime.text=formatter.format(timestamp)
    }
    private fun getWeek(): List<String>{
        val daysOfTheWeek = mutableListOf<String>()
        if(binding.sunday.isChecked){daysOfTheWeek.add("Sunday")}
        if(binding.monday.isChecked){daysOfTheWeek.add("Monday")}
        if(binding.tuesday.isChecked){daysOfTheWeek.add("Tuesday")}
        if(binding.wednesday.isChecked){daysOfTheWeek.add("Wednesday")}
        if(binding.thursday.isChecked){daysOfTheWeek.add("Thursday")}
        if(binding.friday.isChecked){daysOfTheWeek.add("Friday")}
        if(binding.saturday.isChecked){daysOfTheWeek.add("Saturday")}
        return daysOfTheWeek
    }
}