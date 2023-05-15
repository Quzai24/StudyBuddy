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
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.studybuddy.objects.Task
import com.example.studybuddy.TaskViewModel
import com.example.studybuddy.databinding.FragmentAddBinding
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    private val calendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("hh:mm a", Locale.US)
    private val viewModel: TaskViewModel by activityViewModels()
    private var _binding : FragmentAddBinding? = null
    private val binding get() =_binding!!
    private var hourOfDay = 0
    private var minute = 0
    private var rep = 5
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val rootView = binding.root
        binding.whattime.setOnClickListener { TimePickerDialog(requireContext(), this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show() }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rep = binding.seekBar.progress+5 }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {binding.repeat.text="$rep mins"}
        })

        binding.confirm.setOnClickListener{
            val task = binding.enterTask.text.toString()
            val days = getWeek()
            var weekTwoWeekNo = 2
            if(binding.weekly.isChecked){weekTwoWeekNo=0}
            if(binding.biweekly.isChecked){weekTwoWeekNo=1}
            viewModel.addTask(Task(task,listOf(hourOfDay,minute),days,weekTwoWeekNo,false,rep))
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
    fun getWeek(): List<String>{
        val daysOfTheWeek = mutableListOf<String>()
        if(binding.sunday.isChecked){daysOfTheWeek.add("Sunday")}//else{daysOfTheWeek.add("")}
        if(binding.monday.isChecked){daysOfTheWeek.add("Monday")}//else{daysOfTheWeek.add("")}
        if(binding.tuesday.isChecked){daysOfTheWeek.add("Tuesday")}//else{daysOfTheWeek.add("")}
        if(binding.wednesday.isChecked){daysOfTheWeek.add("Wednesday")}//else{daysOfTheWeek.add("")}
        if(binding.thursday.isChecked){daysOfTheWeek.add("Thursday")}//else{daysOfTheWeek.add("")}
        if(binding.friday.isChecked){daysOfTheWeek.add("Friday")}//else{daysOfTheWeek.add("")}
        if(binding.saturday.isChecked){daysOfTheWeek.add("Saturday")}//else{daysOfTheWeek.add("")}
//        daysOfTheWeek.forEachIndexed { index, element ->
//            if(index>0&&index<7){
//                if(daysOfTheWeek)
//            }
//        }
        return daysOfTheWeek
    }
}